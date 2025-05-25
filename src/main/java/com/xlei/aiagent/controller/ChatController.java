package com.xlei.aiagent.controller;

import com.xlei.aiagent.service.ChatService;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author https://github.com/xuzhixing181
 */
@RestController
@RequestMapping("/ai/chat")
public class ChatController {

    // 注入ChatClient的方式1:使用构造器注入
    private final ChatClient chatClient;

    public ChatController(ChatClient.Builder builder) {
        this.chatClient = builder.defaultSystem("你是恋爱导师").build();
    }

//    @Resource
//    private ChatModel dashscopeChatModel;
//
//    // 方式2:使用建造者模式构建ChatClient
//    private ChatClient chatClient = ChatClient.builder(dashscopeChatModel).defaultSystem("你是一名情感专家").build();

    /**
     * call(): 向 AI 模型发送请求
     * @param userInput: 用户提问
     * @return content: AI模型的响应
     */
    @GetMapping("/ask")
    String generation(String userInput) {
        // content: AI模型的响应
        return this.chatClient.prompt()
                .user(userInput)
                .call()
                .content();
    }

    /**
     * 测试响应格式
     */
    @GetMapping("/testResp")
    public String testResp(String input) {
        ChatResponse chatResponse = chatClient.prompt().user(input).call().chatResponse();
        return chatResponse.toString();
    }


}