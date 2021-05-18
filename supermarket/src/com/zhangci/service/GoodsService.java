package com.zhangci.service;

import com.zhangci.common.ServiceResponse;
import com.zhangci.entity.Goods;

/**
 * ClassName: GoodsService
 * <p>
 * Author: ZhangCi
 * Description:
 * Date: 2021/4/13 20:55
 * Version: 0.1
 * Since: JDK 1.8
 */
public interface GoodsService {

    /**
     * 增加商品信息
     *
     * @param goods 商品对象
     * @return 添加商品后的结果
     */
    ServiceResponse addGoods(Goods goods);

    /**
     * 修改商品信息
     *
     * @param goods 商品对象
     * @return 修改操作的结果
     */
    ServiceResponse updateGoods(Goods goods);

    /**
     * 查询所有商品信息
     */
    void selectGoods();

    /**
     * 根据id查询单个商品信息
     */
    void selectGoodsById();

    /**
     * 根据id删除商品信息
     *
     * @return 操作结果
     */
    ServiceResponse deleteGoodsById();

    /**
     * 显示商品信息
     *
     * @param goods 实例化的商品
     * @return 操作是否成功
     */
    ServiceResponse showGoods(Goods goods);
}
