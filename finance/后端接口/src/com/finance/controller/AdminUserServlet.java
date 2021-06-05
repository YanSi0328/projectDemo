package com.finance.controller;

import com.alibaba.fastjson.JSON;
import com.finance.cons.ReturnCode;
import com.finance.cons.ReturnWeb;
import com.finance.entity.AdminUser;
import com.finance.cons.PageInfo;
import com.finance.entity.Menu;
import com.finance.service.AdminUserService;
import com.finance.service.impl.AdminUserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author:HuJingJing
 * @className: AdminUserServlet
 * @deacription:
 * @date: 2021/5/28 21:53
 * @version: 0.1
 * @since: 1.8
 */
@WebServlet("/admin/*")
public class AdminUserServlet extends BaseServlet {


    public void query(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        AdminUserServiceImpl ads = new AdminUserServiceImpl();
        String adminName = req.getParameter("adminName");
        String adminId = req.getParameter("adminId");
//        String adminId = req.getParameter("adminId");
        Integer adminPassword = 0;
        if (adminId != null && !"".equals(adminId)) {
            adminPassword = Integer.valueOf(adminId);
        }
        AdminUser queryAd = new AdminUser();
        queryAd.setAdminId(adminPassword);
        queryAd.setAdminName(adminName);

        String pageStr = req.getParameter("page");
        String pageSizeStr = req.getParameter("pageSize");
        Integer page = 1;
        Integer pageSize = 3;
        if (pageStr != null && !"".equals(pageStr)) {
            page = Integer.valueOf(pageStr);
        }
        if (pageSizeStr != null && !"".equals(pageSizeStr)) {
            pageSize = Integer.valueOf(pageSizeStr);
        }
        List<AdminUser> adminList = ads.queryAdmin(page, pageSize, queryAd);
        Integer total = ads.getAdminNum(queryAd);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPage(page);
        pageInfo.setPageSize(pageSize);
        pageInfo.setTotal(total);
        ReturnWeb re = new ReturnWeb();
        re.setCode(ReturnCode.STATUS_SUCCESS.getCode());
        re.setReturnMsg(ReturnCode.STATUS_SUCCESS.getMsg());
        re.setReturnData(adminList);
        re.setPageInfo(pageInfo);
        String s = JSON.toJSONString(re);
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter writer = resp.getWriter();
        writer.print(s);
        writer.flush();
        writer.close();


    }

    public void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");

        String parameter = req.getParameter("adminId");
        String adminName = req.getParameter("adminName");
        String roleId = req.getParameter("roleId");
        String adminStatus = req.getParameter("adminStatus");
        String adminPassword = req.getParameter("adminPassword");
        String adminImg = req.getParameter("adminImg");

        Integer adminId = 0;
        if (parameter != null && !"".equals(parameter)) {
            adminId = Integer.valueOf(parameter);
        }
        AdminUser ad = new AdminUser();
        ad.setAdminId(adminId);
        ad.setAdminName(adminName);
        ad.setRoleId(Integer.parseInt(roleId));
        ad.setAdminStatus(adminStatus);
        ad.setAdminPassword(adminPassword);
        ad.setAdminImg(adminImg);

