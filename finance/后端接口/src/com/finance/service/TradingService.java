package com.finance.service;

import java.util.Map;

/**
 * @author:Wang
 * @className: TradingService
 * @description:
 * @date: 2021/6/1 15:04
 * @version:0.1
 * @since:1.8
 */
public interface TradingService {
    /**
     * 修改企业挂单信息
     *
     * @param paramMap 挂单信息参数集合
     * @return 受影响的记录数
     */
    Integer editStockTradingById(Map<String, Object> paramMap);
}
