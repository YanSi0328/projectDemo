package com.zhangci.service;

import com.zhangci.etity.Product;

import java.util.List;

/**
 * ClassName: ProductService
 * Author: ZhangCi
 *
 * @description:
 * @date: 2021/5/14 10:04
 * @version: 0.1
 * @since: 1.8
 */
public interface ProductService {

    Product selProductById(String pordId);

    Integer selAllProdCount(Integer pageSize, Product queryProd);

    List<Product> selAllProdMsg(Integer page, Integer pageSize, Product queryProd);

    /**
     * 新增商品信息
     * @param addProd 新增的商品对象
     * @return 受影响的记录条数
     */
    Integer addProd(Product addProd);

    /**
     * 新增商品信息
     * @param updaProd 新增的商品对象
     * @return 受影响的记录条数
     */
    Integer updatProd(Product updaProd);

    /**
     * 根据指定id 删除商品
     * @param pid 商品id
     * @return 受影响的记录条数
     */
    Integer delProdById(String pid);
}
