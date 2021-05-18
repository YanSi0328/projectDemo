package com.zhangci.service;

import com.zhangci.common.ServiceResponse;
import com.zhangci.entity.GoodsShopping;

import java.util.Map;

/**
 * ClassName: BuyGoodsServce
 * <p>
 * Author: ZhangCi
 * Description: 购买管理
 * Date: 2021/4/15 12:11
 * Version: 0.1
 * Since: JDK 1.8
 */
public interface BuyGoodsService {

    /**
     * 购物管理入口
     */
    void goodsShopMenu();

    /**
     * 向购物车增加商品信息
     *
     * @return 返回的是购物车信息，分别是账单号和商品信息
     */
    Map<Integer, GoodsShopping> loopStoreGoods();


    /**
     * 修改购物车信息
     * @return 操作是否成功
     */
    ServiceResponse updateGoodsNum();

    /**
     * 删除购物车信息
     */
    ServiceResponse deleteShopping();

    /**
     * 展示购物车信息
     * @return 操作是否成功
     */
    ServiceResponse showShopCar();

    /**
     * 支付方式
     *
     * @param money 支付的金额
     * @return 支付方式
     */
    int paymentWay(Float money);
}
