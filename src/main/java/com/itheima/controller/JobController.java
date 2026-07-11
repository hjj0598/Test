package com.itheima.controller;

import com.itheima.pojo.Job;
import com.itheima.pojo.Result;
import com.itheima.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/job")
public class JobController {
    @Autowired
    private JobService jobService;

       //获取所有兼职信息
    @GetMapping("/list")
    public Result list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "") String keyword
    ) {
        List<Job> list = jobService.getJobList(keyword, page, pageSize);
        return Result.success(list);
    }

    // // 获取兼职详情1
//    @GetMapping("/{id}")
//    public Result getJobById(@PathVariable Long id) {
//        Job job = jobService.getById(id);
//        if (job == null) {
//            return Result.error("兼职不存在");
//        }
//        return Result.success(job);
//    }


     ///------  获取兼职2
     @GetMapping("/detail/{id}")
     public Result getJobDetailWithBusiness(@PathVariable Long id) {
         Map<String, Object> detail = jobService.getJobDetailWithBusiness(id);
         if (detail == null) {
             return Result.error("兼职不存在");
         }
         return Result.success(detail);
     }

    ///------  获取兼职3
    @GetMapping("/{id}")
    public Result getJobDetail(@PathVariable Long id) {
        Map<String, Object> data = jobService.getJobDetail(id);
        return Result.success(data);
    }

    /// --- 发布兼职
    @PostMapping
    public Result add(@RequestBody Job job) {
        jobService.add(job);
        return Result.success();
    }
}