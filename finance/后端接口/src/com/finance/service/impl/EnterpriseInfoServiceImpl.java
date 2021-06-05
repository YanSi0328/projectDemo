package com.finance.service.impl;

import com.finance.dao.EnterpriseInfoDao;
import com.finance.dao.TradingDao;
import com.finance.dao.impl.EnterpriseInfoDaoImpl;
import com.finance.dao.impl.TradingDaoImpl;
import com.finance.entity.EnterpriseInfo;
import com.finance.service.EnterpriseInfoService;

import java.util.List;
import java.util.Map;

/**
 * @author:Wang
 * @className: EnterpriseInfoServiceImpl
 * @description:
 * @date: 2021/5/31 10:07
 * @version:0.1
 * @since:1.8
 */
public class EnterpriseInfoServiceImpl implements EnterpriseInfoService {
    @Override
    public List<EnterpriseInfo> getEnterpriseInfoByPage(String ename, int page, int pageSize) {
        EnterpriseInfoDao enterpriseInfoDao = new EnterpriseInfoDaoImpl();
        List<EnterpriseInfo> list = enterpriseInfoDao.getEnterpriseInfoByPage(ename, page, pageSize);
        return list;
    }

    @Override
    public Integer getEnterpriseTotalPage(String ename) {
        EnterpriseInfoDao enterpriseInfoDao = new EnterpriseInfoDaoImpl();
        Integer totalPage = enterpriseInfoDao.getEnterpriseTotalPage(ename);
        return totalPage;
    }

    @Override
    public Integer addEnterpriseInfo(Map<String, Object> paramMap) {
        EnterpriseInfoDao enterpriseInfoDao = new EnterpriseInfoDaoImpl();
        Integer result1 = enterpriseInfoDao.addEnterpriseInfo(paramMap);
        System.out.println(result1+"-----result1");
        Integer result2=0;
        if (result1>0){
            //独角兽企业添加成功后才会执行添加挂单信息的操作
            Object id = paramMap.get("enterprise_id");
            TradingDao tradingDao = new TradingDaoImpl();
            result2 =tradingDao.addStockTrading((Double)id);
        }
        return result1+result2;
    }

    @Override
    public EnterpriseInfo getEnterpriseByParms(String name, String code) {
        EnterpriseInfoDao enterpriseInfoDao = new EnterpriseInfoDaoImpl();
        EnterpriseInfo es = enterpriseInfoDao.getEnterpriseByParms(name, code);
        return es;
    }

    @Override
    public Integer editEnterpriseInfo(Map<String, Object> paramMap) {
        EnterpriseInfoDao enterpriseInfoDao = new EnterpriseInfoDaoImpl();
        Integer result = enterpriseInfoDao.editEnterpriseInfo(paramMap);
        return result;
    }

    @Override
    public List<String> getEnterpriseType() {
        EnterpriseInfoDao enterpriseInfoDao = new EnterpriseInfoDaoImpl();
        List<String> enterpriseType = enterpriseInfoDao.getEnterpriseType();
        return enterpriseType;
    }
}
