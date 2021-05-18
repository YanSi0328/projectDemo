package com.zhangci.sql;

/**
 * ClassName: CashierSql
 * <p>
 * Author: ZhangCi
 * Description:
 * Date: 2021/4/15 21:53
 * Version: 0.1
 * Since: JDK 1.8
 */
public interface CashierSql {
    //所有字段不区分大小写

    // 向订单信息表新增数据
    String ADD_ORDER_MSG = "INSERT INTO orderMsg (uid,total,time,type)VALUES(?,?,now(),?)";

    // 向订单详情表新增数据
    String ADD_ORDER_DETAILS = "INSERT INTO orderDetails (oid,gid,bgNum)VALUES(?,?,?)";


    // 会员编号
    // 订单编号，商品编号，购买数量，会员号，总金额，下单时间，支付方式
    String SELECT_BY_UID = "SELECT oid,gid,bgNum,uid,total,time,type FROM orderDetails od,orderMsg om WHERE od.oid = om.id AND om.uid = ?";

    // 订单编号
    String SELECT_BU_OID = "SELECT oid, gid, bgNum, uid, total, time, type FROM orderDetails od,orderMsg om WHERE od.oid = om.id AND oid = ?";


    // 查询本月销量前十的订单:Query the orders of the top ten sales in this month
    // 商品类别    该商品本月销量    月份
    String SELECT_TOPTEN_SALES = "SELECT g.type,sum(od.bgNum) from orderDetails od INNER JOIN orderMsg om on od.oid = om.id,goods g\n" +
            "WHERE g.id = od.gid AND DATE_FORMAT(om.time,'%c') = ?\n" +
            "GROUP BY g.type\n" +
            "ORDER BY bgNum DESC\n" +
            "LIMIT 10";

    // 查询本月总销售额:Total sales of this month
    // 月份   总销量
    String SELECT_TOSAL_MONTH = "SELECT sum(od.bgNum)  from orderDetails od INNER JOIN orderMsg om\n" +
            "on od.oid = om.id\n" +
            "WHERE DATE_FORMAT(om.time,'%c') = ?";

}
