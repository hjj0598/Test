package com.itheima.controller;

import com.itheima.pojo.Business;
import com.itheima.pojo.Result;
import com.itheima.service.BusinessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/business")
public class BusinessController {

    @Autowired
    private BusinessService businessService;

    /**
     * 根据用户ID查询商家信息
     */
    @GetMapping("/info")
    public Result getInfo(@RequestParam Long userId) {
        Business business = businessService.getByUserId(userId);
        return Result.success(business);
    }

    /**
     * 更新商家信息
     */
    @PostMapping("/update")
    public Result update(@RequestBody Business business) {
        businessService.update(business);
        return Result.success();
    }
}