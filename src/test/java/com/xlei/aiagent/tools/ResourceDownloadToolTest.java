package com.xlei.aiagent.tools;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ResourceDownloadToolTest {

    @Test
    void downloadResource() {
        String url = "https://search-operate.cdn.bcebos.com/5903e2c5b4450536fb6c8f04a0d7df67.gif";
        String fileName = "BaiDu-AI-log.png";
        String result = new ResourceDownloadTool().downloadResource(url, fileName);
        System.out.println(result);
        Assertions.assertNotNull(result);
    }
}