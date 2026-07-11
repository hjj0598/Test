package com.itheima.exception;


import com.itheima.pojo.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/*
*   自己定义了一个全局异常处理器
*  出错
* */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public Result exception(Exception e){
        e.printStackTrace();
        return Result.error("对不起操作失败详细的问题可以，联系管理员 电话好嘛 16673365841");
    }
}
