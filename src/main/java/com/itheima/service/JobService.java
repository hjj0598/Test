package com.itheima.service;

import com.itheima.pojo.Job;

import java.util.List;
import java.util.Map;

public interface JobService {
    List<Job> getJobList(String keyword, Integer page, Integer pageSize);

    Job getById(Long id);

    Map<String, Object> getJobDetailWithBusiness(Long id);

    Map<String, Object> getJobDetail(Long id);

    void add(Job job); // 加到接口里
}
