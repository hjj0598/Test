package com.itheima.service.impl;

import com.itheima.mapper.UserMapper;
import com.itheima.pojo.User;
import com.itheima.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper  userMapper;

    @Override
    public User login(User user) {
        return userMapper.getByUsernameAndPassword(user);
    }

    // ✅ 事务：全部成功才提交，失败全部回滚
    @Transactional
    @Override
    public void register(User user) {
        // 1. 补全基础信息
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());

        // 2. 插入 user 表，并自动返回主键 id
        userMapper.register(user);

        // 3. 根据角色，自动插入对应表
        if ("student".equals(user.getRole())) {
            userMapper.insertStudent(user.getId());
        } else if ("business".equals(user.getRole())) {
            userMapper.insertBusiness(user.getId());
        }
    }
}
