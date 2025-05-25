package com.xlei.aiagent.demo.invoke;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author https://github.com/xuzhixing181
 */
@Component
public class SpringAiInvoke implements CommandLineRunner {

    // 按照Bean的名称注入
    @Resource
    private ChatModel dashscopeChatModel;

    @Override
    public void run(String... args) throws Exception {
        AssistantMessage assistantMessage = dashscopeChatModel.call(new Prompt("请简单介绍你自己")).getResult().getOutput();
        System.out.println(assistantMessage.getText());

//        System.out.println("========================");
//        assistantMessage = dashscopeChatModel.call(new Prompt("请详细介绍下如何学号Java AI应用开发")).getResult().getOutput();
//        System.out.println(assistantMessage);
    }
}