package com.finance.controller;

import com.finance.cons.PageInfo;
import com.finance.cons.ReturnWeb;
import com.finance.entity.ProdBasic;
import com.finance.service.ProdAuditorService;
import com.finance.service.impl.ProdAuditorServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: ProdAuditorServlet
 * Author: ZhangCi
 *
 * @description: 产品审核
 * @date: 2021/6/1 21:17
 * @version: 0.1
 * @since: 1.8
 */
@WebServlet("/prodAuditor/*")
public class ProdAuditorServlet extends BaseServlet {
    private ProdAuditorService paService = new ProdAuditorServiceImpl();
    private ReturnWeb returnWeb;

    public void query(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<String, Object> params = new HashMap<>(16);
        PageInfo pInfo;
        // 1 获取页面参数
        String prodName = req.getParameter("prodName");
        String auditStatus = req.getParameter("auditStatus");
        String pageStr = req.getParameter("page");
        String pageSizeStr = req.getParameter("pageSize");
        // 2 查询参数放入参数集合
        if (prodName != null && !"".equals(prodName)) {
            params.put("p_name", prodName);
        }
        if (auditStatus != null && !"".equals(auditStatus)) {
            params.put("review_status", auditStatus);
        }
        System.out.println("查询参数 " + params);

        // 3 处理页码参数信息
        Integer page = 1;
        Integer pageSize = 5;
        if (pageStr != null && !"".equals(pageStr)) {
            page = Integer.parseInt(pageStr);
        }
        if (pageSizeStr != null && !"".equals(pageSizeStr)) {
            pageSize = Integer.parseInt(pageSizeStr);
        }

        // 4.1 根据参数获得总的记录数
        Integer total = paService.getTotal(params);
        // 4.2 封装页码信息
        pInfo = new PageInfo(page, pageSize, total);
        System.out.println("pInfo -> " + pInfo);
        // 4.3 根据参数查询，得到指定搞得审核信息集合
        List<ProdBasic> paList = paService.getMsgByParams(params, pInfo);

        // 5 处理返回数据
        if (paList.size() != 0) {
            // success
            returnWeb = BaseServlet.reSuccess(paList, pInfo);
        } else {
            returnWeb = BaseServlet.reError();
        }

        // 6 将查询结果输出到客户端
        BaseServlet.writeValue(resp, returnWeb);
        System.out.println("prodAuditor/query -> 执行了");
    }

    // 得到审核信息
    public void getReview(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String prodName = req.getParameter("prodName");
        // 将审核人和审核状态发送至客户端
        Map<String, Object> reviewMsg = paService.getReviewMsg(prodName);
        // 应该对返回到客户端的数据加一个限制
        BaseServlet.writeValue(resp, BaseServlet.reValue(reviewMsg));
        System.out.println("prodAuditor/getReview -> 执行了");
    }

    // 得到审核产品信息
    public void getReviewProd(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // 1 得到产品的审核信息
        String prodName = req.getParameter("prodName");
        Map<String, Object> reviewMsg = paService.getReviewMsg(prodName);
        String loginUser = "zc";
        String reviewer = (String) reviewMsg.get("reviewer");
        String status = (String) reviewMsg.get("status");
        System.out.println("审核人 " + reviewer);
        System.out.println("审核状态 " + status);

        ProdBasic pBasic = paService.getReviewProd(prodName);
        // 2 判断是否可以审核
        if (reviewer.equals(loginUser)) {
            // 是登录用户，此时判定是否是待审核状态
            // status 转换为integer类型为什么比较不了？
            if (Integer.parseInt(status) == 1) {
                // 是待审核状态，此时输出待审核产品信息
                returnWeb = BaseServlet.reValue(pBasic);
            } else {
                returnWeb = BaseServlet.reError("产品无法审核");
            }
        } else {
            // 不是登录用户
            returnWeb = BaseServlet.reError("您不是审核人");
        }

        // 将查询结果输出到客户端
        BaseServlet.writeValue(resp, returnWeb);
        System.out.println("prodAuditor/getReviewProd -> 执行了");
    }


    public void modifyStatus(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // 1 获取页面参数
        String auditStatus = req.getParameter("auditStatus");
        String prodName = req.getParameter("prodName");

        Integer record = paService.modifyReviewStatus(auditStatus, prodName);
        if (record == 1) {
            returnWeb = BaseServlet.reValue("修改 " + record + " 条审核状态成功");
        } else {
            returnWeb = BaseServlet.reError();
        }

        // 6 将查询结果输出到客户端
        BaseServlet.writeValue(resp, returnWeb);
        System.out.println("prodAuditor/modifyStatus -> 执行了");
    }
}
