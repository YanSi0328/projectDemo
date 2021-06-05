package com.finance.dao;

import com.finance.entity.ProdEquity;

/**
 * ClassName: ProdEquityDao
 * Author: ZhangCi
 *
 * @description: 数据持久层-净值
 * @date: 2021/6/1 9:08
 * @version: 0.1
 * @since: 1.8
 */
public interface ProdEquityDao {

    /**
     * 根据产品名称查询净值表是否有对应的信息
     *
     * @param pName 产品名称
     * @return 查询到的数据记录数
     */
    Integer getProdName(String pName);

    /**
     * 新增净值信息
     *
     * @param pEquity 待新增的净值信息
     * @return 操作结果
     */
    Integer addPEMsg(ProdEquity pEquity);

    /**
     * 修改净值信息
     *
     * @param pEquity 待新增的净值信息
     * @return 操作结果
     */
    Integer modifyByPName(ProdEquity pEquity);
}
