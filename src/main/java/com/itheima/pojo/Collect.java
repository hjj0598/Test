package com.itheima.pojo;

import lombok.Data;

import java.util.Date;

@Data

public class Collect {
    private Long id;
    private Long userId;
    private Long jobId;
    private Date createTime;
}