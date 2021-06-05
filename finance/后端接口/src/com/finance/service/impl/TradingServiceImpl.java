package com.finance.service.impl;

import com.finance.dao.TradingDao;
import com.finance.dao.impl.TradingDaoImpl;
import com.finance.service.TradingService;

import java.util.Map;

/**
 * @author:Wang
 * @className: TradingServiceImpl
 * @description:
 * @date: 2021/6/1 15:05
 * @version:0.1
 * @since:1.8
 */
public class TradingServiceImpl implements TradingService {
    @Override
    public Integer editStockTradingById(Map<String, Object> paramMap) {
        TradingDao tradingDao = new TradingDaoImpl();
        Integer result = tradingDao.editStockTradingById(paramMap);
        return result;
    }
}
