package com.itheima.service.impl;

import com.itheima.mapper.BusinessMapper;
import com.itheima.pojo.Business;
import com.itheima.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BusinessServiceImpl implements BusinessService {

    @Autowired
    private BusinessMapper businessMapper;

    @Override
    public Business getByUserId(Long userId) {

        return businessMapper.getByUserId(userId);
    }

    // 事务管理，确保数据安全
    @Transactional
    @Override
    public void update(Business business) {

        businessMapper.updateById(business);
    }
}