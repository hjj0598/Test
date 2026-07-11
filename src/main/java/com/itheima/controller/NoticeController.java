package com.itheima.controller;

import com.itheima.pojo.Notice;
import com.itheima.pojo.Result;
import com.itheima.service.NoticeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class NoticeController {

      @Autowired
      private NoticeService noticeService;


      //获取所有的notice
     @GetMapping("/admin/notice")
    public Result notice() {

        List<Notice> list = noticeService.getNotice();
        return Result.success(list);
    }


       //admin 用户可以发布

    // 新增：管理员发布公告（必加！）
    // ======================
    @PostMapping("/admin/notice")
    public Result addNotice(@RequestBody Notice notice) {
        noticeService.addNotice(notice);
        return Result.success();
    }
}
