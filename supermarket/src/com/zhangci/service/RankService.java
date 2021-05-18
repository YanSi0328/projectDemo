package com.zhangci.service;

import com.zhangci.common.ServiceResponse;

/**
 * ClassName: RankService
 * <p>
 * Author: ZhangCi
 * Description: 排行统计业务逻辑
 * Date: 2021/4/16 22:47
 * Version: 0.1
 * Since: JDK 1.8
 */
public interface RankService {

    /**
     * 按月份及按商品类别统计销量前10的商品,以及本月总销量
     * @return 操作是否成功
     */
    ServiceResponse topTenAndTotalSalMonth();
}
