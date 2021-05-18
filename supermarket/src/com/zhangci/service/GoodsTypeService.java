package com.zhangci.service;

import com.zhangci.common.ServiceResponse;
import com.zhangci.entity.GoodsType;

/**
 * ClassName: GoodsTypeService
 * <p>
 * Author: ZhangCi
 * Description:
 * Date: 2021/4/14 19:16
 * Version: 0.1
 * Since: JDK 1.8
 */
public interface GoodsTypeService {

    /**
     * 添加商品类型信息
     *
     * @param goodsType 商品类型对象
     * @return
     */
    ServiceResponse addGoodsType(GoodsType goodsType);

    /**
     * 修改商品类型信息
     *
     * @param goodsType 商品类型对象
     * @return 操作是否成功
     */
    ServiceResponse updateGoodsType(GoodsType goodsType);

    /**
     * 查询全部商品类型信息
     */
    void selectGoodsType();

    /**
     * 根据id删除商品类型信息
     *
     * @return 操作是否成功
     */
    ServiceResponse deleteGoodsTypeById();
}
