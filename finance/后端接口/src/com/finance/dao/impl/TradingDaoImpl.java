package com.finance.dao.impl;

import com.finance.dao.TradingDao;
import com.finance.entity.StockTrading;
import com.finance.util.DBHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author:Wang
 * @className: TradingDaoImpl
 * @description:
 * @date: 2021/6/1 11:33
 * @version:0.1
 * @since:1.8
 */
public class TradingDaoImpl implements TradingDao {
    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    @Override
    public StockTrading getStockTradingById(int id) {
        return null;
    }

    @Override
    public Integer editStockTradingById(Map<String, Object> paramMap) {
        conn = DBHelper.getConn();
        String sql = "UPDATE stock_trading SET ";
        StringBuilder builder = new StringBuilder(sql);
        List<Object> list = new ArrayList<>(10);
        paramMap.forEach((s, o) -> {
            builder.append(s);
            builder.append("=");
            builder.append("?");
            builder.append(",");
            list.add(o);
        });
        builder.deleteCharAt(builder.lastIndexOf(","));
        builder.append(" WHERE id = " + (double) paramMap.get("id"));

        System.out.println(builder.toString());
        int result = 0;
        try {
            ps = conn.prepareStatement(builder.toString());
            for (int i = 0; i < list.size(); i++) {
                ps.setObject(i + 1, list.get(i));
            }
            result = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        DBHelper.CloseConn(conn, ps, null);
        return result;
    }

    @Override
    public Integer addStockTrading(double id) {
        int result = 0;
        conn = DBHelper.getConn();
        String sql = "INSERT INTO stock_trading (enterprise_id) VALUES (?)";
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, (int) id);
            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DBHelper.CloseConn(conn, ps, null);
        return result;
    }
}
