package com.itheima.filter;

import com.alibaba.fastjson.JSONObject;
import com.itheima.pojo.Result;
import com.itheima.utils.JwtUtils;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.IOException;

@Slf4j
@WebFilter(urlPatterns = "/*")
public class LoginCheckFilter implements Filter {


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
                //1 获取请求对应的url
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse resp= (HttpServletResponse) servletResponse;

        //2.判断请求url中是否包含login，如果包含 说明是登录操作，放行。
        String url=request.getRequestURI().toString();
        log.info("请求的url:{}",url);

        if(url.contains("login")){
            log.info("登录操作，放行。。。");
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }

        //3.获取请求头中的令牌(token)
      String jwt=  request.getHeader("token");
        //4.判断令牌是否存在，如果不存在，返回错误结果（未登录）
        if(!StringUtils.hasLength(jwt)){
            log.info("请求头token为空，返回未登录的信息");
           Result error= Result.error("没有登录");
           //手动的转换 对象--json-----》阿里巴巴  fastJson
         String notLogin=   JSONObject.toJSONString(error);
         resp.getWriter().write(notLogin);
         return;
        }
        //5.解析token,如果解析失败，返回错误结果（未登录）。
        try {
            JwtUtils.parseJWT(jwt); //解析不报错，就是成功
        }catch (Exception e){//解析失败
            e.printStackTrace();
            log.info("解析令牌失败，返回未登录的信息");
            Result error= Result.error("没有登录");
            //手动的转换 对象--json-----》阿里巴巴  fastJson
            String notLogin=   JSONObject.toJSONString(error);
            resp.getWriter().write(notLogin);
            return;
        }
        //6.放行
        log.info("令牌合法，放行");
        filterChain.doFilter(servletRequest,servletResponse);

    }


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }


}
