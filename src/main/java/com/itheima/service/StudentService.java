package com.itheima.service;

import com.itheima.pojo.Student;

public interface StudentService {
    Student getByUserId(Long userId);
    void update(Student student);
}