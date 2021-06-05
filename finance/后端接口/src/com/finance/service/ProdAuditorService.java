package com.finance.service;

import com.finance.cons.PageInfo;
import com.finance.entity.ProdBasic;

import java.util.List;
import java.util.Map;

/**
 * ClassName: ProdAuditorService
 * Author: ZhangCi
 *
 * @description: 业务逻辑层接口-产品审核
 * @date: 2021/6/2 15:44
 * @version: 0.1
 * @since: 1.8
 */
public interface ProdAuditorService {
    /**
     * 根据参数查询总记录数
     *
     * @param params 参数列表
     * @return 总记录
     */
    Integer getTotal(Map<String, Object> params);

    /**
     * 根据指定参数查询
     *
     * @param params 参数集合
     * @param pInfo  页码信息
     * @return 查询到的产品审核信息集合
     */
    List<ProdBasic> getMsgByParams(Map<String, Object> params, PageInfo pInfo);

    /**
     * 根据产品名称修改产品审核状态
     *
     * @param auditStatus 待修改的审核状态
     * @param pName       产品名称
     * @return 受影响的数据库记录数
     */
    Integer modifyReviewStatus(String auditStatus, String pName);

    /**
     * 根据产品名称查询到审核信息
     *
     * @param pName 产品名称
     * @return 审核信息集合
     */
    Map<String, Object> getReviewMsg(String pName);

    /**
     * 查询到待审核的产品信息
     *
     * @param prodName 产品名
     * @return 待审核产品
     */
    ProdBasic getReviewProd(String prodName);
}
