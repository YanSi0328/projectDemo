package com.zhangci.service;

import com.zhangci.common.ServiceResponse;

/**
 * ClassName: SelectOrderService
 * <p>
 * Author: ZhangCi
 * Description:
 * Date: 2021/4/16 18:00
 * Version: 0.1
 * Since: JDK 1.8
 */
public interface SelectOrderService {

    /**
     * 订单查询主界面
     */
    void selectMainMenu();

    /**
     * 根据会员编号查询订单信息
     */
    ServiceResponse selectOrderMsgByUid();

    /**
     * 根据订单编号查询订单信息
     */
    ServiceResponse selectOrderMsgBuOid();
}
