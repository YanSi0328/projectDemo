package com.finance.dao.impl;

import com.finance.dao.ProdSeriesDao;
import com.finance.cons.PageInfo;
import com.finance.entity.ProdSeries;
import com.finance.sql.ProdSql;
import com.finance.util.DBHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: ProdSeriesDaoImpl
 * Author: ZhangCi
 *
 * @description:
 * @date: 2021/5/26 23:00
 * @version: 0.1
 * @since: 1.8
 */
public class ProdSeriesDaoImpl implements ProdSeriesDao {
    private PreparedStatement ps;
    private Connection conn;
    private ResultSet rs;

    @Override
    public Integer getTotalRecord(String psName) {
        conn = DBHelper.getConn();
        String sql = ProdSql.TOTAL_PS_RECORD;
        if (psName != null && !"".equals(psName)) {
            // 拼接sql
            sql += " WHERE ps_name=?";
        }
        Integer total = null;
        System.out.println("getTotalRecord -> sql " + sql);

        try {
            ps = conn.prepareStatement(sql);
            if (sql.contains("?")) {
                // 此时需要占位符赋值
                ps.setString(1, psName);
            }
            rs = ps.executeQuery();
            while (rs.next()) {
                total = rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBHelper.CloseConn(conn, ps, rs);
        }
        return total;
    }

    @Override
    public List<ProdSeries> getMsgByPage(String psName, PageInfo pInfo) {
        List<ProdSeries> psList = new ArrayList<>(10);
        conn = DBHelper.getConn();
        String sql = ProdSql.SEL_ALL_PS;
        // 是否需要拼接sql
        if (psName != null && !"".equals(psName)) {
            // 拼接sql
            sql += " WHERE ps_name=?";
        }
        // 分页条件
        if (pInfo.getPage() != null) {
            sql += " limit " + (pInfo.getPage() - 1) * pInfo.getPageSize() + "," + pInfo.getPageSize();
        }
//        sql += " limit " + (pInfo.getPage() - 1) * pInfo.getPageSize() + "," + pInfo.getPageSize();
        System.out.println("getMsgByPage -> sql " + sql);
        try {
            ps = conn.prepareStatement(sql);
            if (sql.contains("?")) {
                // 此时需要占位符赋值
                ps.setString(1, psName);
            }
            // 执行查询
            rs = ps.executeQuery();
            while (rs.next()) {
                // ps_id,p_Cname,p_Ename,remit_info
                psList.add(new ProdSeries(rs.getInt("ps_id"),
                        rs.getString("p_Cname"),
                        rs.getString("p_Ename"),
                        rs.getString("remit_info")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBHelper.CloseConn(conn, ps, rs);
        }
        // 将查询到的产品系列集合进行返回
        return psList;
    }

    @Override
    public Integer addProdSeries(ProdSeries pSeries) {
        conn = DBHelper.getConn();
        String sql = ProdSql.ADD_PS_MSG;
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, pSeries.getPsId());
            ps.setString(2, pSeries.getPCanal());
            ps.setString(3, pSeries.getPsName());
            ps.setString(4, pSeries.getPCname());
            ps.setString(5, pSeries.getPEname());
            System.out.println("addProdSeries -> " + sql);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBHelper.CloseConn(conn, ps, null);
        }
        return null;
    }

    @Override
    public Integer addRemitMsg(ProdSeries pSeries) {
        conn = DBHelper.getConn();
        String sql = ProdSql.ADD_REMIT;
        System.out.println("addRemitMsg -> " + sql);
        try {
            ps = conn.prepareStatement(sql);
            ps.setObject(1, pSeries.getDueBankName());
            ps.setString(2, pSeries.getSwiftCode());
            ps.setString(3, pSeries.getDueBankArea());
            ps.setString(4, pSeries.getDueBankCity());
            ps.setString(5, pSeries.getBenefitName());
            ps.setString(6, pSeries.getBenefitAccount());
            ps.setString(7, pSeries.getBenefitAddress());
            ps.setString(8, pSeries.getRemitInfo());
            ps.setString(9, pSeries.getRemitRemark());
            ps.setString(10, pSeries.getBankCode());
            ps.setString(11, pSeries.getCnapsCode());
            ps.setObject(12, pSeries.getPsId());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBHelper.CloseConn(conn, ps, null);
        }
        return null;
    }

    @Override
    public ProdSeries getMsgById(Integer psId) {
        ProdSeries pSeries;
        conn = DBHelper.getConn();
        String sql = ProdSql.SEL_PS_ID;
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, psId);
            rs = ps.executeQuery();
            System.out.println("getMsgById -> " + sql);
            if (rs.next()) {
                // p_canal,ps_name,p_Cname,p_Ename
                return pSeries = new ProdSeries(rs.getString("p_canal"),
                        rs.getString("ps_name"),
                        rs.getString("p_Cname"),
                        rs.getString("p_Ename"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBHelper.CloseConn(conn, ps, rs);
        }
        return null;
    }

    @Override
    public Integer updateById(ProdSeries pSeries) {
        conn = DBHelper.getConn();
        String sql = ProdSql.UP_PS_ID;
        System.out.println("updateProdSeries -> " + sql);
        try {
            ps = conn.prepareStatement(sql);
            // p_canal=?,ps_name=?,p_Cname=?,p_Ename=?,update_time=NOW() WHERE ps_id=?
            ps.setString(1, pSeries.getPCanal());
            ps.setString(2, pSeries.getPsName());
            ps.setString(3, pSeries.getPCname());
            ps.setString(4, pSeries.getPEname());
            ps.setInt(5, pSeries.getPsId());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBHelper.CloseConn(conn, ps, null);
        }
        return null;
    }
}
