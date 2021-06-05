package com.finance.service;

import com.finance.entity.RechangeDeposit;

import java.util.List;

/**
 * @author:Wang
 * @className: RechangeDepositService
 * @description:
 * @date: 2021/6/3 15:22
 * @version:0.1
 * @since:1.8
 */
public interface RechangeDepositService {
    /**
     * 多条件查询
     *
     * @param page     页码
     * @param pageSize 每页记录数
     * @param rd       充值提现信息类
     * @return
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
