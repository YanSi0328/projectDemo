package com.finance.controller;

import com.alibaba.fastjson.JSON;
import com.finance.cons.ReturnCode;
import com.finance.cons.ReturnWeb;
import com.finance.entity.AdminUser;
import com.finance.entity.Menu;
import com.finance.service.impl.LoginServerImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author:HuJingJing
 * @className: LoginServlet
 * @deacription:
 * @date: 2021/5/28 17:42
 * @version: 0.1
 * @since: 1.8
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("触发了");
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
        String adminName = req.getParameter("adminName");
        String adminPassword = req.getParameter("adminPassword");

        //输入账号和密码
        AdminUser insertUser = new AdminUser(adminName, adminPassword);
        LoginServerImpl ls = new LoginServerImpl();
        //登录
        AdminUser loginUser = ls.login(insertUser);
        System.out.println(" 0101 " + loginUser);

        ReturnWeb re = new ReturnWeb();

        if (loginUser != null) {
            //查权限 展示左侧菜单栏
            List<Menu> adminRole = ls.getAdminRole(loginUser);
            System.out.println("-----------------");
            System.out.println(adminRole);
            //去拿到用户拥有的权限
            HttpSession session = req.getSession();
            //登录访问控制
            session.setAttribute("loginUser", loginUser);
            //权限访问控制  展示菜单
            session.setAttribute("adminRole", adminRole);
            re.setCode(ReturnCode.LOGIN_SUCCESS.getCode());
            re.setReturnMsg(ReturnCode.LOGIN_SUCCESS.getMsg());
        } else {
            re.setCode(ReturnCode.LOGIN_FAILED.getCode());
            re.setReturnMsg(ReturnCode.LOGIN_FAILED.getMsg());
        }
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter pw = resp.getWriter();
        String res = JSON.toJSONString(re);
        pw.print(res);
        pw.flush();
        pw.close();
    }


//    public void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//       req.setCharacterEncoding("utf-8");
//        String adminName = req.getParameter("adminName");
//        String adminPassword = req.getParameter("adminPassword");
//        AdminUser adminUser = new AdminUser()
//        try {
//            Integer  roleId= new LoginServerImpl().login(adminUser);
//            ReturnWeb re = new ReturnWeb();//类必须创建
//
//            if (roleId!=null&&roleId!=1){
//             re.setCode(ReturnCode.LOGIN_SUCCESS.getCode());
//             re.setReturnMsg(ReturnCode.LOGIN_SUCCESS.getMsg());
//            }else {
//                re.setCode(ReturnCode.LOGIN_FAILED.getCode());
//                re.setReturnMsg(ReturnCode.LOGIN_FAILED.getMsg());
//            }
//            HttpSession session = req.getSession();
//            session.setAttribute("adminId",roleId);
//
//        resp.setContentType("application/json;charset=utf-8");
//            String s = JSON.toJSONString(re);
//            PrintWriter writer = resp.getWriter();
//            writer.print(s);
//            writer.flush();
//            writer.close();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//    }
}
