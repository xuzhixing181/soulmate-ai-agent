package com.xlei.aiagent.tools;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import com.xlei.aiagent.constant.FileConstant;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;

/**
 * @author https://github.com/xuzhixing181
 * 文件操作工具类(提供文件读写功能)
 */
public class FileOperationTool {

    private final String FILE_DIR = FileConstant.FILE_SAVE_DIR + "/doc";
    /**
     * 从文本读取内容
     * @param fileName
     * @return
     */
    @Tool(description = "read content from a file")
    public String readFile(@ToolParam(description = "name of a file to read") String fileName) {
        String filePath = FILE_DIR + "/" + fileName;
        try {
            return FileUtil.readUtf8String(filePath);
        } catch (IORuntimeException e) {
            return "read file error: " + e.getMessage();
        }

    }

    @Tool(description = "write content to a file")
    public String writeFile(@ToolParam(description = "name of the file to write") String fileName,
                            @ToolParam(description = "content of the file to write") String content) {
        String filePath = FILE_DIR + "/" + fileName;
        try {
            FileUtil.mkdir(FILE_DIR);
            FileUtil.writeUtf8String(content, filePath);
            return "write file successfully: " + fileName;
        } catch (IORuntimeException e) {
            return "write file error: " + e.getMessage();
        }
    }
}