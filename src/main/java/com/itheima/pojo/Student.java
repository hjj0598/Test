package com.itheima.pojo;

import lombok.Data;

@Data
public class Student {
    private Long id;
    private Long userId;
    private String studentId;  // 对应 student_id 字段
    private String college;
    private String major;
    private String grade;
    private String resume;
    //  新增：接收联表查询出来的 user.phone
    private String phone;
}