package com.finance.controller;

import com.finance.cons.PageInfo;
import com.finance.cons.ReturnWeb;
import com.finance.entity.ProdBasic;
import com.finance.service.ProdBasicService;
import com.finance.service.impl.ProdBasicServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: ProdBasicServlet
 * Author: ZhangCi
 *
 * @description:
 * @date: 2021/5/28 9:12
 * @version: 0.1
 * @since: 1.8
 */
@WebServlet("/prodBasic/*")
public class ProdBasicServlet extends BaseServlet {
    private ProdBasicService pbService = new ProdBasicServiceImpl();
    private PageInfo pInfo = new PageInfo();
    private ReturnWeb returnWeb;

    public void query(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<String, Object> params = new HashMap<>(16);
        // 1 获取页面参数(查询条件)
        String selProdName = req.getParameter("selProdName");
        String selProdType = req.getParameter("selProdType");
        String auditStatus = req.getParameter("selAuditStatus");
        String pageStr = req.getParameter("page");
        String pageSizeStr = req.getParameter("pageSize");
        // 2.1 是否装入参数
        if (selProdName != null && !"".equals(selProdName)) {
            params.put("p_name", selProdName);
        }
        if (selProdType != null && !"".equals(selProdType)) {
            params.put("p_series_category", selProdType);
        }
        if (auditStatus != null && !"".equals(auditStatus)) {
            params.put("review_status", auditStatus);
        }
        System.out.println("查询参数 " + params);

        // 2.2 对页码的处理
        Integer page = 1;
        Integer pageSize = 5;
        if (pageStr != null && !"".equals(pageStr)) {
            page = Integer.parseInt(pageStr);
        }
        if (pageSizeStr != null && !"".equals(pageSizeStr)) {
            pageSize = Integer.parseInt(pageSizeStr);
        }
        // 3 执行业务
        // 3.1 获得总记录数
        Integer total = pbService.getTotal(params);
        // 3.2 封装页码信息
        pInfo = new PageInfo(page, pageSize, total);
        // 3.3 执行条件查询
        List<ProdBasic> pbList = pbService.getBasicByParams(params, pInfo);

        // 4 向客户端发送数据
        if (pbList.size() != 0) {
            // success
            returnWeb = BaseServlet.reSuccess(pbList, pInfo);
        } else {
            returnWeb = BaseServlet.reError();
        }
        // 将查询结果输出到客户端
        BaseServlet.writeTime(resp, returnWeb);
        System.out.println("prodBasic/query -> 执行了");
    }

    public void queryByProdName(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // 1 获取页面参数(查询条件)
        String prodName = req.getParameter("prodName");
        // 2 执行业务
        ProdBasic pBasic = pbService.getMsgByProdName(prodName);
        if (pBasic != null) {
            returnWeb = BaseServlet.reSuccess(pBasic, pInfo);
        } else {
            returnWeb = BaseServlet.reError();
        }
        // 将查询结果输出到客户端
        BaseServlet.writeValue(resp, returnWeb);
        System.out.println("prodBasic/queryByProdName -> 执行了");
    }

    public void add(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // 1 获取参数 Double Integer 类型参数不能为空
        ProdBasic prodBasic = forAddModify(req);
        System.out.println("新增产品信息 " + prodBasic);
        // 3 执行新增业务
        // 3.1 当前系统登录的用户
        String lUser = "zc";

        Integer record = pbService.addMsg(prodBasic, lUser);
        System.out.println("02 " + record);

        if (record == 1) {
            // success
            returnWeb = BaseServlet.reSuccess("新增" + record + "条记录", new PageInfo());
        } else {
            returnWeb = BaseServlet.reError();
        }
        BaseServlet.writeValue(resp, returnWeb);
        System.out.println("prodBasic/add -> 执行了");
    }

    public void modify(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // 1 获取页面参数
        ProdBasic prodBasic = forAddModify(req);
        Integer record = pbService.modifyMsg(prodBasic);

        if (record == 1) {
            // success
            returnWeb = BaseServlet.reSuccess("修改" + record + "条记录", new PageInfo());
        } else {
            returnWeb = BaseServlet.reError();
        }
        BaseServlet.writeValue(resp, returnWeb);
        System.out.println("prodBasic/modify -> 执行了");
    }

    private ProdBasic forAddModify(HttpServletRequest req) {
        String prodCategory = req.getParameter("prodCategory");
        String prodSecCategory = req.getParameter("prodSecCategory");
        String prodName = req.getParameter("prodName");
        String adminOrgan = req.getParameter("adminOrgan");
        Double annualizedYield = Double.parseDouble(req.getParameter("annualizedYield"));
        String fundCurrency = req.getParameter("fundCurrency");
        String opDateStr = req.getParameter("openDate");
        String opDate = opDateStr.substring(0, opDateStr.lastIndexOf("T"));

        String subscriptCycle = req.getParameter("subscriptCycle");
        Double relativeManageCost = Double.parseDouble(req.getParameter("relativeManageCost"));
        Double subscriptionRate = Double.parseDouble(req.getParameter("subscriptionRate"));
        Double startAmount = Double.parseDouble(req.getParameter("startAmount"));
        String chargeMode = req.getParameter("chargeMode");
        String redeemCycle = req.getParameter("redeemCycle");
        Double initRedeemAmount = Double.parseDouble(req.getParameter("initRedeemAmount"));
        Double redeemRate = Double.parseDouble(req.getParameter("redeemRate"));
        Integer redeemLockUp = Integer.parseInt(req.getParameter("redeemLockUp"));
        String reviewer = req.getParameter("reviewer");

        // 2 封装入对象：注意传递的参数顺序
        ProdBasic prodBasic = new ProdBasic(prodCategory, prodSecCategory, prodName, adminOrgan, annualizedYield,
                fundCurrency, opDate, subscriptCycle, relativeManageCost, subscriptionRate, startAmount,
                chargeMode, redeemCycle, initRedeemAmount, redeemRate, redeemLockUp, reviewer);
        return prodBasic;
    }
}
