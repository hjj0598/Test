package com.itheima.mapper;

import com.itheima.pojo.JobApply;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface JobApplyMapper {

    @Insert("INSERT INTO job_apply(job_id, student_id, remark) " +
            "VALUES(#{jobId}, #{studentId}, #{remark})")
    int insert(JobApply jobApply);


    // ======================================
//     <!-- 商家的 user.id（给学生聊天用） -->
    // 学生：我的申请（增加商家的 user_id → businessUserId）
    // ======================================
    @Select("""
        SELECT 
            ja.*,
            j.title      AS jobTitle,
            j.salary     AS jobSalary,
            j.address    AS jobAddress,
            u.real_name  AS studentName,
            u.phone      AS studentPhone,
            bu.id        AS businessUserId 
        FROM job_apply ja
        JOIN student s ON ja.student_id = s.id
        JOIN job     j ON ja.job_id     = j.id
        JOIN user    u ON s.user_id     = u.id
        JOIN business b ON j.business_id = b.id
        JOIN user    bu ON b.user_id = bu.id
        WHERE s.user_id = #{userId}
        ORDER BY ja.apply_time DESC
    """)
    List<JobApply> selectMyApplies(Long userId);

    // ======================================
    // 商家：审核列表（增加学生的 user_id → studentUserId）
//    <!-- 学生的 user.id（给商家聊天用） -->
    // ======================================
    @Select("""
        SELECT 
            ja.*,
            j.title      AS jobTitle,
            u.real_name  AS studentName,
            u.phone      AS studentPhone,
            u.id         AS studentUserId  
        FROM job_apply ja
        JOIN job      j ON ja.job_id     = j.id
        JOIN business b ON j.business_id = b.id
        JOIN student  s ON ja.student_id = s.id
        JOIN user     u ON s.user_id     = u.id
        WHERE b.user_id = #{userId}
        ORDER BY ja.apply_time DESC
    """)
    List<JobApply> selectBusinessApplies(Long userId);

    // 审核状态更新
    @Update("UPDATE job_apply SET status = #{status} WHERE id = #{id}")
    int updateStatus(Long id, Integer status);

    @Select("""
    SELECT 
        ja.*,
        j.title             AS jobTitle,
        j.salary            AS jobSalary,
        j.address           AS jobAddress,
        stu.real_name       AS studentName,
        stu.phone           AS studentPhone,
        stu.id              AS studentUserId,
        bus.user_id         AS businessUserId,
        -- 👇 这里别名必须和 JobApply 里的字段名一模一样！
        bus.company_name    AS businessCompany,
        bus.contact_person  AS businessContact,
        bus.address         AS businessAddress,
        bus.intro           AS businessIntro,
        bus_user.phone      AS businessPhone
    FROM job_apply ja
    JOIN job j          ON ja.job_id       = j.id
    JOIN student s      ON ja.student_id   = s.id
    JOIN user stu       ON s.user_id       = stu.id
    JOIN business bus   ON j.business_id   = bus.id
    JOIN user bus_user  ON bus.user_id     = bus_user.id
    ORDER BY ja.apply_time DESC
""")
    List<JobApply> selectAllApplies();
}