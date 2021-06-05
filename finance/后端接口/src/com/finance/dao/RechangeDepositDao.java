package com.finance.dao;

import com.finance.entity.RechangeDeposit;

import java.util.List;

/**
 * @author:Wang
 * @className: RechangeDepositDao
 * @description:
 * @date: 2021/6/3 13:42
 * @version:0.1
 * @since:1.8
 */
public interface RechangeDepositDao {
    /**
     * 多条件查询
     *
     * @param page     页码
     * @param pageSize 每页记录数
     * @param rd       充值提现信息类
     * @return 充值提现信息记录
     */
    List<RechangeDeposit> getAllRechangeDeposit(int page, int pageSize, RechangeDeposit rd);

    /**
     * 总记录数查询
     *
     * @param page     页码
     * @param pageSize 每页记录数
     * @param rd       充值提现信息类
     * @return 充值提现信息记录数
     */
    int getTotalRechangeDeposit(int page, int pageSize, RechangeDeposit rd);

    /**
     * @return所有不同类型
     */
    List getAllType();

    /**
     * @return所有不同状态
     */
    List getALLStatue();
}
