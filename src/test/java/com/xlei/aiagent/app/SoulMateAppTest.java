package com.xlei.aiagent.app;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class SoulMateAppTest {

    @Resource
    private SoulMateApp soulMateApp;

    @Test
    void testDoChatMultiTimes() {
        String message1 = "我是一名Java开发工程师,还是一个B站up主,本科学历,平时社交圈小,请为我提供一些能接触到异性的途径";
        String chatId = UUID.randomUUID().toString();
        String ans1 = soulMateApp.doChatMultiTimes(message1,chatId);

        String message2 = "请为我推荐一些在约会时可以讨论的话题";
        String ans2 = soulMateApp.doChatMultiTimes(message2, chatId);

        String message3 = "基于前面的对话,你会如何向他人介绍我";
        String ans3 = soulMateApp.doChatMultiTimes(message3, chatId);

    }

    @Test
    void doChatMultiTimesByStream() {
    }

    @Test
    void doChatWithReport() {
        String chatId = UUID.randomUUID().toString();
        String message = "你好,我是xlei,我想让另一半更爱我，请为我给出可实行的建议";
        SoulMateApp.LoveReport loveReport = soulMateApp.doChatWithReport(message, chatId);
        Assertions.assertNotNull(loveReport);
    }
}