package com.finance.dao.impl;

import com.finance.cons.PageInfo;
import com.finance.dao.ProdAuditorDao;
import com.finance.entity.ProdBasic;
import com.finance.sql.ProdSql;
import com.finance.util.DBHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: ProdAuditorDaoImpl
 * Author: ZhangCi
 *
 * @description: 数据持久层实现类-产品审核
 * @date: 2021/6/1 20:25
 * @version: 0.1
 * @since: 1.8
 */
public class ProdAuditorDaoImpl implements ProdAuditorDao {
    private PreparedStatement ps;
    private Connection conn;
    private StringBuilder builder;
    private ResultSet rs;

    @Override
    public List<ProdBasic> getProdAuditor(Map<String, Object> params, PageInfo pInfo) {
        List<ProdBasic> paList = new ArrayList<>(10);
        conn = DBHelper.getConn();
        String sql;
        builder = new StringBuilder(ProdSql.SEL_AUDIT);
        // 拼接参数
        if (params.size() != 0) {
            builder.append(" WHERE ");
            params.forEach((k, v) -> {
                builder.append(k + " LIKE '" + "%" + v + "%");
                builder.append("' AND ");
            });
            sql = builder.substring(0, builder.lastIndexOf("AND") - 1);
        } else {
            sql = builder.toString();
        }
        // 分页条件
        sql += " limit " + (pInfo.getPage() - 1) * pInfo.getPageSize() + "," + pInfo.getPageSize();
        System.out.println("getProdAuditor -> " + sql);

        try {
            ps = conn.prepareStatement(sql);
            //  p_name,p_series_category,p_creator,p_create_time,p_update_time
            rs = ps.executeQuery();
            while (rs.next()) {
                paList.add(new ProdBasic(rs.getString("p_name"),
                        rs.getString("p_series_category"),
                        rs.getString("p_creator"),
                        rs.getString("p_create_time"),
                        rs.getString("p_update_time")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBHelper.CloseConn(conn, ps, rs);
        }

        return paList;
    }

    @Override
    public Map<String, Object> getReview(String pName) {
        Map<String, Object> reviewMap = new HashMap<>(16);
        conn = DBHelper.getConn();
        String sql = ProdSql.SEL_REVIEW;
        System.out.println("getReview -> " + sql);
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, pName);
            rs = ps.executeQuery();
            if (rs.next()) {
                reviewMap.put("status", rs.getString("review_status"));
                reviewMap.put("reviewer", rs.getString("p_reviewer"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBHelper.CloseConn(conn, ps, rs);
        }
        return reviewMap;
    }

    @Override
    public List<ProdBasic> getAuditByName(String pName) {
        String sql = null;
        // 在产品基础信息中有提供此查询的方法
        System.out.println("getAuditByName -> " + sql);
        return null;
    }

    @Override
    public Integer modifyReviewStatus(String status, String pName) {
        conn = DBHelper.getConn();
        String sql = ProdSql.UP_RA_STATUS;
        //  review_status=?,review_time=now() WHERE p_name=?
        System.out.println("modifyReviewStatus -> " + sql);
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, status);
            ps.setString(2, pName);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBHelper.CloseConn(conn, ps, null);
        }
        return null;
    }
}
