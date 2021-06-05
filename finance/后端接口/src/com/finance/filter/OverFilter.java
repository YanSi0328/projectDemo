package com.finance.filter;

import com.alibaba.fastjson.JSON;
import com.finance.cons.ReturnCode;
import com.finance.cons.ReturnWeb;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * ClassName: OverFilter
 * Author: ZhangCi
 *
 * @description: 全局权限控制过滤器
 * @date: 2021/5/19 17:16
 * @version: 0.1
 * @since: 1.8
 */
//@WebFilter("/*")
public class OverFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("全局权限控制过滤器 被创建了");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("全局权限控制过滤器 被执行了");
        // 1 对象转换
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        // 2 获取session：登录时向session存放的关于对应用户的权限信息(以集合的方式存放)
        List<String> userAuthority = (List<String>) request.getSession().getAttribute("adminRole");
        System.out.println("菜单权限 " + userAuthority);
        // 3 获得当前用户的本次访问路径
        String currentPath = request.getServletPath();
        // 4 过滤器白名单
        if ("/login".equals(currentPath)) {
            // 若访问本路径时有用户已经登录，则放行
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            // 5 检查用户权限列表中是否包含此次访问的路径
            if (userAuthority.contains(currentPath)) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                // 6 过滤拦截
                servletResponse.setContentType("application/json;charset=utf-8");
                PrintWriter writer = servletResponse.getWriter();
                writer.print(JSON.toJSONString(
                        new ReturnWeb(ReturnCode.NO_AUTHORITY.getCode(), ReturnCode.NO_AUTHORITY.getMsg())));
                writer.flush();
                writer.close();
            }
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {
        System.out.println("全局权限控制过滤器 被销毁了");
    }
}
