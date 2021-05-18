package com.zhangci.dao;
import com.zhangci.entity.GoodsType;

import java.util.List;
import java.util.Map;

/**
 * ClassName: GoodsTypeDao
 * <p>
 * Author: ZhangCi
 * Description: 商品类型管理
 * Date: 2021/4/13 19:18
 * Version: 0.1
 * Since: JDK 1.8
 */
public interface GoodsTypeDao {
    /**
     * 添加商品类型信息
     *
     * @param goodsType 商品类型对象
     * @return sql执行结果
     */
    int addGoodsType(GoodsType goodsType);

    /**
     * 修改商品信息
     * 动态拼接sql语句的方式
     *
     * @param params 多个参数
     * @param id 指定商品的id
     * @return 受影响的记录条数
     */
    int updateGoodsType(Map<String, Object> params, int id);

    /**
     * 查询商品类型信息
     *
     * @return 商品类型集合
     */
    List<GoodsType> selectAllGoodsType();

    /**
     * 根据id删除商品信息
     *
     * @param id 商品类型id
     * @return 受影响的记录数
     */
    int deleteGoodsByIdType(int id);


}
