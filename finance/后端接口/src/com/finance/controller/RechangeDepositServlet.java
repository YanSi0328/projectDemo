package com.finance.controller;

import com.alibaba.fastjson.JSON;
import com.finance.cons.PageInfo;
import com.finance.cons.ReturnCode;
import com.finance.cons.ReturnWeb;
import com.finance.entity.RechangeDeposit;
import com.finance.service.RechangeDepositService;
import com.finance.service.impl.RechangeDepositServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author:Wang
 * @className: RechangeDepositServlet
 * @description:
 * @date: 2021/6/3 13:48
 * @version:0.1
 * @since:1.8
 */
@WebServlet("/invest/*")
public class RechangeDepositServlet extends BaseServlet {

    public void query(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String name = req.getParameter("clientName");
        String phone = req.getParameter("clientPhone");
        String tradType = req.getParameter("tradType");
        String statueStr = req.getParameter("statue");

        int type = 0;
        int statue = 0;

        if (tradType != null && !"".equals(tradType)) {
            if ("提现".equals(tradType)) {
                type = 1;
            } else if ("充值".equals(tradType)) {
                type = 2;
            }

        }
        if (statueStr != null && !"".equals(statueStr)) {
            if ("已汇款给用户".equals(statueStr)) {
                statue = 1;
            } else if ("已到PTN账户".equals(statueStr)) {
                statue = 2;
            }
        }

        RechangeDeposit rd = new RechangeDeposit(name, phone, type, statue);
        System.out.println(rd.getStatue() + "-----" + rd.getTradType());
        String pageStr = req.getParameter("page");
        String pageSizeStr = req.getParameter("pageSize");
        int page = 1;
        int pageSize = 3;
        if (pageStr != null && !"".equals(pageStr)) {
            page = Integer.parseInt(pageStr);
        }
        if (pageSizeStr != null && !"".equals(pageSizeStr)) {
            pageSize = Integer.parseInt(pageSizeStr);
        }
        ReturnWeb re = new ReturnWeb();
        RechangeDepositService rds = new RechangeDepositServiceImpl();
        int total = rds.getTotalRechangeDeposit(page, pageSize, rd);
        PageInfo pf = new PageInfo(page, pageSize, total);
        List<RechangeDeposit> list = rds.getAllRechangeDeposit(page, pageSize, rd);
        if (list.size() > 0) {
            re.setCode(ReturnCode.STATUS_SUCCESS.getCode());
            re.setReturnMsg(ReturnCode.STATUS_SUCCESS.getMsg());
            re.setPageInfo(pf);
            re.setReturnData(list);
        } else {
            re.setCode(ReturnCode.STATUS_ERROR.getCode());
            re.setReturnMsg(ReturnCode.STATUS_ERROR.getMsg());
            re.setPageInfo(new PageInfo(page, pageSize, 0));
            re.setReturnData("");
        }
        resp.setContentType("text/html;charset=utf-8");
        System.out.println("开始写入");
        PrintWriter writer = resp.getWriter();
        System.out.println(JSON.toJSONString(re));
        writer.print(JSON.toJSONString(re));
    }

    public void type(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        RechangeDepositService eis = new RechangeDepositServiceImpl();
        List<String> list = new ArrayList<>(10);
        List<Integer> typeList = eis.getAllType();
        System.out.println(typeList);
        for (int i = 0; i < typeList.size(); i++) {
            if (typeList.get(i).equals(1)) {
                list.add("提现");
            } else if (typeList.get(i).equals(2)) {
                list.add("重置");
            }
        }
        ReturnWeb returnWeb = new ReturnWeb();
        returnWeb.setReturnData(list);

        resp.setContentType("text/html;charset=utf-8");
        PrintWriter writer = resp.getWriter();
        System.out.println(JSON.toJSONString(returnWeb));
        writer.print(JSON.toJSONString(returnWeb));
    }

    public void statue(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        RechangeDepositService eis = new RechangeDepositServiceImpl();
        List<String> list = new ArrayList<>(10);
        List<Integer> statueList = eis.getALLStatue();
        for (int i = 0; i < statueList.size(); i++) {
            if (statueList.get(i).equals(1)) {
                list.add("已汇款给用户 ");
            } else if (statueList.get(i).equals(2)) {
                list.add("已到PTN账户");
            }
        }
        ReturnWeb returnWeb = new ReturnWeb();
        returnWeb.setReturnData(list);

        resp.setContentType("text/html;charset=utf-8");
        PrintWriter writer = resp.getWriter();
        System.out.println(JSON.toJSONString(returnWeb));
        writer.print(JSON.toJSONString(returnWeb));
    }
}
