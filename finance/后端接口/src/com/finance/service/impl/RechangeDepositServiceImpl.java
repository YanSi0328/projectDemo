package com.finance.service.impl;

import com.finance.dao.RechangeDepositDao;
import com.finance.dao.impl.RechangeDepositDaoImpl;
import com.finance.entity.RechangeDeposit;
import com.finance.service.RechangeDepositService;

import java.util.List;

/**
 * @author:Wang
 * @className: RechangeDepositServiceImpl
 * @description:
 * @date: 2021/6/3 15:23
 * @version:0.1
 * @since:1.8
 */
public class RechangeDepositServiceImpl implements RechangeDepositService {
    @Override
    public List<RechangeDeposit> getAllRechangeDeposit(int page, int pageSize, RechangeDeposit rd) {

        RechangeDepositDao rdd = new RechangeDepositDaoImpl();
        List<RechangeDeposit> allRechangeDeposit = rdd.getAllRechangeDeposit(page, pageSize, rd);
        return allRechangeDeposit;
    }

    @Override
    public int getTotalRechangeDeposit(int page, int pageSize, RechangeDeposit rd) {
        RechangeDepositDao rdd = new RechangeDepositDaoImpl();
        int total = rdd.getTotalRechangeDeposit(page, pageSize, rd);
        return total;
    }

    @Override
    public List getAllType() {
        RechangeDepositDao rdd = new RechangeDepositDaoImpl();
        List allType = rdd.getAllType();
        return allType;
    }

    @Override
    public List getALLStatue() {
        RechangeDepositDao rdd = new RechangeDepositDaoImpl();
        List allStatue = rdd.getALLStatue();
        return allStatue;
    }
}
