package com.xlei.aiagent;

/**
 * @author https://github.com/xuzhixing181
 */
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest
class PgSQLVectorStoreConfigTest {

    @Resource
    private VectorStore pgVectorVectorStore;

    @Test
    void pgVectorVectorStore() {
        List<Document> documents = List.of(
                new Document("Java技术栈学习路线", Map.of("key1", "value1")),
                new Document("Spring AI开发指南",Map.of("key2", "value2")),
                new Document("恋爱约会聊天宝典", Map.of("key3", "value3")),
                new Document("Java开发工作必须要会的技能", Map.of("key3", "value3")),
                new Document("Golang技术栈学习路线", Map.of("key4", "value4")),
                new Document("如何变得富有,财商教育",Map.of("key5", "value5")),
                new Document("如何维护好亲密关系", Map.of("key6", "value6"))
        );
        // 添加文档
        pgVectorVectorStore.add(documents);
        // 相似度查询
        List<Document> results = pgVectorVectorStore.similaritySearch(SearchRequest.builder().query("请为我生成一份恋爱指南").topK(5).build());
        Assertions.assertNotNull(results);

    }
}