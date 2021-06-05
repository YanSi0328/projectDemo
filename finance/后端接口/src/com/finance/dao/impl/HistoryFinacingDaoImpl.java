package com.finance.dao.impl;

import com.finance.dao.HistoryFinancingDao;
import com.finance.entity.HistoryFinancing;
import com.finance.util.DBHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author:Wang
 * @className: HistoryFinacingDaoImpl
 * @description:
 * @date: 2021/6/1 16:30
 * @version:0.1
 * @since:1.8
 */
public class HistoryFinacingDaoImpl implements HistoryFinancingDao {
    private static Connection conn;
    private static PreparedStatement ps;
    private static ResultSet rs;

    @Override
    public List<HistoryFinancing> getHistoryFinancing(String name) {
        conn = DBHelper.getConn();
        List<HistoryFinancing> list = new ArrayList<>(10);
        String sql = "SELECT * FROM history_financing WHERE enterprise_name like ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + name + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new HistoryFinancing(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DBHelper.CloseConn(conn, ps, null);
        return list;
    }
}
