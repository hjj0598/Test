package com.itheima.controller;


import com.itheima.pojo.Result;
import com.itheima.pojo.User;
import com.itheima.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class UserController {
      @Autowired
      private UserService userService;

      @PostMapping("/register")
    public Result  registerUser(@RequestBody User user){
          log.info("用户注册 {}",user);
          userService.register(user);

        return Result.success();
    }
}
