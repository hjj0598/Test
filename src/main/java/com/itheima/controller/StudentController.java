package com.itheima.controller;

import com.itheima.pojo.Result;
import com.itheima.pojo.Student;
import com.itheima.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    // 根据 userId 查询学生信息
    @GetMapping("/info")
    public Result getInfo(@RequestParam Long userId) {
        Student student = studentService.getByUserId(userId);
        return Result.success(student);
    }

    // 更新学生信息
    @PostMapping("/update")
    public Result update(@RequestBody Student student) {
        studentService.update(student);
        return Result.success();
    }
}