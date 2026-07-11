package com.itheima.service;

import com.itheima.pojo.Notice;

import java.util.List;

public interface NoticeService {
    List<Notice> getNotice();

    // 新增
    void addNotice(Notice notice);
}
