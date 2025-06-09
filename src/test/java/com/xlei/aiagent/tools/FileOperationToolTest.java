package com.xlei.aiagent.tools;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FileOperationToolTest {

    @Test
    void writeFile() {
        FileOperationTool tool = new FileOperationTool();
        String fileName = "Tool-Calling-Test.md";
        String content = "测试Spring AI的工具调用功能,将当前内容写入到文本中";
        String result = tool.writeFile(fileName, content);
        Assertions.assertNotNull(result);
    }
    @Test
    void readFile() {
        FileOperationTool tool = new FileOperationTool();
        String fileName = "Tool-Calling-Test.md";
        String result = tool.readFile(fileName);
        Assertions.assertNotNull(result);
    }


}