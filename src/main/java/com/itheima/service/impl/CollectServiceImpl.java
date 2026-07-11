package com.itheima.service.impl;

import com.itheima.mapper.CollectMapper;
import com.itheima.pojo.Collect;
import com.itheima.service.CollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CollectServiceImpl implements CollectService {

    @Autowired
    private CollectMapper collectMapper;


    //根据登录的用户 去查询各自的收藏集合  由于我使用了JWT技术 前面已经设计好 返回JWT令牌
    @Override
    public List<Map<String, Object>> getMyCollect(Long userId) {
        return collectMapper.getMyCollect(userId);
    }

    @Override
    public void deleteCollect(Long id) {
        collectMapper.deleteCollect(id);
    }

   //实现收藏
    @Override
    public void addCollect(Collect collect) {
        collectMapper.addCollect(collect);
    }


     //防止重复添加
    @Override
    public int checkExists(Long userId, Long jobId) {
        return collectMapper.checkExists(userId, jobId);
    }


     //检查当前的job 登录用户是否收藏了
    @Override
    public boolean checkCollected(Long userId, Long jobId) {
        return collectMapper.checkCollected(userId, jobId) > 0;
    }
}