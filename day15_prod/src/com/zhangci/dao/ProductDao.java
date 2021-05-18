package com.zhangci.dao;

import com.zhangci.etity.Product;

import java.util.List;

/**
 * ClassName: ProductDao
 * Author: ZhangCi
 *
 * @description:
 * @date: 2021/5/14 10:03
 * @version: 0.1
 * @since: 1.8
 */
public interface ProductDao {
    /**
     * 根据指定id查询指定商品信息
     *
     * @param pordId
     * @return
     */
    Product selProductById(String pordId);

    List<Product> selAllProdMsg(Integer page, Integer pageSize, Product queryProd);

    /**
     * 根据要查询的商品对象统计记录条数
     *
     * @param queryProd 要查询的对象
     * @return 记录条数
     */
    Integer selAllProdCount(Product queryProd);


    /**
     * 新增商品信息
     *
     * @param addProd 新增的商品对象
     * @return 受影响的记录条数
     */
    Integer addProd(Product addProd);

    /**
     * 修改商品信息
     *
     * @param updaProd 修改的商品对象
     * @return 受影响的记录条数
     */
    Integer updateProd(Product updaProd);

    /**
     * 根据指定id 删除商品
     *
     * @param pid 商品id
     * @return 受影响的记录条数
     */
    Integer delProdById(String pid);
}
