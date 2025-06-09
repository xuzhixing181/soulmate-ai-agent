package com.xlei.aiagent.tools;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PDFGenerationToolTest {

    @Test
    void generatePDF() {
        PDFGenerationTool tool = new PDFGenerationTool();
        String fileName = "Spring AI应用开发技术总结22.pdf";
        String content = "RAG:检索增强生成; Tool-Calling:工具调用;MCP:模型上下文协议";
        String result = tool.generatePDF(fileName, content);
        assertNotNull(result);
    }
}