package com.finance.controller;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.finance.cons.PageInfo;
import com.finance.cons.ReturnCode;
import com.finance.cons.ReturnWeb;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * ClassName: BaseServlet
 * Author: ZhangCi
 *
 * @description: 控制层父类
 * @date: 2021/5/20 22:08
 * @version: 0.1
 * @since: 1.8
 */
public class BaseServlet extends HttpServlet {


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json;charset=utf-8");


        /* 允许跨域的主机地址 ：必须指定地址(前端配好的) */
        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:8088");
        /* 允许跨域的请求方法GET, POST, HEAD 等 */
        resp.setHeader("Access-Control-Allow-Methods", "*");
        /* 重新预检验跨域的缓存时间 (s) */
        resp.setHeader("Access-Control-Max-Age", "3600");
        /* 允许跨域的请求头 */
        resp.setHeader("Access-Control-Allow-Headers", "*");
        /* 是否携带cookie */
        resp.setHeader("Access-Control-Allow-Credentials", "true");

        // 实现一个servlet中可以根据不同的业务请求访问多次
        // 重写方法细分规则
        // 需要让servlet执行的时候，不同的方法进入同一个servlet   /prod/*
        // 根据传入的路径，获取需要调用的方法
        // 获得 servlet配置的路径
        String servletPath = req.getServletPath();
        // 统一资源标识符
        String requestURI = req.getRequestURI();
        //System.out.println(requestURI);
        String methodName = requestURI.substring(requestURI.lastIndexOf("/") + 1);

        // 对方法名进行路径的处理
        try {
            Method method = this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            method.invoke(this, req, resp);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            // 找不到对应的路径时跳转到 404 页面
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    /**
     * 将信息输出到客户端
     *
     * @param resp   响应信息
     * @param object 待返回的数据
     * @throws IOException
     */
    public static void writeValue(HttpServletResponse resp, Object object) throws IOException {
        resp.setContentType("application/json;charset=utf-8");
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(resp.getOutputStream(), object);
    }

    /**
     * 含有localDataTime的输出，解决jackson 不能解决的时间戳问题
     *
     * @param resp
     * @param object
     * @throws IOException
     */
    public static void writeTime(HttpServletResponse resp, Object object) throws IOException {
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter writer = resp.getWriter();
        writer.print(JSON.toJSONString(object));
        writer.flush();
        writer.close();
    }


    /**
     * 返回成功的信息，谁调用返回给谁
     *
     * @param returnData 待封装的数据
     * @param pInfo      页码信息
     * @return 封装完成功信息后的实体
     */
    public static ReturnWeb reSuccess(Object returnData, PageInfo pInfo) {
        return new ReturnWeb(
                ReturnCode.STATUS_SUCCESS.getCode(),
                ReturnCode.STATUS_SUCCESS.getMsg(),
                returnData, pInfo);
    }

    /**
     * 只返回数据
     *
     * @param returnData 待封装的数据
     * @return 封装完成功信息后的实体
     */
    public static ReturnWeb reValue(Object returnData) {
        return new ReturnWeb(
                ReturnCode.STATUS_SUCCESS.getCode(),
                ReturnCode.STATUS_SUCCESS.getMsg(), returnData);
    }

    /**
     * 返回错误的信息
     *
     * @return 封装完成功信息后的实体
     */
    public static ReturnWeb reError() {
        return new ReturnWeb(
                ReturnCode.STATUS_ERROR.getCode(),
                ReturnCode.STATUS_ERROR.getMsg());
    }

    /**
     * 返回错误信息
     *
     * @param errMsg 具体错误信息
     * @return 封装完成功信息后的实体
     */
    public static ReturnWeb reError(String errMsg) {
        return new ReturnWeb(
                ReturnCode.STATUS_ERROR.getCode(),
                ReturnCode.STATUS_ERROR.getMsg(),
                errMsg);
    }
}
