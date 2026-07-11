package com.itheima.mapper;


import com.itheima.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper  {

////    查询全部用户信息
//    @Select("select * from user")
//    public List<User> list();

@Select("SELECT  * FROM test.user WHERE username=#{username} and password=#{password}")
public User getByUsernameAndPassword(User user);

//    @Insert("insert into test.user (username, password, real_name, phone, email, role, create_time, update_time) " +
//            "values (#{username}, #{password}, #{realName}, #{phone}, #{email}, #{role}, #{createTime}, #{updateTime})")
//    public void register(User user);

    // 注册用户 + 自动返回主键ID
    @Insert("insert into test.user (username, password, real_name, phone, email, role, create_time, update_time) " +
            "values (#{username}, #{password}, #{realName}, #{phone}, #{email}, #{role}, #{createTime}, #{updateTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id") //  关键！返回自增ID
    void register(User user);

    // 插入学生表
    @Insert("insert into test.student (user_id) values (#{userId})")
    void insertStudent(Long userId);

    // 插入商家表
    @Insert("insert into test.business (user_id) values (#{userId})")
    void insertBusiness(Long userId);
}
