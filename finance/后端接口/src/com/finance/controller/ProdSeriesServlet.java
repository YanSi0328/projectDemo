package com.finance.controller;

import com.finance.cons.PageInfo;
import com.finance.cons.ReturnWeb;
import com.finance.entity.ProdSeries;
import com.finance.service.ProdSeriesService;
import com.finance.service.impl.ProdSeriesServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * ClassName: ProdSeriesServlet
 * Author: ZhangCi
 *
 * @description: 产品系列管理
 * @date: 2021/5/24 22:49
 * @version: 0.1
 * @since: 1.8
 */
@WebServlet("/prodSer/*")
public class ProdSeriesServlet extends BaseServlet {
    private ProdSeriesService psService = new ProdSeriesServiceImpl();
    private PageInfo pInfo = new PageInfo();
    private ReturnWeb returnWeb;


    public void query(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /* 获取页面信息参数 */
        String pageStr = req.getParameter("page");
        String pageSizeStr = req.getParameter("pageSize");
        String serName = req.getParameter("seriesName");

        // 处理分页信息
        Integer page = 1;
        Integer pageSize = 5;
        // 对客户端取来的页码信息进行处理
        if (pageStr != null && !"".equals(pageStr)) {
            page = Integer.parseInt(pageStr);
        }
        if (pageSizeStr != null && !"".equals(pageSizeStr)) {
            pageSize = Integer.parseInt(pageSizeStr);
        }

        // 获得总记录数
        Integer total = psService.getTotalRecord(serName);
        // 装入页码信息
        pInfo.setPage(page);
        pInfo.setPageSize(pageSize);
        pInfo.setTotal(total);
        // 获得指定的产品系列集合
        List<ProdSeries> psList = psService.getMsgByPage(serName, pInfo);

        // 根据业务结果处理响应信息
        if (psList.size() != 0) {
            // success
            returnWeb = BaseServlet.reSuccess(psList, pInfo);
        } else {
            returnWeb = BaseServlet.reError();
        }
        // 向客户端发送数据
        BaseServlet.writeValue(resp, returnWeb);
        System.out.println("prodSer/query  执行了");
    }


    public void queryById(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /* 获取页面信息参数 */
        Integer psId = Integer.parseInt(req.getParameter("psId"));
        ProdSeries ps = psService.getMsgById(psId);

        // 向客户端发送数据
        BaseServlet.writeValue(resp, BaseServlet.reValue(ps));
        System.out.println("prodSer/queryById -> 执行了");
    }

    public void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获得客户端参数  pCanal、psName、pCname、pEname
        String pCanal = req.getParameter("pCanal");// 产品渠道
        String psName = req.getParameter("psName");// 产品系列/产品分类：基金、保险(未使用)、证券(未使用
        String pCname = req.getParameter("pCname");// 产品中文名称
        String pEname = req.getParameter("pEname");// 产品英文名称
        // 封装对象
        ProdSeries ps = new ProdSeries(pCanal, psName, pCname, pEname);
        // 执行业务
        Integer record = psService.addProdSeries(ps);
        if (record != 0) {
            // success
            returnWeb = BaseServlet.reSuccess("新增 " + record + " 条产品系列信息", pInfo);
        } else {
            returnWeb = BaseServlet.reError();
        }
        // 向客户端发送数据
        BaseServlet.writeValue(resp, returnWeb);
        System.out.println("prodSer/add -> 执行了");
    }

    public void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1 获取参数
        Integer psId = Integer.parseInt(req.getParameter("psId"));
        String pCanal = req.getParameter("pCanal");// 产品渠道
        String psName = req.getParameter("psName");// 产品系列/产品分类：基金、保险(未使用)、证券(未使用
        String pCname = req.getParameter("pCname");// 产品中文名称
        String pEname = req.getParameter("pEname");// 产品英文名称
        // 2 封装对象
        ProdSeries pSeries = new ProdSeries(psId, pCanal, psName, pCname, pEname);
        // 3 执行业务
        Integer record = psService.updateById(pSeries);
        if (record != 0) {
            // success
            returnWeb = BaseServlet.reValue("修改 " + record + " 条产品系列信息成功");
        } else {
            returnWeb = BaseServlet.reError();
        }
        BaseServlet.writeValue(resp, returnWeb);
        System.out.println("prodSer/update -> 执行了");
    }

    public void addRemit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1 获取汇款信息客户端参数
        Integer psId = Integer.parseInt(req.getParameter("psId"));
        String dueBankName = req.getParameter("dueBankName");
        String swiftCode = req.getParameter("swiftCode");
        String dueBankArea = req.getParameter("dueBankArea");
        String dueBankCity = req.getParameter("dueBankCity");
        String benefitName = req.getParameter("beneficiaryName");
        String benefitAccount = req.getParameter("beneficiaryAccount");
        String benefitAddress = req.getParameter("beneficiaryAddress");
        String remitInfo = req.getParameter("remitUser"); // 注意 该字段对应 数据库中 remit_info 字段
        String remitRemark = req.getParameter("remitRemark");
        String bankCode = req.getParameter("bankCode");
        String cnapsCode = req.getParameter("cnapsCode");
        // 2 封装对象
        ProdSeries pSeries = new ProdSeries(psId, dueBankName, swiftCode, dueBankArea,
                dueBankCity, benefitName, benefitAccount, benefitAddress,
                remitInfo, remitRemark, bankCode, cnapsCode);
        System.out.println("汇款信息 " + pSeries);
        Integer record = psService.addRemitMsg(pSeries);

        if (record != 0) {
            // success
            returnWeb = BaseServlet.reValue("新增 " + record + " 条产品汇款信息成功");
        } else {
            returnWeb = BaseServlet.reError();
        }
        BaseServlet.writeValue(resp, returnWeb);
        System.out.println("prodSer/addRemit -> 执行了");
    }

}