package com.finance.controller;

import com.alibaba.fastjson.JSON;
import com.finance.cons.ReturnCode;
import com.finance.cons.ReturnWeb;
import com.finance.dao.AdminUserDao;
import com.finance.dao.impl.AdminUserDaoImpl;
import com.finance.entity.AdminUser;
import lombok.SneakyThrows;

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
 * @className: UpdatePwdServlet
 * @deacription:
 * @date: 2021/6/2 18:28
 * @version: 0.1
 * @since: 1.8
 */
@WebServlet("/updatePwd")
public class UpdatePwdServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @SneakyThrows
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
        AdminUserDao ad = new AdminUserDaoImpl();
        String pwd = req.getParameter("adminPassword");
        System.out.println(pwd);
        AdminUser user = new AdminUser();

        HttpSession session = req.getSession();
        user = (AdminUser) session.getAttribute("loginUser");
        System.out.println(user);
        //String loginUser = (String) session.getAttribute("loginUser");
        System.out.println();
        //user.setAdminName(loginUser);
        user.setAdminPassword(pwd);

        System.out.println("123===" + user.toString());
        Integer res = ad.editAdminByName(user);

        if (res > 0) {
            re.setCode(ReturnCode.STATUS_SUCCESS.getCode());
            re.setReturnMsg(ReturnCode.STATUS_SUCCESS.getMsg());
        } else {
            re.setCode(ReturnCode.STATUS_ERROR.getCode());
            re.setReturnMsg(ReturnCode.STATUS_ERROR.getMsg());
        }
        String resstr = JSON.toJSONString(re);
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter writer = resp.getWriter();
        writer.print(resstr);
        writer.flush();
        writer.close();
    }
}
