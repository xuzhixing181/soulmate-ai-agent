package com.xlei.aiagent.app;

import com.xlei.aiagent.advisor.MyLoggerAdvisor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.List;

import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY;
import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_RETRIEVE_SIZE_KEY;

/**
 * @author xulei https://github.com/xuzhixing181
 */
@Component
@Slf4j
public class SoulMateApp {

    private final ChatClient chatClient;

    private static final String SYSTEM_PROMPT = "扮演深耕恋爱心理领域的专家。开场向用户表明身份，告知用户可倾诉恋爱难题。" +
            "围绕单身、恋爱、已婚三种状态提问：单身状态询问社交圈拓展及追求心仪对象的困扰；" +
            "恋爱状态询问沟通、习惯差异引发的矛盾；已婚状态询问家庭责任与亲属关系处理的问题。" +
            "引导用户详述事情经过、对方反应及自身想法，以便给出专属解决方案。";

    public SoulMateApp(ChatModel dashscopeChatModel) {
        // 实现基于内存的对话记忆
        InMemoryChatMemory chatMemory = new InMemoryChatMemory();
        chatClient = ChatClient.builder(dashscopeChatModel)
                .defaultSystem(SYSTEM_PROMPT)
                .defaultAdvisors(
                        new MessageChatMemoryAdvisor(chatMemory),
                        new MyLoggerAdvisor())   // 自定义日志增强,可按序开启
                .build();
    }

    /**
     * AI基础对话,支持多轮会话记忆
     * @return
     */
    public String doChatMultiTimes(String message, String chatId) {
        ChatResponse chatResponse = chatClient.prompt()
                .user(message).advisors(spec ->
                        // CHAT_MEMORY_CONVERSATION_ID_KEY: 聊天会话的唯一标识符
                        // CHAT_MEMORY_RETRIEVE_SIZE_KEY: 指定从记忆中检索的历史消息数量,即上下文的长度
                    spec.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId).param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 3))
                .call().chatResponse();
        String content = chatResponse.getResult().getOutput().getText();
        return content;
    }

    /**
     * AI 基础对话（支持多轮对话记忆,SSE 流式传输）
     * @return
     */
    public Flux<String> doChatMultiTimesByStream(String message, String chatId) {
        Flux<String> content = chatClient.prompt().user(message)
                .advisors(spec -> spec.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId).param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, chatId))
                .stream().content();
        return content;
    }

    record LoveReport(String title, List<String> suggestions) {

    }

    /**
     * AI 恋爱报告功能（实战结构化输出）
     *
     * @param message
     * @param chatId
     * @return
     */
    public LoveReport doChatWithReport(String message, String chatId) {
        LoveReport loveReport = chatClient
                .prompt()
                .system(SYSTEM_PROMPT + "每次对话后都要生成分析报告，标题为 {用户名}的恋爱报告，内容为建议列表")
                .user(message)
                .advisors(spec -> spec.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId)
                        .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 10))
                .call()
                .entity(LoveReport.class);    // 需要添加依赖 jsonschema-generator,才能支持结构化数据转换
        return loveReport;
    }


}