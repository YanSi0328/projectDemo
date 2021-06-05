package com.finance.dao;

import com.finance.entity.StockTrading;

import java.util.Map;


/**
 * @author:Wang
 * @className: TradingDao
 * @description:
 * @date: 2021/5/31 21:16
 * @version:0.1
 * @since:1.8
 */
public interface TradingDao {
    /**
     * 查询企业挂单信息
     *
     * @param id 企业id
     * @return 挂单信息
     */
    StockTrading getStockTradingById(int id);

    /**
     * 修改企业挂单信息
     *
     * @param paramMap 挂单信息参数集合
     * @return 受影响的记录数
     */
    Integer editStockTradingById(Map<String, Object> paramMap);

    /**
     * 添加企业挂单信息
     *
     * @param id 企业id
     * @return 受影响的记录数
     */
    Integer addStockTrading(double id);
}
