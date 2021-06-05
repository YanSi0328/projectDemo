package com.finance.controller;

import com.alibaba.fastjson.JSON;
import com.finance.cons.ReturnCode;
import com.finance.cons.ReturnWeb;
import com.finance.entity.ClientInfo;
import com.finance.cons.PageInfo;
import com.finance.service.ClientInfoService;
import com.finance.service.impl.ClientInfoServiceImpl;

import javax.servlet.ServletException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.List;

/**
 * @author:Wang
 * @className: ClientInfoServelet
 * @description:
 * @date: 2021/5/30 15:59
 * @version:0.1
 * @since:1.8
 */
@WebServlet("/client/*")
public class ClientInfoServelet extends BaseServlet {
    public void query(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("utf-8");
        System.out.println("11111111111");

        int page = 1;
        int pageSize = 3;
        String pageStr = req.getParameter("page");
        String pageSizeStr = req.getParameter("pageSize");

        String name = req.getParameter("clientName");
        String phone = req.getParameter("clientPhone");
        ClientInfo clientInfo = new ClientInfo(name, phone);

        if (pageStr != null && !"".equals(pageStr)) {
            page = Integer.parseInt(pageStr);
        }
        if (pageSizeStr != null && !"".equals(pageSizeStr)) {
            pageSize = Integer.parseInt(pageSizeStr);
        }

        ClientInfoService clientInfoService = new ClientInfoServiceImpl();
        int totalPage = clientInfoService.getClientTotalPage(clientInfo);
        PageInfo pageInfo = new PageInfo(page, pageSize, totalPage);

        List<ClientInfo> clientInfoList = clientInfoService.getClientByPage(page, pageSize, clientInfo);

        ReturnWeb re = new ReturnWeb();
        re.setCode(ReturnCode.STATUS_SUCCESS.getCode());
        re.setReturnMsg(ReturnCode.STATUS_SUCCESS.getMsg());
        re.setPageInfo(pageInfo);
        re.setReturnData(clientInfoList);

        resp.setContentType("text/html;charset=utf-8");
        PrintWriter writer = null;
        writer = resp.getWriter();
        writer.print(JSON.toJSONString(re));

        //验证返回的数据格式
        //System.out.println(JSON.toJSONString(re));
        writer.flush();
        writer.close();
    }

}
