package com.itheima.config;

import com.itheima.aiservice.ConsultantService;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.service.AiServices;
import org.springframework.beans.factory.annotation.Autowired;
import dev.langchain4j.model.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonConfig {

//    @Autowired
//    private OpenAiChatModel model;
    // =============================================
    // 内存会话记忆（每个用户独立记忆，不用 Redis）
    // =============================================
    @Bean//会话记忆提供者

    public ChatMemoryProvider chatMemoryProvider() {
        return memoryId -> MessageWindowChatMemory.builder()
                .id(memoryId)          // 按用户ID存储
                .maxMessages(20)       // 保留最近20条消息
                .build();
    }


      //非流式  ✅ 流式输出
    //✅ 上下文记忆
    //✅ RAG 知识库检索
    //✅ 阿里云通义千问模型
    //✅ Spring 完全解耦
//    @Bean
//    public ConsultantService consultantService(ChatMemoryProvider chatMemoryProvider) {
//        return AiServices.builder(ConsultantService.class)
//                .chatModel(model)           // 你现在正确的方法
//                .chatMemoryProvider(chatMemoryProvider) // 加上这个 → 内存记忆生效
//                .build();
//    }

    // 流式对话 Service（新增）
    //  流式 + 记忆 + RAG 知识库（只改这里，加 contentRetriever）
    @Bean
    public ConsultantService streamConsultantService(
            OpenAiStreamingChatModel streamingModel,
            ChatMemoryProvider chatMemoryProvider,
            ContentRetriever contentRetriever // 加这个
    ) {
        return AiServices.builder(ConsultantService.class)
                .streamingChatModel(streamingModel)
                .chatMemoryProvider(chatMemoryProvider)
                .contentRetriever(contentRetriever) //  加这个
                .build();
    }
    @Bean
    public EmbeddingModel embeddingModel() {
        return dev.langchain4j.model.openai.OpenAiEmbeddingModel.builder()
                .baseUrl("https://dashscope.aliyuncs.com/compatible-mode/v1")
                .apiKey("sk-97466e7080854dfba5851621203015d2")
                .modelName("text-embedding-v3") // 阿里云文本向量模型v3模型
                .logRequests(true)
                .logResponses(true)
                .build();
    }
}
