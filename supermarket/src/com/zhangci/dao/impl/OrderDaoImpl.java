package com.zhangci.dao.impl;

import com.zhangci.dao.OrderDao;
import com.zhangci.entity.OrderDetails;
import com.zhangci.entity.OrderMsg;
import com.zhangci.sql.CashierSql;
import com.zhangci.util.DBHelper;

import java.sql.*;

/**
 * ClassName: OrderDaoImpl
 * <p>
 * Author: ZhangCi
 * Description:
 * Date: 2021/4/15 21:52
 * Version: 0.1
 * Since: JDK 1.8
 */
public class OrderDaoImpl implements OrderDao {
    private Connection connection;
    private PreparedStatement ps;
    private String sql;


    @Override
    public int addOrderMsg(OrderMsg orderMsg) {
        connection = DBHelper.getMysqlConnection(true);
        sql = CashierSql.ADD_ORDER_MSG;
        try {
            ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            //会员id 总金额 支付类型
            ps.setObject(1, orderMsg.getOrderUserId());
            ps.setObject(2, orderMsg.getOrderTotal());
            ps.setObject(3, orderMsg.getOrderType());
            ps.executeUpdate();
            ResultSet incrementId = ps.getGeneratedKeys();
            if (incrementId.next()) {
                //将自增主键返回出去
                return incrementId.getInt(1);
            }
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBHelper.closeResources(connection, ps, null);
        }
        return 0;
    }

    @Override
    public int addOrderDetails(OrderDetails orderDetails) {
        connection = DBHelper.getMysqlConnection(true);
        sql = CashierSql.ADD_ORDER_DETAILS;

        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, orderDetails.getODetailsOrderId());
            ps.setObject(2, orderDetails.getODetailsGoodsId());
            ps.setObject(3, orderDetails.getODetailsBGNum());

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBHelper.closeResources(connection, ps, null);
        }
        return 0;
    }
}
