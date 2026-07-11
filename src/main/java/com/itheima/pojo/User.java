package com.itheima.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 用户表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {


    private Long id;
    private String username;
    private String password;
    private String realName;
    private String phone;
    private String email;
    private String role;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}