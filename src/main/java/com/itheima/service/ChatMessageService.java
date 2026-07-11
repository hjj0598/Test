package com.itheima.service;


import com.itheima.pojo.ChatMessage;

import java.util.List;

public interface ChatMessageService {
    List<ChatMessage> getList(Integer applyId);
    int send(ChatMessage message);
}