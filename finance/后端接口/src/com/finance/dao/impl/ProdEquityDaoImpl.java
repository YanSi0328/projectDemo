package com.finance.dao.impl;

import com.finance.dao.ProdEquityDao;
import com.finance.entity.ProdEquity;
import com.finance.sql.ProdSql;
import com.finance.util.DBHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * ClassName: ProdEquityDaoImpl
 * Author: ZhangCi
 *
 * @description:
 * @date: 2021/6/1 19:36
 * @version: 0.1
 * @since: 1.8
 */
public class ProdEquityDaoImpl implements ProdEquityDao {
    private PreparedStatement ps;
    private Connection conn;
    private ResultSet rs;

    @Override
    public Integer getProdName(String pName) {
        Integer num = null;
        conn = DBHelper.getConn();
        String sql = ProdSql.SEL_PE;
        System.out.println("getProdName -> " + sql);
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, pName);
            rs = ps.executeQuery();
            if (rs.next()) {
                num = rs.getInt("num");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBHelper.CloseConn(conn, ps, rs);
        }
        return num;
    }

    @Override
    public Integer addPEMsg(ProdEquity pEquity) {
        conn = DBHelper.getConn();
        String sql = ProdSql.ADD_PE;
        System.out.println("addPEMsg -> " + sql);
        try {
            ps = conn.prepareStatement(sql);
            // p_name,average,base_date,growth_rate
            ps.setString(1, pEquity.getProdName());
            ps.setInt(2, pEquity.getAverage());
            ps.setString(3, pEquity.getBaseDate());
            ps.setDouble(4, pEquity.getGrowthRate());

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBHelper.CloseConn(conn, ps, null);
        }
        return null;
    }

    @Override
    public Integer modifyByPName(ProdEquity pEquity) {
        conn = DBHelper.getConn();
        String sql = ProdSql.UP_PE;
        System.out.println("modifyByPName -> " + sql);
        try {
            ps = conn.prepareStatement(sql);
            // average=?,base_date=?,growth_rate=?   p_name=?
            ps.setInt(1, pEquity.getAverage());
            ps.setString(2, pEquity.getBaseDate());
            ps.setDouble(3, pEquity.getGrowthRate());
            ps.setString(4, pEquity.getProdName());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBHelper.CloseConn(conn, ps, null);
        }
        return null;
    }
}
