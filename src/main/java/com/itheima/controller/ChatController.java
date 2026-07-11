package com.itheima.controller;


import com.itheima.pojo.ChatMessage;
import com.itheima.service.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    private ChatMessageService chatMessageService;

    // 获取聊天记录
    @GetMapping("/list")
    public Map<String, Object> list(Integer applyId) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", 1);
        map.put("data", chatMessageService.getList(applyId));
        return map;
    }

    // 发送消息
    @PostMapping("/send")
    public Map<String, Object> send(@RequestBody ChatMessage message) {
        chatMessageService.send(message);
        Map<String, Object> map = new HashMap<>();
        map.put("code", 1);
        map.put("msg", "发送成功");
        return map;
    }
}