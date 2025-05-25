package com.xlei.aiagent.demo.invoke;

import dev.langchain4j.community.model.dashscope.QwenChatModel;
import dev.langchain4j.model.chat.ChatLanguageModel;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author https://github.com/xuzhixing181
 */
public class LangChainAiInvoke {

    @Value("${spring.ai.dashscope.api-key}")
    private static String API_KEY;

    public static void main(String[] args) {
        ChatLanguageModel qwenModel = QwenChatModel.builder()
                .apiKey(API_KEY)
                .modelName("qwen-max")
                .build();
        String answer = qwenModel.chat("请介绍下查理.芒格");
        System.out.println(answer);
    }
}
