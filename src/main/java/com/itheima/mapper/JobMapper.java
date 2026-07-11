package com.itheima.mapper;

import com.itheima.pojo.Job;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface JobMapper {

    // ！！新增：查询所有有效的兼职，为了将来能够很好的爬取数据数据做成知识库
    @Select("SELECT * FROM test.job WHERE status = 1")
    List<Job> selectAll();

    // 分页查询兼职列表（带搜索）
    @Select("SELECT * FROM test.job WHERE status = 1 " +
            "AND (title LIKE CONCAT('%',#{keyword},'%') OR address LIKE CONCAT('%',#{keyword},'%')) " +
            "LIMIT #{page},#{pageSize}")
    List<Job> list(@Param("keyword") String keyword, @Param("page") Integer page, @Param("pageSize") Integer pageSize);



       //根据具体的jobid 来查询具体工作的信息
        @Select("select * from job where id = #{id}")
        Job selectById(Long id);

    // 新增：联表查询，返回带商家名称+电话的详情
    @Select("""
        SELECT j.*, b.company_name, u.phone
        FROM job j
        LEFT JOIN business b ON j.business_id = b.user_id
        LEFT JOIN user u ON b.user_id = u.id
        WHERE j.id = #{id}
    """)
    Map<String, Object> getJobDetailWithBusiness(Long id);


    @Select("""
    SELECT 
        j.*,
        b.company_name,
        u.phone,
        u.real_name,
        j.publish_time -- 明确把发布时间加回来
    FROM job j
    LEFT JOIN business b ON j.business_id = b.id
    LEFT JOIN user u ON b.user_id = u.id
    WHERE j.id = #{jobId}
""")
    Map<String, Object> getJobDetailById(Long jobId);


    // 发布兼职
    @Insert("INSERT INTO test.job(title, content, salary, type, address, num, business_id, status) " +
            "VALUES(#{title}, #{content}, #{salary}, #{type}, #{address}, #{num}, #{businessId}, #{status})")
    void insert(Job job);
}