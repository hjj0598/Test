package com.itheima.pojo;

import lombok.Data;

@Data
public class Business {
    private Long id;
    private Long userId;
    private String companyName;
    private String contactPerson;
    private String address;
    private String intro;
}