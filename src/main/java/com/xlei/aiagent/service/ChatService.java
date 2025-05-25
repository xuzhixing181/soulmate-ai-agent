package com.xlei.aiagent.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

/**
 * @author https://github.com/xuzhixing181
 */
@Service
@Slf4j
public class ChatService {

    // 1) 使用构造器注入
//    private static ChatClient chatClient;
//
//    public ChatService(ChatClient.Builder builder) {
//        this.chatClient = builder.defaultSystem("你是恋爱导师").build();
//    }
//
//    public static String doChat(String text){
//        String content = chatClient.prompt().user(text).call().content();
////        log.info("content:{}", content);
//        return content;
//    }
//
//    public static void main(String[] args) {
//        String chatClientAns = doChat("请介绍下Spring AI");
//        System.out.printf("chatClientAns: " + chatClientAns);
//
//    }
    public static void main(String[] args) {

    }
}