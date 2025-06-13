package com.xlei.aiagent.agent;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SoulManusTest {

    @Resource
    private SoulManus soulManus;

    @Test
    public void execute() {
        String userPrompt = """
                我对象居住在深圳南山,请帮我找到5公里内合适的约会地点，
                并结合一些网络图片，制定一份详细的约会计划，
                并以 PDF 格式输出""";
        String answer = soulManus.execute(userPrompt);
        System.out.println("answer: " + answer);
        Assertions.assertNotNull(answer);
    }

}