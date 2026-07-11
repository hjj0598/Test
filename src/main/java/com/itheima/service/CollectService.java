package com.itheima.service;

import com.itheima.pojo.Collect;

import java.util.List;
import java.util.Map;

public interface CollectService {
    // 获取我的收藏
    List<Map<String, Object>> getMyCollect(Long userId);

    // 删除收藏
    void deleteCollect(Long id);


     //实现收藏
    void addCollect(Collect collect);

    int checkExists(Long userId, Long jobId);

    boolean checkCollected(Long userId, Long jobId);
}
