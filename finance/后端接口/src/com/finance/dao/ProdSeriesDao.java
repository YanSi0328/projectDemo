package com.finance.dao;

import com.finance.cons.PageInfo;
import com.finance.entity.ProdSeries;

import java.util.List;

/**
 * ClassName: ProdSeriesDao
 * Author: ZhangCi
 *
 * @description: 接口：产品系列管理数据持久层
 * @date: 2021/5/24 22:52
 * @version: 0.1
 * @since: 1.8
 */
public interface ProdSeriesDao {

    /**
     * 查询总的记录数
     *
     * @param psName 系列名
     * @return 总记录数
     */
    Integer getTotalRecord(String psName);

    /**
     * 获得商品系列信息
     *
     * @param psName 系列名
     * @param pInfo  分页信息对象
     * @return 查询到的产品系列集合
     */
    List<ProdSeries> getMsgByPage(String psName, PageInfo pInfo);

    /**
     * 新增商品系列信息
     *
     * @param pSeries 待新增的数据
     * @return 受影响的数据库记录数
     */
    Integer addProdSeries(ProdSeries pSeries);


    /**
     * 汇款信息
     *
     * @param pSeries 封装的汇款信息对象
     * @return 受影响的数据库记录数
     */
    Integer addRemitMsg(ProdSeries pSeries);

    /**
     * 根据产品系列id查询指定信息
     *
     * @param psId 产品系列id
     * @return 查到的产品信息对象
     */
    ProdSeries getMsgById(Integer psId);

    /**
     * 修改产品系列的信息
     *
     * @param pSeries 封装的待修改的对象
     * @return 受影响的数据库记录数
     */
    Integer updateById(ProdSeries pSeries);
}
