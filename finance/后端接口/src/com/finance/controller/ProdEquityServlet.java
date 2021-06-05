package com.finance.controller;

import com.finance.cons.PageInfo;
import com.finance.cons.ReturnWeb;
import com.finance.entity.ProdEquity;
import com.finance.service.ProdEquityService;
import com.finance.service.impl.ProdEquityServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * ClassName: ProdEquityServlet
 * Author: ZhangCi
 *
 * @description:
 * @date: 2021/6/1 19:57
 * @version: 0.1
 * @since: 1.8
 */
@WebServlet("/prodEquity/*")
public class ProdEquityServlet extends BaseServlet {
    private ProdEquityService peService = new ProdEquityServiceImpl();
    private ReturnWeb returnWeb;

    public void add(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // 1 获得客户端参数：注意此时的输入必须不能为空，否则都会出错
        String prodName = req.getParameter("prodName");
        Integer average = Integer.parseInt(req.getParameter("average"));
        String baseDateStr = req.getParameter("baseDate");
        String baseDate = baseDateStr.substring(0, baseDateStr.lastIndexOf("T"));
        Double growthRate = Double.parseDouble(req.getParameter("growthRate"));
        ProdEquity prodEquity = new ProdEquity(prodName, average, baseDate, growthRate);
        // 2 执行业务
        Integer record = peService.dealEquity(prodEquity);
        if (record == 1) {
            // success
            returnWeb = BaseServlet.reSuccess(" " + record + " 条产品净值记录", new PageInfo());
        } else {
            returnWeb = BaseServlet.reError();
        }
        BaseServlet.writeValue(resp, returnWeb);
        System.out.println("prodBasic/addEquity ->  执行了");
    }
}
