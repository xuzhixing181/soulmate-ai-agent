package com.xlei.aiagent.agent;

import cn.hutool.core.util.StrUtil;
import com.xlei.aiagent.model.AgentStatus;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * @author https://github.com/xuzhixing181
 * 1) 代理抽象类,所有代理的基类,用于管理Agent状态和执行流程
 * 2) 使用模板方法,由子类实现 执行单个步骤的step方法(必选) 和 清理资源的 cleanup方法 (可选)
 */
@Data
@Slf4j
public abstract class BaseAgent {

    /**
     * Agent名称
     */
    private String name;

    /**
     * 系统提示词
     */
    private String systemPrompt;

    /**
     * 执行下一步操作的提示词
     */
    private String nextStepPrompt;

    /**
     * 当前Agent的执行状态
     */
    private AgentStatus agentStatus = AgentStatus.RUNNING;

    /**
     * 最大执行次数
     */
    private Integer maxSteps = 10;

    /**
     * 当前执行次数
     */
    private Integer curStep = 0;

    private ChatClient chatClient;

    /**
     * 代替ChatMemory,自行维护会话上下文
     */
    private List<Message> messageList = new ArrayList<>();

    /**
     * 运行代理
     * @param userPrompt 用户提示词
     * @return 执行结果
     */
    public String execute(String userPrompt){
        // 1.基础校验
        if (AgentStatus.IDLE.equals(this.agentStatus)){
            throw new RuntimeException("Cannot execute agent " + getName() + " for status : " + this.agentStatus);
        }
        if (StrUtil.isEmpty(userPrompt)){
            throw new RuntimeException("Cannot run agent with empty user prompt");
        }
        // 2.改变Agent运行状态
        this.agentStatus = AgentStatus.RUNNING;
        // 记录消息上下文
        messageList.add(new UserMessage(userPrompt));
        List<String> results = new ArrayList<>();
        int curStep = 0;
        // 3.循环逐步执行任务
        try {
            while(curStep <= maxSteps && !AgentStatus.FINISHED.equals(agentStatus)){
                String curStepResult = step();
                String result = "execute step " + curStep + "/" + maxSteps + ": " + curStepResult;
                log.info("execute info :{}",result);
                results.add(result);
            }
            // 当达到最大执行次数后,添加完成标识
            if (curStep == maxSteps){
                this.agentStatus = AgentStatus.FINISHED;
                results.add("Terminated: Reached max steps (" + maxSteps + ")");
            }
            return String.join("\n",results);
        } catch (Exception e) {
           this.agentStatus = AgentStatus.ERROR;
           log.error(this.name + " agent execute error: ", e);
           return "execute error: " + e.getMessage();
        } finally {
            // 4.清理资源
            this.cleanup();
        }
    }

    /**
     * 执行单个步骤 (模板方法,由子类实现)
     * @return 执行结果
     */
    public abstract String step();

    /**
     * 清理资源
     */
    public void cleanup(){

    }


}