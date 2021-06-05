package com.finance.service;

import com.finance.entity.ProdEquity;

/**
 * ClassName: ProdEquityService
 * Author: ZhangCi
 *
 * @description: 业务逻辑层-净值
 * @date: 2021/6/1 19:59
 * @version: 0.1
 * @since: 1.8
 */
public interface ProdEquityService {
    /**
     * 新增产品净值信息
     *
     * @param prodEquity 产品净值信息
     * @return 操作记录数
     */
    Integer dealEquity(ProdEquity prodEquity);
}
