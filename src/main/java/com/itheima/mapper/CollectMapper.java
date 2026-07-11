package com.itheima.mapper;

import com.itheima.pojo.Collect;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface CollectMapper {

    @Select("SELECT c.id AS collect_id, c.job_id, j.* FROM collect c " +
            "JOIN job j ON c.job_id = j.id " +
            "WHERE c.user_id = #{userId}")
    List<Map<String, Object>> getMyCollect(Long userId);

    @Delete("DELETE FROM collect WHERE id = #{id}")
    void deleteCollect(Long id);


    // ====================== 只加这一个 ====================== 实现收藏功能
    @Insert("INSERT INTO collect(user_id, job_id) VALUES(#{userId}, #{jobId})")
    void addCollect(Collect collect);


    //用来看是否重复添加了
    @Select("SELECT COUNT(*) FROM collect WHERE user_id=#{userId} AND job_id=#{jobId}")
    int checkExists(@Param("userId") Long userId, @Param("jobId") Long jobId);


      //检查登录用户 是否收藏当前兼职
    @Select("SELECT COUNT(*) FROM collect WHERE user_id = #{userId} AND job_id = #{jobId}")
    int checkCollected(@Param("userId") Long userId, @Param("jobId") Long jobId);
}