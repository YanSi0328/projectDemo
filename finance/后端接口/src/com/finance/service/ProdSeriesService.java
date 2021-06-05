package com.finance.service;

import com.finance.cons.PageInfo;
import com.finance.entity.ProdSeries;

import java.util.List;

/**
 * ClassName: ProdSeriesService
 * Author: ZhangCi
 *
 * @description: 接口：产品系列管理
 * @date: 2021/5/29 10:32
 * @version: 0.1
 * @since: 1.8
 */
public interface ProdSeriesService {

    /**
     * 根据指定字段查询总记录数
     *
     * @param serName 系列名
     * @return 总记录数
     */
    Integer getTotalRecord(String serName);

    /**
     * 条件查询
     *
     * @param serName 系列名
     * @param pInfo   页码信息对象
     * @return 产品系列集合
     */
    List<ProdSeries> getMsgByPage(String serName, PageInfo pInfo);

    /**
     * 新增产品系列信息
     *
     * @param ps 产品系列对象
     * @return 操作记录
     */
    Integer addProdSeries(ProdSeries ps);

    /**
     * 根据产品系列id查询指定信息
     *
     * @param psId 产品系列id
     * @return 查到的产品信息对象
     */
    ProdSeries getMsgById(Integer psId);

    /**
     * 修改
     *
     * @param pSeries 封装的待修改的对象
     * @return 受影响的数据库记录数
     */
    Integer updateById(ProdSeries pSeries);

    /**
     * 新增汇款信息
     *
     * @param pSeries 汇款信息
     * @return 受影响的数据库记录数
     */
    Integer addRemitMsg(ProdSeries pSeries);
}
