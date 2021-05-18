package com.zhangci.dao;

import com.zhangci.entity.OrderDetails;
import com.zhangci.entity.OrderMsg;

/**
 * ClassName: OrderDao
 * <p>
 * Author: ZhangCi
 * Description:
 * Date: 2021/4/15 21:51
 * Version: 0.1
 * Since: JDK 1.8
 */
public interface OrderDao {
    /**
     * 新增订单信息表记录
     * @param orderMsg 订单信息
     * @return 受影响的sql记录
     */
    int addOrderMsg(OrderMsg orderMsg);

    /**
     * 新增订单详情表信息
     * @param orderDetails 订单对象
     * @return 受影响的sql记录
     */
    int addOrderDetails(OrderDetails orderDetails);

}
