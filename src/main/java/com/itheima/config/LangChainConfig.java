package com.itheima.config;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.ClassPathDocumentLoader;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class LangChainConfig {
//        @Bean
//    public OpenAiChatModel openAiChatModel() {
//        return OpenAiChatModel.builder()
//                .baseUrl("https://dashscope.aliyuncs.com/compatible-mode/v1")
//                .apiKey("sk-97466e7080854dfba5851621203015d2")
//                .modelName("qwen-plus")
//
//                .logRequests(true)    // 打印请求
//                .logResponses(true)   // 打印响应
//                .build();
//
//    }
    // 流式模型（给流式接口用）
    @Bean
    public OpenAiStreamingChatModel streamingChatModel() {
        return OpenAiStreamingChatModel.builder()
                .baseUrl("https://dashscope.aliyuncs.com/compatible-mode/v1")
                .apiKey("sk-97466e7080854dfba5851621203015d2")
                .modelName("qwen-plus")
                .logRequests(true)
                .logResponses(true)
                .build();
    }



    // 1. 向量库（加载你的知识库）
    @Bean
    public EmbeddingStore embeddingStore(EmbeddingModel embeddingModel) {
        InMemoryEmbeddingStore store = new InMemoryEmbeddingStore();

        // 如果没有 content 目录，不会抛异常！
        List<Document> documents = new ArrayList<>();
        try {
            documents = ClassPathDocumentLoader.loadDocuments("content");
        } catch (Exception e) {
            System.err.println("⚠️ content 目录不存在，跳过文档加载");
        }

        EmbeddingStoreIngestor ingestor = EmbeddingStoreIngestor.builder()
                .embeddingStore(store)
                .embeddingModel(embeddingModel)
                .build();

        ingestor.ingest(documents);
        return store;
    }

    // 2. 知识库检索器
    @Bean
    public ContentRetriever contentRetriever(EmbeddingStore store, EmbeddingModel embeddingModel) {
        return EmbeddingStoreContentRetriever.builder()
                .embeddingStore(store)
                .embeddingModel(embeddingModel)
                .minScore(0.5)
                .maxResults(3)
                .build();
    }

}
