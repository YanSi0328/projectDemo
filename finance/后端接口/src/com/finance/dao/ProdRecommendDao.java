package com.finance.dao;

import com.finance.cons.PageInfo;
import com.finance.entity.ProdRecommend;

import java.util.List;
import java.util.Map;

/**
 * ClassName: ProdRecommendDao
 * Author: ZhangCi
 *
 * @description: 数据持久层接口-产品推荐
 * @date: 2021/6/1 9:34
 * @version: 0.1
 * @since: 1.8
 */
public interface ProdRecommendDao {

    /**
     * 根据参数，查询总的记录数
     *
     * @param seriesName 待查询的参数
     * @return 总记录数
     */
    Integer getTotal(String seriesName);

    /**
     * 根据分页信息查询产品基础信息
     *
     * @param seriesName 产品系列名称
     * @param pInfo      分页信息
     * @return 查询到的产品信息集合
     */
    List<ProdRecommend> getMsgByParams(String seriesName, PageInfo pInfo);

    /**
     * 新增推荐信息
     *
     * @param prodRecommend 产品推荐信息
     * @return 操作记录数
     */
    Integer addPRMsg(ProdRecommend prodRecommend);

    /**
     * 修改产品推荐信息，根据产品名称来进行修改，产品名称也封装入产品对象
     *
     * @param prodRecommend 产品推荐信息
     * @return 操作记录数
     */
    Integer modifyMsg(ProdRecommend prodRecommend);

    /**
     * 根据系列名称获取产品id和产品名称键值对
     *
     * @param pCategory 产品系列
     * @return 查询到的集合
     */
    List<Map<String, Object>> relatedMsg(String pCategory);

    /**
     * 根据产品名称修改关联信息
     *
     * @param relatedPid 关联信息
     * @param pId        产品id
     * @return 受影响的记录数
     */
    Integer modifyRelated(String relatedPid, Integer pId);
}
