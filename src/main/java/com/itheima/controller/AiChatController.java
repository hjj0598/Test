package com.itheima.controller;

import com.itheima.aiservice.ConsultantService;
import dev.langchain4j.model.openai.OpenAiChatModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@Slf4j
@RestController
public class AiChatController {

   @Autowired
   private ConsultantService consultantService;

    //  流式输出 调用模型
    @PostMapping(value = "/chat", produces = "text/plain;charset=utf-8")
    public Flux<String> chat(String memoryId, String message) {
        return consultantService.chat(memoryId, message);
    }
}
