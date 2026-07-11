package com.itheima.controller;

import com.itheima.pojo.JobApply;
import com.itheima.pojo.Result;
import com.itheima.service.JobApplyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/job/apply")
public class JobApplyController {

    @Autowired
    private JobApplyService jobApplyService;

    @PostMapping
    public Result apply(@RequestBody JobApply jobApply) {
        boolean success = jobApplyService.addApply(jobApply);
        return success ? Result.success("申请成功") : Result.error("申请失败");
    }


    //  修正后：参数名改为 userId
    @GetMapping("/my")
    public Result myList(@RequestParam Long userId) {
        List<JobApply> list = jobApplyService.getMyApplies(userId);
        return Result.success(list);
    }

    // 商家接口也保持一致
    @GetMapping("/business")
    public Result businessList(@RequestParam Long userId) {
        List<JobApply> list = jobApplyService.getBusinessApplies(userId);
        return Result.success(list);
    }
    // 商家：审核
    @PostMapping("/audit")
    public Result audit(
            @RequestParam Long id,
            @RequestParam Integer status) {
        boolean success = jobApplyService.audit(id, status);
        return success ? Result.success("审核成功") : Result.error("失败");
    }

    @GetMapping("/all")
    public Result allApplies() {
        List<JobApply> list = jobApplyService.getAllApplies();
        return Result.success(list);
    }
}