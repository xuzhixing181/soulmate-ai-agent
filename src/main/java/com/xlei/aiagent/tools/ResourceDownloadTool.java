package com.xlei.aiagent.tools;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpUtil;
import com.xlei.aiagent.constant.FileConstant;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;

import java.io.File;

/**
 * @author https://github.com/xuzhixing181
 * 资源下载工具类
 */
public class ResourceDownloadTool {

    @Tool(description = "download resource from a given url")
    public String downloadResource(@ToolParam(description = "url of the resource to download") String url,
                                   @ToolParam(description = "name of the file to save the download resource") String fileName){
        String fileDir = FileConstant.FILE_SAVE_DIR + "/doc/download";
        String filePath = fileDir + File.separator + fileName;
        try {
            // 创建资源下载的目录
            FileUtil.mkdir(fileDir);
            // 下载资源并保存到文件中
            HttpUtil.downloadFile(url,new File(filePath));
            return "download resource successfully: " + filePath;
        } catch (Exception e) {
            return "download resource error: " + e.getMessage();
        }

    }
}