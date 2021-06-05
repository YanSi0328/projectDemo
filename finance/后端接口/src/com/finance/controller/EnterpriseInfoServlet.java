package com.finance.controller;

import com.alibaba.fastjson.JSON;
import com.finance.cons.PageInfo;
import com.finance.cons.ReturnCode;
import com.finance.cons.ReturnWeb;
import com.finance.entity.EnterpriseInfo;
import com.finance.service.EnterpriseInfoService;
import com.finance.service.impl.EnterpriseInfoServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;


import static org.apache.commons.lang3.math.NumberUtils.isCreatable;

/**
 * @author:Wang
 * @className: EnterpriseInfoServlet
 * @description:
 * @date: 2021/5/31 10:16
 * @version:0.1
 * @since:1.8
 */
@WebServlet("/enterprise/*")
public class EnterpriseInfoServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter writer = resp.getWriter();
        writer.print("1111111111");
    }

    public void query(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        int page = 1;
        int pageSize = 3;
        String name = req.getParameter("clientName");
        String pageStr = req.getParameter("page");
        String pageSizeStr = req.getParameter("pageSize");

        if (pageStr != null && !"".equals(pageStr)) {
            page = Integer.parseInt(pageStr);
        }
        if (pageSizeStr != null && !"".equals(pageSizeStr)) {
            pageSize = Integer.parseInt(pageSizeStr);
        }

        EnterpriseInfoService enterpriseInfoService = new EnterpriseInfoServiceImpl();
        Integer totalPage = enterpriseInfoService.getEnterpriseTotalPage(name);
        List<EnterpriseInfo> enterpriseInfoList = enterpriseInfoService.getEnterpriseInfoByPage(name, page, pageSize);

        ReturnWeb re = new ReturnWeb();
        if (totalPage > 0 && enterpriseInfoList.size() > 0) {
            re.setCode(ReturnCode.STATUS_SUCCESS.getCode());
            re.setReturnMsg(ReturnCode.STATUS_SUCCESS.getMsg());
        } else {
            re.setCode(ReturnCode.STATUS_ERROR.getCode());
            re.setReturnMsg(ReturnCode.STATUS_ERROR.getMsg());
        }
        re.setPageInfo(new PageInfo(page, pageSize, totalPage));
        re.setReturnData(enterpriseInfoList);

        resp.setContentType("text/html;charset=utf-8");
        PrintWriter writer = resp.getWriter();
        System.out.println(JSON.toJSONString(re));
        writer.print(JSON.toJSONString(re));

    }

    public void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        System.out.println("-----------------");
        //map中的key必须是表字段名
        Map<String, Object> paramMap = showParams(req);
        EnterpriseInfoService enterpriseInfoService = new EnterpriseInfoServiceImpl();
        int result = enterpriseInfoService.addEnterpriseInfo(paramMap);
        System.out.println(result + "------result");
        ReturnWeb re = new ReturnWeb();
        if (result > 0) {
            re.setCode(ReturnCode.STATUS_SUCCESS.getCode());
            re.setReturnMsg(ReturnCode.STATUS_SUCCESS.getMsg());
        } else {
            re.setCode(ReturnCode.STATUS_ERROR.getCode());
            re.setReturnMsg(ReturnCode.STATUS_ERROR.getMsg());
        }
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter writer = resp.getWriter();
        writer.print(JSON.toJSONString(re));
    }

    public void queryOne(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String code = req.getParameter("enterprise_code");
        String name = req.getParameter("enterprise_name");
        ReturnWeb re = new ReturnWeb();


        EnterpriseInfoService enterpriseInfoService = new EnterpriseInfoServiceImpl();
        EnterpriseInfo enterpriseByParms = enterpriseInfoService.getEnterpriseByParms(name, code);
        if (enterpriseByParms != null) {
            re.setCode(ReturnCode.STATUS_SUCCESS.getCode());
            re.setReturnMsg(ReturnCode.STATUS_SUCCESS.getMsg());
            re.setReturnData(enterpriseByParms);
        } else {
            re.setCode(ReturnCode.STATUS_ERROR.getCode());
            re.setReturnMsg(ReturnCode.STATUS_ERROR.getMsg());
        }
        System.out.println(JSON.toJSONString(re));
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter writer = resp.getWriter();
        writer.print(JSON.toJSONString(re));


    }

    public void edit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        System.out.println("-----------------");
        Map<String, Object> paramMap = showParams(req);

        EnterpriseInfoService enterpriseInfoService = new EnterpriseInfoServiceImpl();
        int result = enterpriseInfoService.editEnterpriseInfo(paramMap);
        //判断
        System.out.println(result + "-----------");
        ReturnWeb re = new ReturnWeb();
        if (result > 0) {
            re.setCode(ReturnCode.STATUS_SUCCESS.getCode());
            re.setReturnMsg(ReturnCode.STATUS_SUCCESS.getMsg());
        } else {
            re.setCode(ReturnCode.STATUS_ERROR.getCode());
            re.setReturnMsg(ReturnCode.STATUS_ERROR.getMsg());
        }
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter writer = resp.getWriter();
        writer.print(JSON.toJSONString(re));

    }

    public void type(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        EnterpriseInfoService enterpriseInfoService = new EnterpriseInfoServiceImpl();
        List<String> enterpriseType = enterpriseInfoService.getEnterpriseType();
        ReturnWeb returnWeb = new ReturnWeb();
        returnWeb.setReturnData(enterpriseType);

        resp.setContentType("text/html;charset=utf-8");
        PrintWriter writer = resp.getWriter();
        System.out.println(JSON.toJSONString(returnWeb));
        writer.print(JSON.toJSONString(returnWeb));
    }

    public Map<String, Object> showParams(HttpServletRequest request) {
        Map<String, Object> map = new TreeMap<>();
        Enumeration enu = request.getParameterNames();
        while (enu.hasMoreElements()) {
            String paraName = (String) enu.nextElement();
            String value = request.getParameter(paraName);
//            System.out.println(value);
            boolean flag = isCreatable(value);
//            System.out.println(flag);
            if (flag) {
                Double i = Double.parseDouble(value);
//                System.out.println(i);
                map.put(paraName, i);
            } else {
                map.put(paraName, value);
            }
//            System.out.println(paraName+": "+request.getParameter(paraName));
        }
//        System.out.println(map);
        return map;
    }


}
