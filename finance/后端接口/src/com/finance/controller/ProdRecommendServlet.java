package com.finance.controller;

import com.finance.cons.PageInfo;
import com.finance.cons.ReturnWeb;
import com.finance.entity.ProdRecommend;
import com.finance.service.ProdRecommendService;
import com.finance.service.impl.ProdRecommendServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * ClassName: ProdRecommendServlet
 * Author: ZhangCi
 *
 * @description:
 * @date: 2021/6/1 21:15
 * @version: 0.1
 * @since: 1.8
 */
@WebServlet("/prodRe/*")
public class ProdRecommendServlet extends BaseServlet {
    private ProdRecommendService prService = new ProdRecommendServiceImpl();
    private ReturnWeb returnWeb;

    public void query(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PageInfo pInfo;
        // 1 获取页面参数
        String seriesName = req.getParameter("seriesName");
        String pageStr = req.getParameter("page");
        String pageSizeStr = req.getParameter("pageSize");

        // 2 处理页码参数信息
        Integer page = 1;
        Integer pageSize = 5;
        if (pageStr != null && !"".equals(pageStr)) {
            page = Integer.parseInt(pageStr);
        }
        if (pageSizeStr != null && !"".equals(pageSizeStr)) {
            pageSize = Integer.parseInt(pageSizeStr);
        }

        // 3 根据参数获得总的记录数
        Integer total = prService.getTotal(seriesName);
        pInfo = new PageInfo(page, pageSize, total);
        List<ProdRecommend> prList = prService.getMsgByParams(seriesName, pInfo);

        if (prList.size() != 0) {
            // success
            returnWeb = BaseServlet.reSuccess(prList, pInfo);
        } else {
            returnWeb = BaseServlet.reError();
        }

        BaseServlet.writeValue(resp, returnWeb);
        System.out.println("prodRe/query -> 执行了");
    }

    public void queryProdName(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        List<String> pNameList = prService.getByProdName();
        if (pNameList.size() != 0) {
            // success
            returnWeb = BaseServlet.reValue(pNameList);
        } else {
            returnWeb = BaseServlet.reError();
        }

        BaseServlet.writeValue(resp, returnWeb);
        System.out.println("prodRe/query -> 执行了");
    }

    public void add(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // 1 获取新增参数
        String prodName = req.getParameter("prodName");
        Integer degree = Integer.parseInt(req.getParameter("degree"));
        Integer investVisible = Integer.parseInt(req.getParameter("investVisible"));
        Integer firstRound = Integer.parseInt(req.getParameter("firstRound"));
        Integer onlinePurchase = Integer.parseInt(req.getParameter("onlinePurchase"));
        String reason = req.getParameter("reason");
        // 2 封装参数
        ProdRecommend prodRecommend = new ProdRecommend(prodName, degree, investVisible, firstRound, onlinePurchase, reason);
        // 3 执行业务
        Integer record = prService.addPRMsg(prodRecommend);
        if (record == 1) {
            returnWeb = BaseServlet.reValue("新增产品推荐信息 " + record);
        } else {
            returnWeb = BaseServlet.reError();
        }
        BaseServlet.writeValue(resp, returnWeb);
        System.out.println("prodRe/add -> 执行了");
    }

    public void modify(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String prodName = req.getParameter("prodName");
        Integer degree = Integer.parseInt(req.getParameter("degree"));
        Integer investVisible = Integer.parseInt(req.getParameter("investVisible"));
        Integer firstRound = Integer.parseInt(req.getParameter("firstRound"));
        Integer onlinePurchase = Integer.parseInt(req.getParameter("onlinePurchase"));
        String reason = req.getParameter("reason");
        // 2 封装参数
        ProdRecommend prodRecommend = new ProdRecommend(prodName, degree, investVisible, firstRound, onlinePurchase, reason);

        // 3 执行业务
        Integer record = prService.modifyPRMsg(prodRecommend);
        if (record == 1) {
            returnWeb = BaseServlet.reValue("修改产品推荐信息 " + record);
        } else {
            returnWeb = BaseServlet.reError();
        }
        BaseServlet.writeValue(resp, returnWeb);
        System.out.println("prodRe/modify -> 执行了");
    }

    public void related(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        // 1 获取参数
        String pCategory = req.getParameter("pCategory");

        // 3 执行业务
        List<Map<String, Object>> relatedList = prService.relatedMsg(pCategory);

        BaseServlet.writeValue(resp, BaseServlet.reValue(relatedList));
        System.out.println("prodRe/related -> 执行了");
    }

    public void modifyRelated(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        // 1 获取参数
        String pidStr = req.getParameter("pidStr");
        Integer pId = Integer.parseInt(req.getParameter("pId"));

        // 2 执行业务
        Integer record = prService.modifyRelated(pidStr, pId);
        if (record == 1) {
            returnWeb = BaseServlet.reValue("修改产品关联信息 " + record);
        } else {
            returnWeb = BaseServlet.reError();
        }
        BaseServlet.writeValue(resp, returnWeb);
        System.out.println("prodRe/modifyRelated -> 执行了");
    }
}
