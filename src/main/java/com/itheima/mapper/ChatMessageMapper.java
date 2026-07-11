package com.itheima.mapper;


import com.itheima.pojo.ChatMessage;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;

public interface ChatMessageMapper {

    // 获取聊天记录  给每个对话记录绑定了对应的信息 apply_id
    @Select("SELECT * FROM chat_message WHERE apply_id = #{applyId} ORDER BY create_time ASC")
    List<ChatMessage> getMessageList(@Param("applyId") Integer applyId);

    // 发送消息
    @Insert("INSERT INTO chat_message(apply_id, from_id, to_id, content, create_time, is_read) " +
            "VALUES(#{applyId}, #{fromId}, #{toId}, #{content}, NOW(), 0)")
    int insertMessage(ChatMessage message);
}