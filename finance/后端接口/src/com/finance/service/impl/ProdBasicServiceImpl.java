package com.finance.service.impl;

import com.finance.cons.PageInfo;
import com.finance.dao.ProdBasicDao;
import com.finance.dao.impl.ProdBasicDaoImpl;
import com.finance.entity.ProdBasic;
import com.finance.service.ProdBasicService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: ProdBasicServiceImpl
 * Author: ZhangCi
 *
 * @description: 业务逻辑层-产品基本信息实现类
 * @date: 2021/6/1 11:16
 * @version: 0.1
 * @since: 1.8
 */
public class ProdBasicServiceImpl implements ProdBasicService {
    private ProdBasicDao pbDao = new ProdBasicDaoImpl();

    @Override
    public Integer getTotal(Map<String, Object> params) {
        return pbDao.getTotal(params);
    }

    @Override
    public List<ProdBasic> getBasicByParams(Map<String, Object> params, PageInfo pInfo) {
        return pbDao.getProdByPage(params, pInfo);
    }

    @Override
    public Integer addMsg(ProdBasic prodBasic, String loginUser) {
        // 产品编号的处理
        /**业务需求:
         *  基金产品编号使用 17 开头
         *  保险产品编号使用 19 开头
         *  证券产品编号使用 21 开头
         * 并且编号实现递增
         */
        // 1 初始参数
        String prodCategory = prodBasic.getProdCategory();
        // 2 根据指定系列进行查询
        Map<String, Object> params = new HashMap<>(16);
        params.put("p_category", prodCategory);
        List<ProdBasic> msgByPage = pbDao.getProdByPage(params, new PageInfo());
        // 3 获得该系列最后一条记录的产品编号，并且执行编号自增 1
        Integer psId = msgByPage.get(msgByPage.size() - 1).getProdId() + 1;
        System.out.println("psId " + psId);

        prodBasic.setProdId(psId);
        prodBasic.setProdName(prodBasic.getProdName() + "【" + prodCategory + "】");
        // 创建人
        prodBasic.setCreator(loginUser);

        System.out.println("根据业务需求更改后的" + prodBasic);
        return pbDao.addProd(prodBasic);
    }

    @Override
    public Integer modifyMsg(ProdBasic prodBasic) {
//        String openDateStr = prodBasic.getOpenDate();
//        String openDate = openDateStr.substring(0, openDateStr.lastIndexOf("T"));
//        prodBasic.setOpenDate(openDate);
        return pbDao.modifyProd(prodBasic);
    }

    @Override
    public ProdBasic getMsgByProdName(String prodName) {
        return pbDao.getProdByProdName(prodName);
    }
}
