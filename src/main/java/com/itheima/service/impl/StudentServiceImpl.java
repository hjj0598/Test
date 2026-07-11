package com.itheima.service.impl;

import com.itheima.mapper.StudentMapper;
import com.itheima.pojo.Student;
import com.itheima.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public Student getByUserId(Long userId) {
        return studentMapper.getByUserId(userId);
    }

    @Transactional
    @Override
    public void update(Student student) {

        studentMapper.updateById(student);
    }
}