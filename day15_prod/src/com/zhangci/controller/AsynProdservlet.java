package com.zhangci.controller;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhangci.etity.PageInfo;
import com.zhangci.etity.Product;
import com.zhangci.etity.ReturnCode;
import com.zhangci.etity.ReturnEntity;
import com.zhangci.service.ProductService;
import com.zhangci.service.impl.ProductServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * ClassName: AsynProdservlet
 * Author: ZhangCi
 *
 * @description: 异步分页查询服务端
 * @date: 2021/5/18 10:05
 * @version: 0.1
 * @since: 1.8
 */
@WebServlet("/asynProd/*")
public class AsynProdservlet extends BaseServlet {
    private ProductService prodService = new ProductServiceImpl();
    private ReturnEntity returnEntity = new ReturnEntity();

    // 查询
    public void query(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. 获得前端传递回来的页码信息
        String pageStr = req.getParameter("page");
        String pageSizeStr = req.getParameter("pageSize");
        String prodid = req.getParameter("prodid");
        String prodname = req.getParameter("prodname");
        // 2. 封装查询对象
        Product queryProd = new Product(prodid, prodname);
        // 3. 初始页面信息prodid=0&prodname=null
        Integer page = 1;
        Integer pageSize = 3;
        // 4. 对得到的页面字符串数据进行处理：两种情况：传递过来的无键、传递过来的无值
        if (pageStr != null && !"".equals(pageStr)) {
            // 有值且不为空，则将页面传递过来的页码字符串进行处理
            page = Integer.parseInt(pageStr);
        }
        if (pageSizeStr != null && !"".equals(pageSizeStr)) {
            // 有值且不为空，则将页面传递过来的页码字符串进行处理
            pageSize = Integer.parseInt(pageSizeStr);
        }
        // 5. 根据信息进行查询:得到指定的详细商品信息、总的商品信息页数
        List<Product> allProdMsg = prodService.selAllProdMsg(page, pageSize, queryProd);
        Integer allProdCount = prodService.selAllProdCount(pageSize, queryProd);
        System.out.println("查询结果 " + allProdMsg + "  " + allProdCount);

        // 6. 将查询结果输出到页面
        // 6.1 设置输出的数据格式
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter writer = resp.getWriter();
        // 6.2 封装返回的数据
        ReturnEntity returnEntity = new ReturnEntity();
        // 6.3 根据查询结果返回数据
        if (allProdMsg.size() > 0) {
            // sucess : 封装相关数据
            returnEntity.setCode(ReturnCode.STATUS_SUCCESS.getCode());
            returnEntity.setReturnMsg(ReturnCode.STATUS_SUCCESS.getMsg());
            returnEntity.setReturnData(allProdMsg);
            returnEntity.setPageInfo(new PageInfo(page, pageSize, allProdCount));
        } else {
            // error
            returnEntity.setCode(ReturnCode.STATUS_ERROR.getCode());
            returnEntity.setReturnMsg(ReturnCode.STATUS_ERROR.getMsg());
            returnEntity.setPageInfo(new PageInfo(page, pageSize, allProdCount));
        }
        // 6.4 json封装
        writer.print(JSON.toJSONString(returnEntity));
        writer.flush();
        writer.close();
    }

    // 新增
    public void addProd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("新增商品");
        // 1. 获得输入的商品信息
        String prodId = req.getParameter("prodId");
        String prodName = req.getParameter("prodName");
        BigDecimal prodPrice = new BigDecimal(req.getParameter("prodPrice"));
        String prodImg = req.getParameter("prodImg");
        String prodDesc = req.getParameter("prodDesc");
        // 2. 对象封装
        Product addProd = new Product(prodId, prodName, prodPrice, prodImg, prodDesc);
        System.out.println("新增商品信息：" + addProd);

        /*Map<String, String[]> paramMap = req.getParameterMap();
        System.out.println(paramMap.size());
        for (int i = 0; i < paramMap.size(); i++) {
            System.out.println(paramMap.get(i));
        }
        Product addProd = new Product();
        try {
            BeanUtils.populate(addProd, paramMap);
            System.out.println("00000");
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        System.out.println("新增商品信息0000：" + addProd);*/