        AdminUserServiceImpl adm = new AdminUserServiceImpl();
        Integer res = adm.addAdmin(ad);
        ReturnWeb re = new ReturnWeb();
        if (res > 0) {
            re.setCode(ReturnCode.STATUS_SUCCESS.getCode());
            re.setReturnMsg(ReturnCode.STATUS_SUCCESS.getMsg());
        } else {
            re.setCode(ReturnCode.STATUS_ERROR.getCode());
            re.setReturnMsg(ReturnCode.STATUS_ERROR.getMsg());

            resp.setContentType("text/html;charset=utf-8");
            String s = JSON.toJSONString(re);
            PrintWriter writer = resp.getWriter();
            writer.print(s);
            writer.flush();
            writer.close();
        }
    }

    public void edit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String adminName = req.getParameter("adminName");
        String roleIdStr = req.getParameter("roleId");
        String adminStatus = req.getParameter("adminStatus");
        String adminPassword = req.getParameter("adminPassword");
        String adminImg = req.getParameter("adminImg");
        String adminId = req.getParameter("adminId");

        String menuid = req.getParameter("id");

        Integer roleId = 0;
        if (roleIdStr != null && !"".equals(roleIdStr)) {
            roleId = Integer.valueOf(roleIdStr);
        }
        AdminUser ad = new AdminUser();
        ad.setAdminName(adminName);
        ad.setRoleId(roleId);
        ad.setAdminStatus(adminStatus);
        ad.setAdminPassword(adminPassword);
        ad.setAdminImg(adminImg);
        ad.setAdminId(Integer.parseInt(adminId));
        ad.setMenuId(menuid);

        AdminUserServiceImpl adm = new AdminUserServiceImpl();
        //Integer res = adm.addAdmin(ad);
        Integer res = adm.editAdmin(ad);
        ReturnWeb re = new ReturnWeb();
        if (res > 0) {
            re.setCode(ReturnCode.STATUS_SUCCESS.getCode());
            re.setReturnMsg(ReturnCode.STATUS_SUCCESS.getMsg());
        } else {
            re.setCode(ReturnCode.STATUS_ERROR.getCode());
            re.setReturnMsg(ReturnCode.STATUS_ERROR.getMsg());

            resp.setContentType("text/html;charset=utf-8");
            String s = JSON.toJSONString(re);
            PrintWriter writer = resp.getWriter();
            writer.print(s);
            writer.flush();
            writer.close();

        }
    }

    public void edituserauth(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        ReturnWeb re = new ReturnWeb();
        AdminUserServiceImpl adm = new AdminUserServiceImpl();
        String adminId = req.getParameter("adminId");
//            String id= req.getParameter("id");
        System.out.println(adminId + "是数组吗");
        String md = req.getParameter("menuId");
        System.out.println("=================");
        System.out.println(md);

//            Integer menuId = 0;
//            if (md != null && !"".equals(md)) {
//                menuId = Integer.valueOf(md);
//            }
        AdminUser ad = new AdminUser();
        ad.setAdminId(Integer.parseInt(adminId));
        ad.setMenuId(md);
//            ad.setAdminId(Integer.parseInt(id));

        Integer res = adm.editUserAuth(ad);
        if (res > 0) {
            re.setCode(ReturnCode.STATUS_SUCCESS.getCode());
            re.setReturnMsg(ReturnCode.STATUS_SUCCESS.getMsg());
        } else {
            re.setCode(ReturnCode.STATUS_ERROR.getCode());
            re.setReturnMsg(ReturnCode.STATUS_ERROR.getMsg());

        }
        //  菜单数组
        List<Menu> allAuth = adm.getAllAuth();
        String currentUserAuth = adm.getCurrentUserAuth(ad);
        System.out.println("所有菜单");
        System.out.println(allAuth);
        String resstr = JSON.toJSONString(re);
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter writer = resp.getWriter();
        writer.print(resstr);
        writer.flush();
        writer.close();
    }

    public void del(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("utf-8");
        String adminId = req.getParameter("adminId");
        AdminUser ad = new AdminUser();
        AdminUserServiceImpl adm = new AdminUserServiceImpl();
        ad.setAdminId(Integer.parseInt(adminId));
        Integer res = adm.delAdmin(ad.getAdminId());
        ReturnWeb re = new ReturnWeb();
        if (res > 0) {
            re.setCode(ReturnCode.STATUS_SUCCESS.getCode());
            re.setReturnMsg(ReturnCode.STATUS_SUCCESS.getMsg());
        } else {
            re.setCode(ReturnCode.STATUS_ERROR.getCode());
            re.setReturnMsg(ReturnCode.STATUS_ERROR.getMsg());

            resp.setContentType("text/html;charset=utf-8");
            String s = JSON.toJSONString(re);
            PrintWriter writer = resp.getWriter();
            writer.print(s);
            writer.flush();
            writer.close();

        }
    }  //修改密码

    public void editPass(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Integer adminId = (Integer) session.getAttribute("adminId");
        ReturnWeb re = new ReturnWeb();
        if (adminId == null) {
            re.setCode(ReturnCode.LOGIN_FAILED.getCode());
            re.setReturnMsg(ReturnCode.LOGIN_FAILED.getMsg());
        } else {
            String adminPass = req.getParameter("adminPass");
            String newPass = req.getParameter("newpass");

            AdminUserService as = new AdminUserServiceImpl();
            String passById = as.getPassById(adminId);
            if (!passById.equals(adminPass)) {
                re.setCode(ReturnCode.STATUS_ERROR.getCode());
                re.setReturnMsg(ReturnCode.STATUS_ERROR.getMsg());
            } else {
                Integer integer = as.editPassword(adminId, newPass);
                if (integer > 0) {
                    re.setCode(ReturnCode.LOGIN_SUCCESS.getCode());
                    re.setReturnMsg(ReturnCode.LOGIN_SUCCESS.getMsg());
                } else {
                    re.setCode(ReturnCode.STATUS_ERROR.getCode());
                    re.setReturnMsg(ReturnCode.STATUS_ERROR.getMsg());
                }
            }

        }
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter writer = resp.getWriter();
        writer.write(JSON.toJSONString(re));
        writer.flush();
        writer.close();
    }

    //权限
    public void getauth(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        ReturnWeb re = new ReturnWeb();
        AdminUser adminUser1 = new AdminUser();

        AdminUserServiceImpl adminUserService = new AdminUserServiceImpl();
        String adminIdstr = req.getParameter("adminId");
        System.out.println(adminIdstr + 55555);
        //  要往返回对象中加的。
//         int[] roles = Arrays.asList(adminIdstr.split(",")).stream().mapToInt(Integer::parseInt).toArray();

        Integer adminId = null;
        if (adminIdstr != null && !"".equals(adminIdstr)) {
            adminId = Integer.valueOf(adminIdstr);
        }
        AdminUser adminUser = new AdminUser();
        adminUser.setAdminId(adminId);
        //  菜单数组
        List<Menu> allAuth = adminUserService.getAllAuth();
        String currentUserAuth = adminUserService.getCurrentUserAuth(adminUser);
        System.out.println("所有菜单");
        System.out.println(allAuth);
//           father : 最后要往返回对象中加的。
/*         List<Menu> father = allAuth.stream().filter(o -> o.getPId() == 0).collect(Collectors.toList());
         List<Menu> children = allAuth.stream().filter(o -> o.getPId() != 0).collect(Collectors.toList());



         father.stream().forEach(f->{
             children.stream().forEach(c->{
                 if (c.getPId()==f.getId()){
                     boolean add1 = f.getSubmenu().add(c);
                     System.out.println(add1+"526534");
                 }

             });
         });*/
//         String currentUserAuth = adminUserService.getCurrentUserAuth(adminUser);
/*         System.out.println("888888888");
         System.out.println(father);*/
        re.setCode(ReturnCode.STATUS_SUCCESS.getCode());
        re.setReturnMsg(ReturnCode.STATUS_SUCCESS.getMsg());
        //权限列表数据
        re.setReturnData(allAuth);
        System.out.println(allAuth);
        //额外数据
        re.setExtData(currentUserAuth);
        String resstr = JSON.toJSONString(re);
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter writer = resp.getWriter();
        writer.print(resstr);
        writer.flush();
        writer.close();
    }

}