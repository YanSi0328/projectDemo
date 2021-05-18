package com.zhangci.controller;

import com.zhangci.etity.Product;
import com.zhangci.service.ProductService;
import com.zhangci.service.impl.ProductServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 * ClassName: ProductServlet
 * Author: ZhangCi
 *
 * @description:
 * @date: 2021/5/17 16:34
 * @version: 0.1
 * @since: 1.8
 */
@WebServlet("/prod/*")
public class ProductServlet extends BaseServlet {
    private ProductService prodService = new ProductServiceImpl();

    public void query(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("utf-8");
        String prodid = req.getParameter("prodid");
        String prodname = req.getParameter("prodname");
        Product queryProd = new Product(prodid, prodname);

        String pageStr = req.getParameter("page");
        System.out.println("pageStr" + pageStr);
        String pageSizeStr = req.getParameter("pageSize");

        // 指定首次查询的页数和页面记录数
        Integer page = 1;
        Integer pageSize = 3;
        // 一些特  殊情况：页面跳转时未进行传值和直接无相应的键
        if (pageStr != null && !"".equals(pageStr)) {
            page = Integer.parseInt(pageStr);
        }
        if (pageSizeStr != null && !"".equals(pageSizeStr)) {
            pageSize = Integer.parseInt(pageSizeStr);
        }
        // 总记录数
        Integer totalPage = prodService.selAllProdCount(pageSize, queryProd);

        // 执行查询，得到所有的查询结果
        List<Product> allProdMsg = prodService.selAllProdMsg(page, pageSize, queryProd);

        // 将数据发送至jsp页面
        req.setAttribute("queryProd", queryProd);
        req.setAttribute("page", page);
        req.setAttribute("pageSize", pageSize);
        req.setAttribute("totalPage", totalPage);
        req.setAttribute("prodList", allProdMsg);
        req.getRequestDispatcher("/product/prodMsg.jsp").forward(req, resp);
    }

    // 新增
    public void addProd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("新增商品");
        String addMsg = null;

        // 获得页面输入的信息
        String prodId = req.getParameter("prodId");
        String prodName = req.getParameter("prodName");
        BigDecimal prodPrice = new BigDecimal(req.getParameter("prodPrice"));
        String prodImg = req.getParameter("prodImg");
        String prodDesc = req.getParameter("prodDesc");
        Product addProduct = new Product(prodId, prodName, prodPrice, prodImg, prodDesc);
        System.out.println("新增商品信息 " + addProduct);

        // 调用业务层逻辑，执行新增
        Integer record = prodService.addProd(addProduct);
        // 判断是否添加成功
        if (record != 1) {
            // 添加失败
            addMsg = "新增商品信息失败";
        } else {
            addMsg = "新增商品信息成功";
        }
        System.out.println(addMsg);
        // 封装数据放入指定页面
        req.setAttribute("addMsg", addMsg);
        req.getRequestDispatcher("/product/add_flag.jsp").forward(req, resp);
    }

    // 修改
    public void updateProd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("修改指定商品信息");
        String addMsg = null;

        // 获得页面的数据
        String prodId = req.getParameter("prodId");
        String prodName = req.getParameter("prodName");
        BigDecimal prodPrice = new BigDecimal(req.getParameter("prodPrice"));
        String prodImg = req.getParameter("prodImg");
        String prodDesc = req.getParameter("prodDesc");
        Product updaProd = new Product(prodId, prodName, prodPrice, prodImg, prodDesc);
        System.out.println("修改后的商品信息 " + updaProd);

        Integer record = prodService.updatProd(updaProd);
        // 判断是否添加成功
        if (record != 1) {
            // 添加失败
            addMsg = "新增商品信息失败";
        } else {
            addMsg = "新增商品信息成功";
        }
        System.out.println(addMsg);
        // 封装数据放入指定页面
        req.setAttribute("addMsg", addMsg);
        req.getRequestDispatcher("/product/add_flag.jsp").forward(req, resp);
    }

    // 通过 id 查询指定商品
    public void selectProdById(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("通过 id 查询指定商品");
        // 获取待查询的id
        String pid = req.getParameter("pid");
        System.out.println("查询的商品id是 " + pid);

        // 执行查询
        Product prodById = prodService.selProductById(pid);
        // 封装数据放入指定页面
        req.setAttribute("prodById", prodById);
        req.getRequestDispatcher("/product/editProd.jsp").forward(req, resp);
    }

    // 通过 id 删除指定商品
    public void delProdById(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("通过 id 删除指定商品");
        String addMsg = null;

        String pid = req.getParameter("pid");
        System.out.println("删除的商品id是 " + pid);

        // 执行删除
        Integer record = prodService.delProdById(pid);
        // 判断是否添加成功
        if (record != 1) {
            // 添加失败
            addMsg = "删除商品信息失败";
        } else {
            addMsg = "删除商品信息成功";
        }
        System.out.println(addMsg);
        // 封装数据放入指定页面
        req.setAttribute("addMsg", addMsg);
        req.getRequestDispatcher("/product/add_flag.jsp").forward(req, resp);
    }
}
