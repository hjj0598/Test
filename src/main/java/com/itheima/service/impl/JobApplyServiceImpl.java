package com.itheima.service.impl;

import com.itheima.mapper.JobApplyMapper;
import com.itheima.mapper.StudentMapper;
import com.itheima.pojo.JobApply;
import com.itheima.pojo.Student;
import com.itheima.service.JobApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobApplyServiceImpl implements JobApplyService {

    @Autowired
    private JobApplyMapper jobApplyMapper;

    @Autowired
    private StudentMapper studentMapper; // 注入你的 StudentMapper

    @Override
    public boolean addApply(JobApply jobApply) {
        // 1. 前端传过来的是 user.id（登录用户ID）
        Long userId = jobApply.getStudentId();

        // 2. 根据 user.id 查询 student 对象
        Student student = studentMapper.getByUserId(userId);

        // 3. 拿到真正的 student.id
        Long realStudentId = student.getId();

        // 4. 设置到申请表里
        jobApply.setStudentId(realStudentId);

        // 5. 插入数据库
        return jobApplyMapper.insert(jobApply) > 0;
    }

    @Override
    public List<JobApply> getMyApplies(Long userId) {
        return jobApplyMapper.selectMyApplies(userId);
    }

    @Override
    public List<JobApply> getBusinessApplies(Long userId) {
        return jobApplyMapper.selectBusinessApplies(userId);
    }

    // 审核
    @Override
    public boolean audit(Long id, Integer status) {
        return jobApplyMapper.updateStatus(id, status) > 0;
    }

     //获取所有兼职列表 admin
    @Override
    public List<JobApply> getAllApplies() {
        return jobApplyMapper.selectAllApplies();
    }
}