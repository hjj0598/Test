package com.itheima.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobApply {


    private    Long  id;
    private  Long  jobId;
    private  Long studentId;
    private LocalDateTime applyTime;
    private   Integer status ;
    private String remark;


    // 添加前端渲染需要的字段
    private String jobTitle;    // 兼职标题
    private String jobSalary;  // 薪资
    private String jobAddress; // 地址
    private String studentName;// 学生姓名
    private String studentPhone;// 学生电话

    // ====================== 聊天需要的字段（新增）======================
    private Long businessUserId;  // 商家的 user.id（学生 → 商家）
    private Long studentUserId;   // 学生的 user.id（商家 → 学生）

    // ====================== 只在这里追加 商家显示字段（新增）======================
    private String businessCompany;  // 商家公司名称
    private String businessContact;  // 商家联系人
    private String businessAddress;  // 商家地址
    private String businessIntro;    // 商家介绍
    private String businessPhone;    // 商家电话
}
