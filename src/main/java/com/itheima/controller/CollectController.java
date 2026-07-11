package com.itheima.controller;

import com.itheima.pojo.Collect;
import com.itheima.pojo.Result;
import com.itheima.service.CollectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@Slf4j
@RestController
@RequestMapping("/collect")
public class CollectController {

    @Autowired
    private CollectService collectService;

    // 获取我的收藏
    @GetMapping("/list")
    public Result list(@RequestParam Long userId) {
        log.info("我的列表id{}",userId);
        return Result.success(collectService.getMyCollect(userId));
    }

    // 删除收藏
    @PostMapping("/delete")
    public Result delete(@RequestParam Long id) {
        collectService.deleteCollect(id);
        return Result.success();
    }

     //实现收藏功能
//    @PostMapping("/add")
//    public Result add(@RequestBody Collect collect) {
//        collectService.addCollect(collect);
//        return Result.success();
//    }

    @PostMapping("/add")
    public Result add(@RequestBody Collect collect) {
        // 先查是否已经收藏
        int count = collectService.checkExists(collect.getUserId(), collect.getJobId());
        if (count > 0) {
            return Result.error("已收藏，请勿重复操作");
        }

        collectService.addCollect(collect);
        return Result.success();
    }

    @GetMapping("/check")
    public Result check(@RequestParam Long userId, @RequestParam Long jobId) {
        boolean collected = collectService.checkCollected(userId, jobId);
        return Result.success(collected);
    }
}