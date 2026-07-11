package com.itheima.service;

import com.itheima.pojo.JobApply;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface JobApplyService {
    boolean addApply(JobApply jobApply);
    // 学生：我的申请（接收 userId = user.id）
    List<JobApply> getMyApplies(Long userId);

    // 商家：审核列表（接收 userId = user.id）
    List<JobApply> getBusinessApplies(Long userId);
    boolean audit(Long id, Integer status);

    //获取所有应聘消息
    List<JobApply> getAllApplies(); // 👈 加这一行
}