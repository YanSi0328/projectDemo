package com.finance.dao;

import com.finance.cons.PageInfo;
import com.finance.entity.ProdBasic;

import java.util.List;
import java.util.Map;

/**
 * ClassName: ProdAuditorDao
 * Author: ZhangCi
 *
 * @description: 数据持久层接口-产品审核
 * @date: 2021/6/1 9:53
 * @version: 0.1
 * @since: 1.8
 */
public interface ProdAuditorDao {

    /**
     * 根据分页信息查询产品审核信息
     *
     * @param params 封装的查询参数
     * @param pInfo  分页信息
     * @return 查询到的产品信息集合
     */
    List<ProdBasic> getProdAuditor(Map<String, Object> params, PageInfo pInfo);

    /**
     * 根据产品名称获得审核人和审核状态
     *
     * @param pName 产品名称
     * @return 信息集合
     */
    Map<String, Object> getReview(String pName);

    /**
     * 根据产品名称查询待审核产品信息
     *
     * @param pName 产品名称
     * @return 查询到的产品信息
     */
    List<ProdBasic> getAuditByName(String pName);

    /**
     * 修改产品的审核状态
     *
     * @param status 指定状态
     * @param pName  指定产品名
     * @return 操作记录数
     */
    Integer modifyReviewStatus(String status, String pName);
}
