package com.finance.service;

import com.finance.cons.PageInfo;
import com.finance.entity.ProdRecommend;

import java.util.List;
import java.util.Map;

/**
 * ClassName: ProdRecomService
 * Author: ZhangCi
 *
 * @description: 业务逻辑层-产品推荐
 * @date: 2021/6/1 21:12
 * @version: 0.1
 * @since: 1.8
 */
public interface ProdRecommendService {
    /**
     * 根据产品名称查询总记录数
     *
     * @param seriesName 产品系列名称
     * @return 总记录
     */
    Integer getTotal(String seriesName);

    /**
     * 根据系列名称查询推荐信息
     *
     * @param seriesName 系列名称
     * @param pInfo      分页信息
     * @return 查询到的推荐信息集合
     */
    List<ProdRecommend> getMsgByParams(String seriesName, PageInfo pInfo);

    /**
     * 新增产品推荐信息
     *
     * @param prodRecommend 新增产品推荐信息
     * @return 操作记录数
     */
    Integer addPRMsg(ProdRecommend prodRecommend);

    /**
     * 修改产品推荐信息
     *
     * @param prodRecommend 产品推荐信息
     * @return 操作记录数
     */
    Integer modifyPRMsg(ProdRecommend prodRecommend);

    /**
     * 获取产品信息表中的产品名称
     *
     * @return 产品名称集合
     */
    List<String> getByProdName();

    /**
     * 根据系列名称获取产品id和产品名称键值对
     *
     * @param pCategory 产品系列
     * @return 查询到的集合
     */
    List<Map<String, Object>> relatedMsg(String pCategory);

    /**
     * 根据产品id修改关联信息
     *
     * @param pidStr
     * @param pId    产品id
     * @return 受影响的记录数
     */
    Integer modifyRelated(String pidStr, Integer pId);
}
