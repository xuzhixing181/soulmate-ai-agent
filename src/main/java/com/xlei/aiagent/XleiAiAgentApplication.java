package com.xlei.aiagent;

import org.springframework.ai.autoconfigure.mcp.client.McpClientAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = McpClientAutoConfiguration.class)
public class XleiAiAgentApplication {

    public static void main(String[] args) {
        SpringApplication.run(XleiAiAgentApplication.class, args);
    }

}
