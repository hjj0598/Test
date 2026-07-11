package com.itheima.mapper;

import com.itheima.pojo.Student;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface StudentMapper {

    // 根据 userId 查询学生信息
//    @Select("SELECT * FROM test.student WHERE user_id = #{userId}")
//    Student getByUserId(Long userId);
    // ✅ 新的联表查询：同时拿到 student + user.phone
    @Select("""
        SELECT s.*, u.phone
        FROM test.student s
        LEFT JOIN test.user u ON s.user_id = u.id
        WHERE s.user_id = #{userId}
    """)
    Student getByUserId(Long userId);
    // 更新学生信息
    @Update("UPDATE test.student SET " +
            "student_id = #{studentId}, " +
            "college = #{college}, " +
            "major = #{major}, " +
            "grade = #{grade}, " +
            "resume = #{resume} " +
            "WHERE id = #{id}")
    void updateById(Student student);

    // 注册时插入空数据（和商家表逻辑一致）
    @Insert("INSERT INTO test.student (user_id) VALUES (#{userId})")
    void insertStudent(Long userId);
}