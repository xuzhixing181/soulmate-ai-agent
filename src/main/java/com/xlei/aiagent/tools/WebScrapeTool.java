package com.xlei.aiagent.tools;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.ai.tool.annotation.Tool;

import java.io.IOException;

/**
 * @author https://github.com/xuzhixing181
 * 网页抓取工具类
 */
public class WebScrapeTool {

    @Tool(description = "scrape content of a web page")
    public String scrape(String url) {
        try {
            Document document = Jsoup.connect(url).get();
            String text = document.body().text();
            String html = document.html();
            return text + "==>" + html;
        } catch (IOException e) {
            return "scrape web page failed: " + e.getMessage();
        }


    }
}