package com.itheima.service.impl;


import com.itheima.mapper.NoticeMapper;
import com.itheima.pojo.Notice;
import com.itheima.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private NoticeMapper noticeMapper;

    @Override
    public List<Notice> getNotice() {
        return  noticeMapper.list();
    }

    // 新增
    @Override
    public void addNotice(Notice notice) {
        noticeMapper.add(notice);
    }
}
