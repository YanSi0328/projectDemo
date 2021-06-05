package com.finance.dao.impl;

import com.finance.dao.RechangeDepositDao;
import com.finance.entity.RechangeDeposit;
import com.finance.util.DBHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author:Wang
 * @className: RechangeDepositDaoImpl
 * @description:
 * @date: 2021/6/3 14:58
 * @version:0.1
 * @since:1.8
 */
public class RechangeDepositDaoImpl implements RechangeDepositDao {
    private static Connection conn;
    private static PreparedStatement ps;
    private static ResultSet rs;

    @Override
    public List<RechangeDeposit> getAllRechangeDeposit(int page, int pageSize, RechangeDeposit rd) {
        List<RechangeDeposit> rdList = new ArrayList<>(10);
        conn = DBHelper.getConn();
        String sql = "SELECT * FROM  recharge_deposit r";

        StringBuilder builder = new StringBuilder(sql);
        List<Object> paramList = new ArrayList(10);
        boolean flag = true;

        if (rd.getClientName() != null && !"".equals(rd.getClientName())) {
            if (flag) {
                builder.append(" WHERE");
                flag = false;
            } else {
                builder.append(" AND");
            }
            builder.append(" r.client_name = ?");
            paramList.add(rd.getClientName());
        }
        if (rd.getClientPhone() != null && !"".equals(rd.getClientPhone())) {
            if (flag) {
                builder.append(" WHERE");
                flag = false;
            } else {
                builder.append(" AND");
            }
            builder.append(" r.client_phone = ?");
            paramList.add(rd.getClientPhone());
        }
        if (rd.getTradType() != null && !"".equals(rd.getTradType()) && rd.getTradType() != 0) {
            if (flag) {
                builder.append(" WHERE");
                flag = false;
            } else {
                builder.append(" AND");
            }
            builder.append(" r.trad_type = ?");
            paramList.add(rd.getTradType());
        }
        if (rd.getStatue() != null && !"".equals(rd.getStatue()) && rd.getStatue() != 0) {
            if (flag) {
                builder.append(" WHERE");
                flag = false;
            } else {
                builder.append(" AND");
            }
            builder.append(" r.statue = ?");
            paramList.add(rd.getStatue());
        }
        builder.append(" LIMIT " + (page - 1) * pageSize + "," + pageSize);
        System.out.println(builder.toString());
        try {
            ps = conn.prepareStatement(builder.toString());
            for (int i = 0; i < paramList.size(); i++) {
                ps.setObject(i + 1, paramList.get(i));
                System.out.println(paramList.get(i));
            }
            rs = ps.executeQuery();
            while (rs.next()) {
                rdList.add(new RechangeDeposit(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DBHelper.CloseConn(conn, ps, rs);
        return rdList;
    }

    @Override
    public int getTotalRechangeDeposit(int page, int pageSize, RechangeDeposit rd) {

        conn = DBHelper.getConn();
        String sql = "SELECT count(1) total FROM recharge_deposit r";
        StringBuilder builder = new StringBuilder(sql);
        List<Object> paramList = new ArrayList(10);
        boolean flag = true;
        if (rd.getClientName() != null && !"".equals(rd.getClientName())) {
            if (flag) {
                builder.append(" WHERE");
                flag = false;
            } else {
                builder.append(" AND");
            }
            builder.append(" r.client_name = ?");
            paramList.add(rd.getClientName());
        }
        if (rd.getClientPhone() != null && !"".equals(rd.getClientPhone())) {
            if (flag) {
                builder.append(" WHERE");
                flag = false;
            } else {
                builder.append(" AND");
            }
            builder.append(" r.client_phone = ?");
            paramList.add(rd.getClientPhone());
        }

        if (rd.getTradType() != null && !"".equals(rd.getTradType()) && rd.getTradType() != 0) {
            if (flag) {
                builder.append(" WHERE");
                flag = false;
            } else {
                builder.append(" AND");
            }
            builder.append(" r.trad_type = ?");
            paramList.add(rd.getTradType());
        }
        if (rd.getStatue() != null && !"".equals(rd.getStatue()) && rd.getStatue() != 0) {
            if (flag) {
                builder.append(" WHERE");
                flag = false;
            } else {
                builder.append(" AND");
            }
            builder.append(" r.statue = ?");
            paramList.add(rd.getStatue());
        }
//        builder.append(" LIMIT " + (page - 1) * pageSize + "," + pageSize);
        System.out.println(builder.toString());
        int result = 0;
        try {
            ps = conn.prepareStatement(builder.toString());
            for (int i = 0; i < paramList.size(); i++) {
                ps.setObject(i + 1, paramList.get(i));
            }
            rs = ps.executeQuery();
            if (rs.next()) {
                result = rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DBHelper.CloseConn(conn, ps, rs);
        return result;
    }

    @Override
    public List getAllType() {
        List<Integer> typeList = new ArrayList<>(10);
        conn = DBHelper.getConn();
        String sql = "SELECT DISTINCT trad_type  FROM recharge_deposit";
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                typeList.add(rs.getInt("trad_type"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DBHelper.CloseConn(conn, ps, rs);
        return typeList;
    }

    @Override
    public List getALLStatue() {
        List<Integer> typeList = new ArrayList<>(10);
        conn = DBHelper.getConn();
        String sql = "SELECT DISTINCT statue FROM recharge_deposit";
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                typeList.add(rs.getInt("statue"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DBHelper.CloseConn(conn, ps, rs);
        return typeList;
    }
}
