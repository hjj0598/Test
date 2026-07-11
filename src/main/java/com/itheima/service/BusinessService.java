package com.itheima.service;

import com.itheima.pojo.Business;

public interface BusinessService {
    //通过当前登录的id 去获得 商家的所有信息
    Business getByUserId(Long userId);

    // 跟新我的商家信息
    void update(Business business);
}