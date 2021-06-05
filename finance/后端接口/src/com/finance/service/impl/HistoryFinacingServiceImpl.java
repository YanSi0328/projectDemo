package com.finance.service.impl;

import com.finance.dao.HistoryFinancingDao;
import com.finance.dao.impl.HistoryFinacingDaoImpl;
import com.finance.entity.HistoryFinancing;
import com.finance.service.HistoryFinancingService;

import java.util.List;

/**
 * @author:Wang
 * @className: HistoryFinacingServiceImpl
 * @description:
 * @date: 2021/6/1 16:51
 * @version:0.1
 * @since:1.8
 */
public class HistoryFinacingServiceImpl implements HistoryFinancingService {
    @Override
    public List<HistoryFinancing> getHistoryFinancing(String name) {
        HistoryFinancingDao hf = new HistoryFinacingDaoImpl();
        List<HistoryFinancing> hfList = hf.getHistoryFinancing(name);
        return hfList;
    }
}
