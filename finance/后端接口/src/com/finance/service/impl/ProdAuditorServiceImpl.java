package com.finance.service.impl;

import com.finance.cons.PageInfo;
import com.finance.dao.ProdAuditorDao;
import com.finance.dao.ProdBasicDao;
import com.finance.dao.impl.ProdAuditorDaoImpl;
import com.finance.dao.impl.ProdBasicDaoImpl;
import com.finance.entity.ProdBasic;
import com.finance.service.ProdAuditorService;

import java.util.List;
import java.util.Map;

/**
 * ClassName: ProdAuditorServiceImpl
 * Author: ZhangCi
 *
 * @description: 业务逻辑层实现类-产品审核
 * @date: 2021/6/2 15:45
 * @version: 0.1
 * @since: 1.8
 */
public class ProdAuditorServiceImpl implements ProdAuditorService {
    private ProdBasicDao pbDao = new ProdBasicDaoImpl();
    private ProdAuditorDao paDao = new ProdAuditorDaoImpl();

    @Override
    public Integer getTotal(Map<String, Object> params) {
        // 产品审核与产品基础信息共用一张数据库表
        // 由于在产品基础信息中已经书写过相关方法，故在此处不在写，直接调用即可
        return pbDao.getTotal(params);
    }

    @Override
    public List<ProdBasic> getMsgByParams(Map<String, Object> params, PageInfo pInfo) {
        return paDao.getProdAuditor(params, pInfo);
    }

    @Override
    public Integer modifyReviewStatus(String auditStatus, String pName) {
        /**
         * 操作按钮分析：
         * -- 首先：当点击当前行时，进行查询得到审核状态和审核人
         * -- 然后：判断查询出来的审核状态，若是待审核才会触发操作按钮，其他状态操作按钮提示无需操作无效
         * -- 其次：判断查询到的审批人是否是当前登录的系统用户，相同则可以审核，立即弹出产品信息框等待审核，否则提示无权审核
         */
        return paDao.modifyReviewStatus(auditStatus, pName);
    }

    @Override
    public Map<String, Object> getReviewMsg(String pName) {
        return paDao.getReview(pName);
    }

    @Override
    public ProdBasic getReviewProd(String prodName) {
        return pbDao.getProdByProdName(prodName);
    }
}
