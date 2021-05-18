package com.zhangci.dao;


import com.zhangci.entity.Goods;

import java.util.List;
import java.util.Map;

/**
 * ClassName: GoodsDao
 * <p>
 * Author: ZhangCi
 * Description: 商品管理dao
 * Date: 2021/4/13 19:19
 * Version: 0.1
 * Since: JDK 1.8
 */


public interface GoodsDao {
    /**
     * 添加商品信息
     *
     * @param goods 商品对象
     * @return sql执行结果
     */
    int addGoods(Goods goods);

    /**
     * 修改商品信息
     * 动态拼接sql语句的方式
     *
     * @param params 多个参数
     * @param id 指定商品的id
     * @return 受影响的记录条数
     */
    int updateGoods(Map<String, Object> params, int id);

    /**
     * 查询商品信息
     *
     * @return 商品集合
     */
    List<Goods> selectAllGoods();

    /**
     * 根据id查询单个商品的信息
     * @param id 商品的id
     * @return 受影响的sql语句数
     */
    Goods selectGoodsById(int id);

    /**
     * 根据id删除商品信息
     *
     * @param id 商品id
     * @return 受影响的记录数
     */
    int deleteGoodsById(int id);
}
