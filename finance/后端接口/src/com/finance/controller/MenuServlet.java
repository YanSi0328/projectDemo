package com.finance.controller;

import com.alibaba.fastjson.JSON;
import com.finance.cons.PageInfo;
import com.finance.cons.ReturnCode;
import com.finance.cons.ReturnWeb;
import com.finance.dao.LoginDao;
import com.finance.dao.MenuDao;
import com.finance.dao.impl.LoginDaoImpl;
import com.finance.dao.impl.MenuDaoImpl;
import com.finance.entity.AdminUser;
import com.finance.entity.Menu;
import com.finance.service.MenuService;
import com.finance.service.impl.MenuServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: MeunServlet
 * Author: ZhangCi
 *
 * @description: 查询菜单信息
 * @date: 2021/5/22 17:40
 * @version: 0.1
 * @since: 1.8
 */
@WebServlet("/menu/*")
public class MenuServlet extends BaseServlet {
    private MenuService menuService = new MenuServiceImpl();
    private ReturnWeb returnWeb;

    // 该方法可以根据字段查询，但此处仅做：根据菜单名和父菜单的查询
    public void query(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> params = new HashMap<>(16);
        PageInfo pInfo = new PageInfo();
        // 获取页面参数
        String name = req.getParameter("menuName");
        String pId = req.getParameter("pId");
        String pageStr = req.getParameter("page");
        String pageSizeStr = req.getParameter("pageSize");
        System.out.println("name " + name);
        System.out.println("pname " + pId);
        // 对菜单名的处理
        if (name != null) {
            // 此时说明客户端想要根据菜单名进行查询，那么此时需要将对应的字段名和值传入参数集合
            // key:name   value:"name"
            params.put("name", name);
        }
        // 对父菜单的处理同上
        if (pId != null) {
            params.put("pid", pId);
        }
        // 对页码的处理

        Integer page = 1;
        Integer pageSize = 10;
        if (pageStr != null && !"".equals(pageStr)) {
            page = Integer.parseInt(pageStr);
        }
        if (pageSizeStr != null && !"".equals(pageSizeStr)) {
            pageSize = Integer.parseInt(pageSizeStr);
        }

        System.out.println("查询的参数列表是 " + params);
        // 执行查询
        Integer total = menuService.getMenuTotal(params);
        List<Menu> menuByPage = menuService.getMenuByPage(page, pageSize, params);
        // 封装页码信息
        pInfo.setPage(page);
        pInfo.setPageSize(pageSize);
        pInfo.setTotal(total);

        System.out.println(page);
        System.out.println(pageSize);
        System.out.println(total);

        if (menuByPage.size() != 0) {
            // success
            returnWeb = BaseServlet.reSuccess(menuByPage, pInfo);
        } else {
            returnWeb = BaseServlet.reError();
        }

        // 将查询结果输出到客户端
        BaseServlet.writeValue(resp, returnWeb);
        System.out.println(" 《根据客户端传入的数据进行查询》");
    }

    public void queryPMenu(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Menu> parentMenu = menuService.getParentMenu();
        BaseServlet.writeValue(resp, BaseServlet.reSuccess(parentMenu, new PageInfo()));
    }

    public void showMenu(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> params = new HashMap<>(16);

        List<Menu> allMenu = menuService.getAllMenu();
        Integer total = menuService.getMenuTotal(params);
        returnWeb = BaseServlet.reSuccess(allMenu, new PageInfo(1, 10, total));
        BaseServlet.writeValue(resp, returnWeb);
        System.out.println(" 《00000000000000000000》");


    }

    public void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 获取菜单参数
        Integer id = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        Integer pid = Integer.parseInt(req.getParameter("pid"));
        String pname = req.getParameter("pname");
        String address = req.getParameter("address");
        String icon = req.getParameter("icon");
        // 执行新增
        Integer record = menuService.addMenuMsg(new Menu(id, name, pid, pname, address, icon));
        if (record.equals(1)) {
            // success
            returnWeb = BaseServlet.reSuccess("新增" + record + "条菜单记录", new PageInfo());
        } else {
            // error
            returnWeb = BaseServlet.reError();
        }
        BaseServlet.writeValue(resp, returnWeb);
        System.out.println(" 《新增》");
    }

    public void modify(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取菜单参数
        Integer id = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        Integer pid = Integer.parseInt(req.getParameter("pid"));
        String pname = req.getParameter("pname");
        String address = req.getParameter("address");
        String icon = req.getParameter("icon");
        Menu modMenu = new Menu(id, name, pid, pname, address, icon);
        // 执行修改
        Integer record = menuService.modifyMenu(modMenu);
        if (record == 1) {
            // success
//            returnWeb = BaseServlet.reSuccess("修改" + record + "条菜单记录");
        } else {
            // error
            returnWeb = BaseServlet.reError();
        }
        BaseServlet.writeValue(resp, returnWeb);
        System.out.println(" 《修改》 ");
    }


    public void delById(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = Integer.parseInt(req.getParameter("id"));
        System.out.println("菜单id " + id);
        // 执行查询
        Integer record = menuService.delMenuById(id);
        if (record != null) {
            // success
//            returnWeb = BaseServlet.reSuccess("删除 " + record + " 条菜单");
        } else {
            // error
            returnWeb = BaseServlet.reError();
        }
        BaseServlet.writeValue(resp, returnWeb);
        System.out.println(" 《删除》 " + id);
    }

    public void roleMenu(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        MenuDao menuDao=new MenuDaoImpl();
        LoginDao loginDao=new LoginDaoImpl();
        AdminUser adminUser=new AdminUser();
        HttpSession session = req.getSession();
        Object adminRole = session.getAttribute("adminRole");
        Object loginUser = session.getAttribute("loginUser");
        ReturnWeb re=new ReturnWeb();
        re.setCode(ReturnCode.STATUS_SUCCESS.getCode());
        re.setReturnMsg(ReturnCode.STATUS_SUCCESS.getMsg());
        re.setExtData( adminRole);
        re.setReturnData(loginUser);
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter writer = resp.getWriter();
        writer.print(JSON.toJSONString(re));
        writer.flush();
        writer.close();

    }

}
