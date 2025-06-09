package com.xlei.aiagent.tools;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WebScrapeToolTest {

    @Test
    void scrapeWebPage() {
        WebScrapeTool tool = new WebScrapeTool();
        String url = "https://www.bilibili.com/video/BV1Yy7HzCEws/?spm_id_from=333.337.search-card.all.click&vd_source=07d27276ac5a0e7907b7332196a62d87";
        String content = tool.scrape(url);
        Assertions.assertNotNull(content);

        PDFGenerationTool pdfGenerationTool = new PDFGenerationTool();
        String result = pdfGenerationTool.generatePDF("网页抓取到的内容2.pdf", content);
        Assertions.assertNotNull(result);
    }
}