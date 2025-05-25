package com.xlei.aiagent;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class XleiAiAgentApplicationTests {

    record User(Integer userId,String username,String email){}
    @Test
    void contextLoads() {
        User user = new User(1010, "zhangsan", "1998776@163.com");
        System.out.println(user);
    }

}
