package com.xlei.aiagent.agent;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatOptions;
import com.xlei.aiagent.model.AgentStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.ToolResponseMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.model.tool.ToolCallingManager;
import org.springframework.ai.model.tool.ToolExecutionResult;
import org.springframework.ai.tool.ToolCallback;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author https://github.com/xuzhixing181
 * 基于ReAct模式,扩展了工具调用能力,调用机制的具体实现:
 *  1) Think: 和AI交互思考使用什么工具
 *  2) Act: 程序执行工具
 *  3) Observe: 将结果返回给AI
 */
@Data
@Slf4j
@EqualsAndHashCode(callSuper = true)
public class ToolCallAgent extends ReActAgent{

    /**
     * 可用的工具调用
     */
    private final ToolCallback[] availableTools;

    private ChatResponse toolCallingResponse;

    private final ToolCallingManager toolCallingManager;

    private final ChatOptions chatOptions;

    public ToolCallAgent(ToolCallback[] availableTools) {
        super();
        this.availableTools = availableTools;
        this.toolCallingManager = ToolCallingManager.builder().build();
        // 禁用Spring AI 内置的工具调用机制,自行维护选项 和 消息上下文
        this.chatOptions = DashScopeChatOptions.builder()
                .withProxyToolCalls(true)
                .build();
    }

    @Override
    public Boolean think() {
        if (StrUtil.isNotBlank(getNextStepPrompt())){
            UserMessage userMessage = new UserMessage(getNextStepPrompt());
            getMessageList().add(userMessage);
        }
        Prompt prompt = new Prompt(getMessageList(), this.chatOptions);
        // 2.调用AI大模型,获取 工具调用的结果
        ChatResponse chatResponse = getChatClient().prompt(prompt)
                .tools(availableTools)
                .call()
                .chatResponse();
        this.toolCallingResponse = chatResponse;

        AssistantMessage assistantMessage = chatResponse.getResult().getOutput();
        // 获取输出的提示信息 和 工具调用列表
        String result = assistantMessage.getText();
        List<AssistantMessage.ToolCall> toolCallList = assistantMessage.getToolCalls();
        log.info("{} 思考的结果: {},选择的工具调用数: {}",getName(),result,toolCallList.size());

        // 处理当前状态并决定下一步行动Act是否需要执行,true:需要执行; false:不需要执行
        if (toolCallList.isEmpty()){
            getMessageList().add(assistantMessage);
            return false;
        } else {
            // 在进行工具调用时会自动记录,无需记录消息助手
            return true;}

    }
    /**
     * 处理当前状态并决定下一步行动
     * @return 是否需要执行行动
     */
    @Override
    public String act() {
        if (!toolCallingResponse.hasToolCalls()){
            return "暂无可用的工具调用";
        }
        Prompt prompt = new Prompt(getNextStepPrompt(), this.chatOptions);
        ToolExecutionResult toolExecutionResult = toolCallingManager.executeToolCalls(prompt, this.toolCallingResponse);
        // conversationHistory: 记录消息上下文,含 助手消息和工具调用返回的结果
        List<Message> conversationHistory = toolExecutionResult.conversationHistory();
        setMessageList(conversationHistory);
        ToolResponseMessage toolRespMsg = (ToolResponseMessage) CollUtil.getLast(conversationHistory);
        // 当所有任务完成后,TerminateTool工具调用(@Tool注解) 通过AI合理中断
        boolean terminated = toolRespMsg.getResponses().stream().anyMatch(toolResponse -> "terminate".equals(toolResponse.name()));
        if (terminated){
            setAgentStatus(AgentStatus.FINISHED);
        }
        String results = toolRespMsg.getResponses().stream().map(response -> String.format("%s 工具调用的结果: %s", response.name(), response.responseData())).collect(Collectors.joining("\n"));
        log.info("current act results: {}", results);
        return results;
    }

}