package com.zhangci.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * ClassName: RankDao
 * <p>
 * Author: ZhangCi
 * Description: 商品排行dao
 * Date: 2021/4/13 19:23
 * Version: 0.1
 * Since: JDK 1.8
 */
public interface RankDao {

    /**
     * 按月份及按商品类别统计销量前10的商品
     *
     * @param month 月份
     * @return 返回查询后的商品类别和该商品本月销量的数据
     */
    List<Map<String, Object>> selectSalesTopTen(int month);

    /**
     * 查询本月总销量
     *
     * @param month 月份
     * @return 总销量
     */
    BigDecimal selectTotalSales(int month);
}
