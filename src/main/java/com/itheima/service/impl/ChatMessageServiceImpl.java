package com.itheima.service.impl;


import com.itheima.mapper.ChatMessageMapper;
import com.itheima.pojo.ChatMessage;
import com.itheima.service.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ChatMessageServiceImpl implements ChatMessageService {

    @Autowired
    private ChatMessageMapper chatMessageMapper;

    @Override
    public List<ChatMessage> getList(Integer applyId) {
        return chatMessageMapper.getMessageList(applyId);
    }

    @Override
    public int send(ChatMessage message) {
        return chatMessageMapper.insertMessage(message);
    }
}