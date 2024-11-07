/**
 * 功能：
 * 作者：
 * 日期：2024/5/4 下午1:08
 */
package com.example.studyspringboot.common;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.studyspringboot.entity.User;
import com.example.studyspringboot.exception.ServiceException;
import com.example.studyspringboot.mapper.UserMapper;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JwtInterceptor implements HandlerInterceptor {
/*
    @Resource
    private UserMapper userMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){

//        //放行登录请求
//        if (request.getRequestURI().contains("/user/login")) {
//            return true;
//        }
//
//        //放行OPTIONS请求
//        String method = request.getMethod();
//        if ("OPTIONS".equals(method)) {
//            return true;
//        }
//
//        //取出请求头中的token
//        String token = request.getHeader("token");
//        System.out.println("token: " + token);
//        if (StringUtils.isEmpty(token)) {
//            response.setStatus(HttpStatus.UNAUTHORIZED.value());
//            return false;
//        }


        //1.拿到token
        String token = request.getHeader("token");
        if (StrUtil.isBlank(token)) {
            token=request.getParameter("token");//url里面传的token参数 如 ?toke=xxx
        }

        //自定义注解通过
        if(handler instanceof HandlerMethod){
            AuthAccess annotation = ((HandlerMethod) handler).getMethodAnnotation(AuthAccess.class);
            if(annotation != null){
                return true;
            }
        }

        //2.验证token有没有
        if(token==null){
            throw new ServiceException("401","请登录");
        }

        //3.拿到user的id并去查询数据库有没有这个user
        String userId;
        try {
            //JWT.decode(token)解码 ，getAudience().get(0)拿到Audience的第一个数据
            userId = JWT.decode(token).getAudience().get(0);
        }catch (JWTDecodeException j){
            throw new ServiceException("401","请登录");
        }
        User user = userMapper.selectById(Integer.valueOf(userId));
        if(user==null){
            throw new ServiceException("401","请登录");
        }
        //4.使用user的密码生成验证器，通过验证器验证token
        //通过用户密码加密之后 生成一个验证器
        JWTVerifier jwtVerifier=JWT.require(Algorithm.HMAC256(user.getPassword())).build();
        try {
            jwtVerifier.verify(token);
        }catch (JWTVerificationException j){
            throw new ServiceException("401  ","请登录");
        }
        return true;
    }*/
}
