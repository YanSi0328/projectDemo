package com.finance.dao.impl;

import com.finance.cons.PageInfo;
import com.finance.dao.ProdBasicDao;
import com.finance.entity.ProdBasic;
import com.finance.sql.ProdSql;
import com.finance.util.DBHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ClassName: ProdBasicDaoImpl
 * Author: ZhangCi
 *
 * @description: 产品基础信息
 * @date: 2021/5/27 18:08
 * @version: 0.1
 * @since: 1.8
 */
public class ProdBasicDaoImpl implements ProdBasicDao {
    private PreparedStatement ps;
    private Connection conn;
    private StringBuilder builder;
    private ResultSet rs;

    @Override
    public Integer getTotal(Map<String, Object> params) {
        Integer total = null;
        String sql;
        conn = DBHelper.getConn();
        builder = new StringBuilder(ProdSql.SEL_PB_TOTAL);
        // 拼接参数
        if (params.size() != 0) {
            builder.append(" WHERE ");
            params.forEach((k, v) -> {
                builder.append(k);
                builder.append(" LIKE '" + "%" + v + "%");
                builder.append("' AND ");
            });
            sql = builder.substring(0, builder.lastIndexOf("AND") - 1);
        } else {
            sql = builder.toString();
        }
        System.out.println("getTotal -> " + sql);
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
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
    public List<ProdBasic> getProdByPage(Map<String, Object> params, PageInfo pInfo) {
        List<ProdBasic> pbList = new ArrayList<>(10);
        String sql;
        conn = DBHelper.getConn();
        builder = new StringBuilder(ProdSql.SEL_PB_MSG);
        // 拼接参数
        if (params.size() != 0) {
            builder.append(" WHERE ");
            params.forEach((k, v) -> {
                builder.append(k + " LIKE '%" + v + "%");
                builder.append("' AND ");
            });
            sql = builder.substring(0, builder.lastIndexOf("AND") - 1);
        } else {
            sql = builder.toString();
        }
        // 分页信息 为其他方法提供查询
        if (pInfo.getPage() != null) {
            sql += " limit " + (pInfo.getPage() - 1) * pInfo.getPageSize() + "," + pInfo.getPageSize();
        }
        System.out.println("getProdByPage -> " + sql);

        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                // p_name,p_category,p_series_category,admin_organ,open_date,fund_currency,review_status
                Integer pId = Integer.parseInt(rs.getString("p_id"));
                String pName = rs.getString("p_name");
                String pCategory = rs.getString("p_category");
                String pSeriesCategory = rs.getString("p_series_category");
                String adminOrgan = rs.getString("admin_organ");
                String openDate = rs.getString("open_date");
                String fundCurrency = rs.getString("fund_currency");
                Integer reviewStatus = Integer.parseInt(rs.getString("review_status"));
                pbList.add(new ProdBasic(pId, pName, pCategory, pSeriesCategory, adminOrgan, fundCurrency, openDate, reviewStatus));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBHelper.CloseConn(conn, ps, rs);
        }

        return pbList;
    }

