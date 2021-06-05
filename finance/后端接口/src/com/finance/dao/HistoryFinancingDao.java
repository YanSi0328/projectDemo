package com.finance.dao;

import com.finance.entity.HistoryFinancing;

import java.util.List;

/**
 * @author:Wang
 * @className: HistoryFinancingDao
 * @description:
 * @date: 2021/6/1 16:26
 * @version:0.1
 * @since:1.8
 */
public interface HistoryFinancingDao {
    /**
     * @param name 企业名称
     * @return 企业融资记录
     */
    List<HistoryFinancing> getHistoryFinancing(String name);
}
