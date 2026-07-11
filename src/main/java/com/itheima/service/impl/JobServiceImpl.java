package com.itheima.service.impl;

import com.itheima.mapper.JobMapper;
import com.itheima.pojo.Job;
import com.itheima.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

// 实现
@Service
public class JobServiceImpl implements JobService {
    @Autowired
    private JobMapper jobMapper;

    @Override
    public List<Job> getJobList(String keyword, Integer page, Integer pageSize) {
        // 分页偏移量计算
        return jobMapper.list(keyword, (page-1)*pageSize, pageSize);
    }

    @Override
    public Job getById(Long id) {
        return jobMapper.selectById(id);
    }
    @Override
    public Map<String, Object> getJobDetailWithBusiness(Long id) {
        return jobMapper.getJobDetailWithBusiness(id);
    }

    public Map<String, Object> getJobDetail(Long jobId) {
        return jobMapper.getJobDetailById(jobId);
    }

    @Transactional
    @Override
    public void add(Job job) {
        jobMapper.insert(job);
    }
}