    @Override
    public ProdBasic getProdByProdName(String pName) {
        ProdBasic pBasic = null;
        conn = DBHelper.getConn();
        String sql = ProdSql.SEL_BY_NAME;
        System.out.println("getProdByProdName -> " + sql);
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, pName);
            rs = ps.executeQuery();
            if (rs.next()) {
                // p_category,p_series_category,p_name,admin_organ,annualized_yield,fund_currency,open_date,
                // subscript_cycle,relative_manage_cost,subscript_rate,start_amount,charge_mode,redeem_cycle,
                // init_redeem_amount,redeem_rate,redeem_lockup,p_reviewer
                // Double 类型的字段：数据库中的数据不能为空，否则就会出现类型转换错误
                String pCategory = rs.getString("p_category");
                String pSeriesCategory = rs.getString("p_series_category");
                String adminOrgan = rs.getString("admin_organ");
                Double yield = Double.parseDouble(rs.getString("annualized_yield"));
                String fundCurrency = rs.getString("fund_currency");
                String openDate = rs.getString("open_date");
                String subscriptCycle = rs.getString("subscript_cycle");
                Double relativeManageCost = Double.parseDouble(rs.getString("relative_manage_cost"));
                Double subscriptRate = Double.parseDouble(rs.getString("subscript_rate"));
                Double startAmount = Double.parseDouble(rs.getString("start_amount"));
                String chargeMode = rs.getString("charge_mode");
                String redeemCycle = rs.getString("redeem_cycle");
                Double initRedeemAmount = Double.parseDouble(rs.getString("init_redeem_amount"));
                Double redeemRate = Double.parseDouble(rs.getString("redeem_rate"));
                Integer redeemLockup = Integer.parseInt(rs.getString("redeem_lockup"));
                String pReviewer = rs.getString("p_reviewer");
                pBasic = new ProdBasic(pCategory, pSeriesCategory, pName, adminOrgan, yield, fundCurrency, openDate, subscriptCycle, relativeManageCost,
                        subscriptRate, startAmount, chargeMode, redeemCycle, initRedeemAmount, redeemRate, redeemLockup, pReviewer);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBHelper.CloseConn(conn, ps, rs);
        }
        return pBasic;
    }

    @Override
    public List<String> getProdName() {
        List<String> nameList = new ArrayList<>(10);
        conn = DBHelper.getConn();
        String sql = ProdSql.SEL_PROD_NAME;
        System.out.println("getProdName -> " + sql);
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                nameList.add(rs.getString("p_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBHelper.CloseConn(conn, ps, rs);
        }
        return nameList;
    }

    @Override
    public Integer addProd(ProdBasic pBasic) {
        conn = DBHelper.getConn();
        String sql = ProdSql.ADD_PB_MSG;
        System.out.println("addProd -> " + sql);
        try {
            ps = conn.prepareStatement(sql);
            // p_id,p_category,p_series_category,p_name,admin_organ,annualized_yield,fund_currency," +
            //            "open_date,subscript_cycle,relative_manage_cost,subscript_rate,start_amount,charge_mode,redeem_cycle,init_redeem_amount," +
            //            "redeem_rate,redeem_lockup,p_reviewer,p_creator,
            ps.setInt(1, pBasic.getProdId());
            ps.setString(2, pBasic.getProdCategory());
            ps.setString(3, pBasic.getProdSecCategory());
            ps.setString(4, pBasic.getProdName());
            ps.setString(5, pBasic.getAdminOrgan());
            ps.setDouble(6, pBasic.getAnnualizedYield());
            ps.setString(7, pBasic.getFundCurrency());
            ps.setObject(8, pBasic.getOpenDate());
            ps.setString(9, pBasic.getSubscriptCycle());
            ps.setDouble(10, pBasic.getRelativeManageCost());
            ps.setDouble(11, pBasic.getSubscriptionRate());
            ps.setDouble(12, pBasic.getStartAmount());
            ps.setString(13, pBasic.getChargeMode());
            ps.setString(14, pBasic.getRedeemCycle());
            ps.setDouble(15, pBasic.getInitRedeemAmount());
            ps.setDouble(16, pBasic.getRedeemRate());
            ps.setInt(17, pBasic.getRedeemLockUp());
            ps.setString(18, pBasic.getReviewer());
            ps.setString(19, pBasic.getCreator());

            // 执行
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBHelper.CloseConn(conn, ps, null);
        }
        return null;
    }

    @Override
    public Integer modifyProd(ProdBasic pBasic) {
        conn = DBHelper.getConn();
        String sql = ProdSql.UP_PB;
        System.out.println("modifyProd -> " + sql);
        try {
            ps = conn.prepareStatement(sql);
            /*p_category=?,p_series_category=?,admin_organ=?,annualized_yield=?,fund_currency=?,
            open_date=?,subscript_cycle=?,relative_manage_cost=?,subscript_rate=?,start_amount=?,charge_mode=?,redeem_cycle=?,
            init_redeem_amount=?,redeem_rate=?,redeem_lockup=?,p_reviewer=?,p_update_time=now() WHERE p_name=?"
             */
            ps.setString(1, pBasic.getProdCategory());
            ps.setString(2, pBasic.getProdSecCategory());
            ps.setString(3, pBasic.getAdminOrgan());
            ps.setDouble(4, pBasic.getAnnualizedYield());
            ps.setString(5, pBasic.getFundCurrency());
            ps.setString(6, pBasic.getOpenDate());
            ps.setString(7, pBasic.getSubscriptCycle());
            ps.setDouble(8, pBasic.getRelativeManageCost());
            ps.setDouble(9, pBasic.getSubscriptionRate());
            ps.setDouble(10, pBasic.getStartAmount());
            ps.setString(11, pBasic.getChargeMode());
            ps.setString(12, pBasic.getRedeemCycle());
            ps.setDouble(13, pBasic.getInitRedeemAmount());
            ps.setDouble(14, pBasic.getRedeemRate());
            ps.setInt(15, pBasic.getRedeemLockUp());
            ps.setString(16, pBasic.getReviewer());
            ps.setString(17, pBasic.getProdName());

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBHelper.CloseConn(conn, ps, null);
        }
        return null;
    }
}
