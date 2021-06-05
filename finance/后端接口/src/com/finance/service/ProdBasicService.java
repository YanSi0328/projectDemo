package com.finance.service;

import com.finance.cons.PageInfo;
import com.finance.entity.ProdBasic;

import java.util.List;
import java.util.Map;

/**
 * ClassName: ProdBasicService
 * Author: ZhangCi
 *
 * @description: 业务逻辑层-产品基本信息
 * @date: 2021/6/1 11:15
 * @version: 0.1
 * @since: 1.8
 */
public interface ProdBasicService {

    /**
     * 根据参数查询记录数
     *
     * @param params 参数
     * @return 总记录
     */
    Integer getTotal(Map<String, Object> params);

    /**
     * 根据参数进行查询
     *
     * @param params 参数列表
     * @param pInfo  页码信息
     * @return 查询到的集合
     */
    List<ProdBasic> getBasicByParams(Map<String, Object> params, PageInfo pInfo);

    /**
     * 新增产品基础信息
     *
     * @param prodBasic 待新增的产品基础信息
     * @return 操作记录数
     */
    Integer addMsg(ProdBasic prodBasic, String loginUser);

    /**
     * 修改信息
     *
     * @param prodBasic 待修改的产品信息
     * @return 操作记录数
     */
    Integer modifyMsg(ProdBasic prodBasic);

    /**
     * 根据产品名称查询产品信息
     *
     * @param prodName 指定产品名称
     * @return 查询到的产品信息
     */
    ProdBasic getMsgByProdName(String prodName);
}
