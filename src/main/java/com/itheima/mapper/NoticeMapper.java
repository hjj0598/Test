package com.itheima.mapper;

import com.itheima.pojo.Notice;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
public interface NoticeMapper {


  @Select("select  * from test.notice")
    public   List<Notice> list();

    // 新增发布
    @Insert("insert into notice(title, content, create_time) values(#{title}, #{content}, now())")
    void add(Notice notice);
}
