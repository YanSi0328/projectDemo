package com.finance.controller;

import com.alibaba.fastjson.JSON;
import com.finance.cons.ReturnCode;
import com.finance.cons.ReturnWeb;
import com.finance.service.TradingService;
import com.finance.service.impl.TradingServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;

import static org.apache.commons.lang3.math.NumberUtils.isCreatable;

/**
 * @author:Wang
 * @className: TradingServlet
 * @description:
 * @date: 2021/6/1 11:40
 * @version:0.1
 * @since:1.8
 */
@WebServlet("/trading/*")
public class TradingServlet extends BaseServlet {
    //enterprise/add?enterprise_id=15&enterprise_name="aaa"
    public void edit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        System.out.println("-----------------");
        //map的key必须是表中字段名称
        Map<String, Object> paramMap = showParams(req);
        System.out.println(paramMap + "---------");
        if (paramMap.size() > 0) {
            System.out.println("11111111");
            TradingService tradingService = new TradingServiceImpl();
            int result = tradingService.editStockTradingById(paramMap);
            System.out.println(result + "--------result");
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
    }

    public Map<String, Object> showParams(HttpServletRequest request) {

        Map<String, Object> map = new TreeMap<>();
        Enumeration enu = request.getParameterNames();
        while (enu.hasMoreElements()) {
            String paraName = (String) enu.nextElement();
            String value = request.getParameter(paraName);
            System.out.println(value);
            boolean flag = isCreatable(value);
            System.out.println(flag);
            if (flag) {
                Double i = Double.parseDouble(value);
                System.out.println(i);
                map.put(paraName, i);
            } else {

                map.put(paraName, null);
            }
//            System.out.println(paraName+": "+request.getParameter(paraName));
        }
        System.out.println(map);
        return map;
    }
}
