package com.itheima.controller;


import com.itheima.pojo.Result;
import com.itheima.pojo.User;
import com.itheima.service.UserService;
import com.itheima.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class LoginController {

    @Autowired
    UserService userService;


    @PostMapping("/login")
    public Result login(@RequestBody User user){
           log.info("用户登录 {}",user);
        User e=  userService.login(user);
        //判断：登录用户是否存在
        //判断：登录用户是否存在
        if(e !=null ){
            //自定义信息
            Map<String , Object> claims = new HashMap<>();
            claims.put("id", e.getId());
            claims.put("role",e.getRole());


            //使用JWT工具类，生成身份令牌
            String jwt = JwtUtils.generateJwt(claims);//jwt包含了当前员工的信息
            return Result.success(jwt);
        }
        return  e!=null?Result.success():Result.error("用户名或者密码错误");
    }
}
