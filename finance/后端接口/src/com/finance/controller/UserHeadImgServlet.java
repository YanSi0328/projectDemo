package com.finance.controller;

import com.alibaba.fastjson.JSON;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author:HuJingJing
 * @className: UserHeadImgServlet
 * @deacription:
 * @date: 2021/6/1 16:58
 * @version: 0.1
 * @since: 1.8
 */
@WebServlet("/upload/*")
@MultipartConfig
public class UserHeadImgServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 解决一下跨域问题
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


        System.out.println("请求到");
        Part file = req.getPart("myfile");
        System.out.println("取到的文件"+file.getName());
        System.out.println("取到的文件2"+file.getSubmittedFileName());
        String basePath = req.getServletContext().getRealPath("/");
        String folder = "picture/";
        System.out.println(basePath+folder+file.getSubmittedFileName());
        file.write(basePath+folder+file.getSubmittedFileName());
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter writer = resp.getWriter();
        Map<String,Object> returnMap = new HashMap<String,Object>();
        returnMap.put("imgsrc","http://localhost:8080/finance/"+folder+file.getSubmittedFileName());
        writer.print(JSON.toJSONString(returnMap));
        writer.flush();
        writer.close();
    }
}
