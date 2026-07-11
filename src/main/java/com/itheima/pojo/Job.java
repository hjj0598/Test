package com.itheima.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Job {
    private Long id;
    private String title;
    private String content;
    private String salary;
    private String type;
    private String address;
    private Integer num;
    private Long businessId; // 驼峰对应 business_id
    private LocalDateTime publishTime; // 驼峰对应 publish_time
    private Integer status;
}