        // 3. 执行新增
        Integer prodRecord = prodService.addProd(addProd);
        // 4. 根据新增返回的记录条数进行判断新增是否成功
        // 4.1 封装待传递的数据

        // 4.2 根据操作结果封装数据
        if (prodRecord > 0) {
            // success：不需要返回数据，为什么？因为是新增，客户端只要知道新增一条记录成功了即可
            returnEntity.setCode(ReturnCode.STATUS_SUCCESS.getCode());
            returnEntity.setReturnMsg(ReturnCode.STATUS_SUCCESS.getMsg());
        } else {
            // error
            returnEntity.setCode(ReturnCode.STATUS_ERROR.getCode());
            returnEntity.setReturnMsg(ReturnCode.STATUS_ERROR.getMsg());
        }

        // 4.3 获得resp的输出流并返回json格式的数据：三个包都要导入
        resp.setContentType("application/json;charset=utf-8");
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(resp.getOutputStream(), returnEntity);
    }

    // 修改
    public void updateProd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("修改指定商品信息");
        // 1. 获得输入的商品信息
        String prodId = req.getParameter("prodId");
        String prodName = req.getParameter("prodName");
        BigDecimal prodPrice = new BigDecimal(req.getParameter("prodPrice"));
        String prodImg = req.getParameter("prodImg");
        String prodDesc = req.getParameter("prodDesc");
        // 2. 对象封装
        Product updateProd = new Product(prodId, prodName, prodPrice, prodImg, prodDesc);
        System.out.println("修改后的商品信息：" + updateProd);
        // 3 执行修改
        Integer num = prodService.updatProd(updateProd);

        if (num == 1) {
            returnEntity.setCode(ReturnCode.STATUS_SUCCESS.getCode());
            returnEntity.setReturnMsg(ReturnCode.STATUS_SUCCESS.getMsg());
        } else {
            returnEntity.setCode(ReturnCode.STATUS_ERROR.getCode());
            returnEntity.setReturnMsg(ReturnCode.STATUS_ERROR.getMsg());
        }
        // 4 写回到客户端
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(resp.getOutputStream(), returnEntity);
    }

    // 通过 id 查询指定商品
    public void selectProdById(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("通过 id 查询指定商品");
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json;charset=utf-8");
        // 1 获得参数
        String prodId = req.getParameter("prodId");
        // 2 执行查询
        Product prodById = prodService.selProductById(prodId);

        // 3 根据是否查到指定商品返回数据
        if (prodById != null) {
            // success
            returnEntity.setCode(ReturnCode.STATUS_SUCCESS.getCode());
            returnEntity.setReturnMsg(ReturnCode.STATUS_SUCCESS.getMsg());
            returnEntity.setReturnData(prodById);
        } else {
            // error
            returnEntity.setCode(ReturnCode.STATUS_ERROR.getCode());
            returnEntity.setReturnMsg(ReturnCode.STATUS_ERROR.getMsg());
        }
        // 4 写回到客户端
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(resp.getOutputStream(), returnEntity);
    }

    // 通过 id 删除指定商品
    public void delProdById(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("通过 id 删除指定商品");
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json;charset=utf-8");
        // 1 获得参数
        String prodId = req.getParameter("prodId");

        // 2 执行删除
        Integer prodRecord = prodService.delProdById(prodId);
        System.out.println("prodRecord " + prodRecord);
        System.out.println("prodId " + prodId);
        if (prodRecord == 1) {
            // success
            returnEntity.setCode(ReturnCode.STATUS_SUCCESS.getCode());
            returnEntity.setReturnMsg(ReturnCode.STATUS_SUCCESS.getMsg());
        } else {
            // error
            returnEntity.setCode(ReturnCode.STATUS_ERROR.getCode());
            returnEntity.setReturnMsg(ReturnCode.STATUS_ERROR.getMsg());
        }
        // 4 写回到客户端
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(resp.getOutputStream(), returnEntity);
    }
}
