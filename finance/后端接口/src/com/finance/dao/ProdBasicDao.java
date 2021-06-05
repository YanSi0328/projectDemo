package com.finance.dao;

import com.finance.cons.PageInfo;
import com.finance.entity.ProdBasic;

import java.util.List;
import java.util.Map;

/**
 * ClassName: ProdBasicDao
 * Author: ZhangCi
 *
 * @description: 数据持久层接口-产品基础信息
 * @date: 2021/6/1 9:14
 * @version: 0.1
 * @since: 1.8
 */
public interface ProdBasicDao {

    /**
     * 根据参数，查询总的记录数
     *
     * @param params 待查询的参数
     * @return 总记录数
     */
    Integer getTotal(Map<String, Object> params);

    /**
     * 根据分页信息查询产品基础信息
     *
     * @param params 封装的查询参数
     * @param pInfo  分页信息
     * @return 查询到的产品信息集合
     */
    List<ProdBasic> getProdByPage(Map<String, Object> params, PageInfo pInfo);

    /**
     * 根据产品名称查询产品信息
     *
     * @param pName 产品名称
     * @return 查询到的产品信息
     */
    ProdBasic getProdByProdName(String pName);

    /**
     * 查询所有的商品名称，提供给产品推荐的新增
     *
     * @return 查询到的产品名集合
     */
    List<String> getProdName();

    /**
     * 新增产品信息
     *
     * @param pBasic 待新增的数据
     * @return 操作记录数
     */
    Integer addProd(ProdBasic pBasic);

    /**
     * 修改产品信息，根据产品名称来进行修改，产品名称也封装入产品对象
     *
     * @param pBasic 待修改的产品信息
     * @return 操作记录数
     */
    Integer modifyProd(ProdBasic pBasic);

}
