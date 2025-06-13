package com.xlei.aiagent.tools;

import jakarta.annotation.Resource;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.ToolCallbacks;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author https://github.com/xuzhixing181
 * 工具调用注册器
 */
@Configuration
public class ToolCallRegister {


    @Value("${search-api.api-key}")
    private String searchApiKey;

    /**
     * 提供多种工具调用实现,以供AI选择
     * @return
     */
    @Bean
    public ToolCallback[] allTools(){
        return ToolCallbacks.from(new FileOperationTool(),
                new PDFGenerationTool(),
                new ResourceDownloadTool(),
                new WebScrapeTool(),
                new WebSearchTool(searchApiKey));
    }
}