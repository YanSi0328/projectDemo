package com.zhangci.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * ClassName: BaseServlet
 * Author: ZhangCi
 *
 * @description: 控制层父类
 * @date: 2021/5/17 10:35
 * @version: 0.1
 * @since: 1.8
 */
public class BaseServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 实现一个servlet中可以根据不同的业务请求访问多次
        // 重写方法细分规则
        // 需要让servlet执行的时候，不同的方法进入同一个servlet   /prod/*
        // 根据传入的路径，获取需要调用的方法
        // 获得 servlet配置的路径
        String servletPath = req.getServletPath();
        // 统一资源标识符
        String requestURI = req.getRequestURI();
        String methodName = requestURI.substring(requestURI.lastIndexOf("/") + 1);

        // 对方法名进行路径的处理
        try {
            Method method = this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            method.invoke(this, req, resp);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            // 找不到对应的路径时跳转到 404 页面
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
