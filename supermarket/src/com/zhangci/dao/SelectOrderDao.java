package com.zhangci.dao;

import java.util.List;
import java.util.Map;

/**
 * ClassName: SelectOrderDao
 * <p>
 * Author: ZhangCi
 * Description: 商品订单记录查询dao
 * Date: 2021/4/13 19:23
 * Version: 0.1
 * Since: JDK 1.8
 */
public interface SelectOrderDao {

    /**
     * 根据会员编号查询订单记录
     * @param uid 会员编号
     * @return 查询结果（多行结果）
     */
    List<Map<String, Object>> selectOrderMsgByUid(int uid);


    /**
     * 根据订单编号查询订单记录
     * @param oid 订单编号
     * @return 查询结果（单行结果）
     */
    List<Map<String, Object>> selectOrderMsgBuOid(int oid);


}
