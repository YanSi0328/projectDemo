package com.finance.controller;

import com.alibaba.fastjson.JSON;
import com.finance.cons.ReturnCode;
import com.finance.cons.ReturnWeb;
import com.finance.entity.HistoryFinancing;
import com.finance.service.HistoryFinancingService;
import com.finance.service.impl.HistoryFinacingServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author:Wang
 * @className: FinancingServlet
 * @description:
 * @date: 2021/6/3 10:15
 * @version:0.1
 * @since:1.8
 */
@WebServlet("/financing/*")
public class FinancingServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    public void query(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String name = req.getParameter("name");
        HistoryFinancingService hf = new HistoryFinacingServiceImpl();
        List<HistoryFinancing> financingList = hf.getHistoryFinancing(name);
        ReturnWeb re = new ReturnWeb();
        if (financingList.size() > 0) {
            re.setCode(ReturnCode.STATUS_SUCCESS.getCode());
            re.setReturnMsg(ReturnCode.STATUS_SUCCESS.getMsg());
            re.setReturnData(financingList);
        } else {
            re.setCode(ReturnCode.STATUS_ERROR.getCode());
            re.setReturnMsg(ReturnCode.STATUS_ERROR.getMsg());
        }
        resp.setContentType("text/html;charset=utf-8");
        System.out.println("开始写入");
        PrintWriter writer = resp.getWriter();
        System.out.println(JSON.toJSONString(re));
        writer.print(JSON.toJSONString(re));
    }
}
