package com.finance.controller;

import com.finance.cons.ReturnCode;
import com.finance.cons.ReturnWeb;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author:HuJingJing
 * @className: LoginOutServlet
 * @deacription:
 * @date: 2021/6/2 16:22
 * @version: 0.1
 * @since: 1.8
 */
@WebServlet("/logout")
public class LoginOutServlet extends HttpServlet {

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

        req.setCharacterEncoding("utf-8");
        ReturnWeb re = new ReturnWeb();
        HttpSession session = req.getSession();
        //移出访问控制
        session.removeAttribute("loginUser");
        //移出权限访问控制  展示菜单
        session.removeAttribute("adminRole");

        re.setCode(ReturnCode.STATUS_SUCCESS.getCode());
        re.setReturnMsg(ReturnCode.STATUS_SUCCESS.getMsg());
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter writer = resp.getWriter();
        writer.print(re);
        writer.flush();
        writer.close();
    }
}
