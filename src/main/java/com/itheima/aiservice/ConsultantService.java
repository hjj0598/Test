package com.itheima.aiservice;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import reactor.core.publisher.Flux;

public interface ConsultantService {

    // 加了 @MemoryId 表示用户ID，用来区分不同人的记忆
    //  直接返回 Flux<String>，最简单！
//    工作内容：负责点单、制作奶茶、清洁卫生
//- 薪资：150元/天
//- 工作地点：学校商业街A101
//- 招聘人数：2    @SystemMessage("我是胡俊杰研发的，我是一个由胡俊杰研发回答所有求职学生的Ai 小助手，例如你可以跟我说 你想要在哪工作，薪资期望怎么样，工作地点，如果有多人也可以问我有没有多人可以干的工作")
    Flux<String> chat(@MemoryId String memoryId, @UserMessage String message);
//    String chat(@MemoryId String userId, @UserMessage String message);
}
