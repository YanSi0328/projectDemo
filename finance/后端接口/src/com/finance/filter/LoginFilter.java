package com.finance.filter;

import com.alibaba.fastjson.JSON;
import com.finance.cons.ReturnCode;
import com.finance.cons.ReturnWeb;
import com.finance.entity.AdminUser;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * ClassName: LoginFilter
 * Author: ZhangCi
 *
 * @description: 登录过滤器
 * @date: 2021/5/19 15:07
 * @version: 0.1
 * @since: 1.8
 */
@WebFilter("/*")
public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("登录 过滤器被创建了");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 1 把对象装换 HttpServletRequest
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        // 乱码
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json;charset=utf-8");

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

        // 2 获取登录时放入session中的用户名
        HttpSession session = req.getSession();;
        // 放的是对象，取的是字符串，所有一直都阻塞在这
        AdminUser loginUser = (AdminUser) session.getAttribute("loginUser");
        System.out.println("登录用户 " + loginUser);
        // 3 获取用户本次登录的路径
        String currentPath = req.getServletPath();
        System.out.println("当前访问路径 " + currentPath);

        // 老师版
//        if (!"/login".equals(req.getServletPath()) && !"/loginOut".equals(req.getServletPath()) && !req.getServletPath().endsWith(".html") && !req.getServletPath().endsWith(".css") && !req.getServletPath().endsWith(".js") && !req.getServletPath().endsWith(".png") && !req.getServletPath().endsWith(".jpg") && !req.getServletPath().endsWith(".jpeg")) {
//            if (loginUser != null) {
//                filterChain.doFilter(req, resp);
//            } else {
//                resp.setContentType("text/html;charset=utf-8");
//                ReturnWeb re = new ReturnWeb();
//                re.setCode(ReturnCode.NO_LOGIN.getCode());
//                re.setReturnMsg(ReturnCode.NO_LOGIN.getMsg());
//                String res = JSON.toJSONString(re);
//                PrintWriter pw = resp.getWriter();
//                pw.print(res);
//                pw.flush();
//                pw.close();
//            }
//        } else {
//            filterChain.doFilter(req, resp);
//        }


        // 4 设置登录白名单：以jsp结尾和路径为/login不进行拦截
        if ("/login".equals(currentPath) || currentPath.endsWith(".jsp")) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            // 5 拦截处理：若用户名不为空，说明当前有用户登录，不进行拦截
            if (loginUser == null) {
                // 6 拦截：输出提示未登录
                System.out.println("未登录，未登录");
                PrintWriter writer = resp.getWriter();
                writer.print(JSON.toJSONString(
                        new ReturnWeb(ReturnCode.NO_LOGIN.getCode(), ReturnCode.NO_LOGIN.getMsg())));
                writer.flush();
                writer.close();
            }else {
                filterChain.doFilter(servletRequest, servletResponse);
            }
        }
    }

    @Override
    public void destroy() {
        System.out.println("登录 过滤器被销毁了");
    }
}
