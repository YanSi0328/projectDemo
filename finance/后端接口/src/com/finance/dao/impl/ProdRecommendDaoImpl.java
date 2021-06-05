package com.finance.dao.impl;

import com.finance.cons.PageInfo;
import com.finance.dao.ProdRecommendDao;
import com.finance.entity.ProdRecommend;
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
 * ClassName: ProdRecommendDaoImpl
 * Author: ZhangCi
 *
 * @description: 数据持久层实现类-产品推荐
 * @date: 2021/6/1 19:49
 * @version: 0.1
 * @since: 1.8
 */
public class ProdRecommendDaoImpl implements ProdRecommendDao {
    private PreparedStatement ps;
    private Connection conn;
    private StringBuilder builder;
    private ResultSet rs;

    @Override
    public Integer getTotal(String seriesName) {
        Integer total = null;
        conn = DBHelper.getConn();
        String sql = ProdSql.SEL_PR_TOTAL;

        // 拼接参数
        if (seriesName != null && !"".equals(seriesName)) {
            sql += " WHERE p_category='" + seriesName + "'";
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
    public List<ProdRecommend> getMsgByParams(String seriesName, PageInfo pInfo) {
        List<ProdRecommend> prList = new ArrayList<>(10);
        conn = DBHelper.getConn();
        String sql = ProdSql.SEL_PR_MSG;
        // 拼接参数
        if (seriesName != null && !"".equals(seriesName)) {
            sql += " WHERE p_category='" + seriesName + "'";
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
                // p_name,degree,first_round,online_purchase,rank
                int id = rs.getInt("id");
                String pName = rs.getString("p_name");
                String pCategory = rs.getString("p_category");
                Integer degree = rs.getInt("degree");
                Integer firstRound = rs.getInt("first_round");
                Integer onlinePurchase = rs.getInt("online_purchase");
                Integer rank = rs.getInt("rank");
                prList.add(new ProdRecommend(id, pName, pCategory, degree, firstRound, onlinePurchase, rank));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBHelper.CloseConn(conn, ps, rs);
        }
        return prList;
    }

    @Override
    public Integer addPRMsg(ProdRecommend prodRecommend) {
        conn = DBHelper.getConn();
        String sql = ProdSql.ADD_PR;
        System.out.println("addPRMsg -> " + sql);
        try {
            ps = conn.prepareStatement(sql);
            // id,p_name,p_category,degree,online_purchase,invest_visible,first_round,reason,
            ps.setInt(1, prodRecommend.getId());
            ps.setString(2, prodRecommend.getProdName());
            ps.setString(3, prodRecommend.getProdCategory());
            ps.setInt(4, prodRecommend.getDegree());
            ps.setInt(5, prodRecommend.getOnlinePurchase());
            ps.setInt(6, prodRecommend.getInvestVisible());
            ps.setInt(7, prodRecommend.getFirstRound());
            ps.setString(8, prodRecommend.getReason());
            ps.setInt(9, prodRecommend.getRank());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBHelper.CloseConn(conn, ps, rs);
        }
        return null;
    }

    @Override
    public Integer modifyMsg(ProdRecommend prodRecommend) {
        conn = DBHelper.getConn();
        String sql = ProdSql.UP_PR;
        System.out.println("modifyMsg -> " + sql);
        try {
            ps = conn.prepareStatement(sql);
            //  degree=?,online_purchase=?,invest_visible=?,first_round=?,reason=?,update_time=NOW() WHERE p_name=?
            ps.setInt(1, prodRecommend.getDegree());
            ps.setInt(2, prodRecommend.getOnlinePurchase());
            ps.setInt(3, prodRecommend.getInvestVisible());
            ps.setInt(4, prodRecommend.getFirstRound());
            ps.setString(5, prodRecommend.getReason());
            ps.setString(6, prodRecommend.getProdName());
            int record = ps.executeUpdate();
            System.out.println("record -> " + record);
            return record;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBHelper.CloseConn(conn, ps, null);
        }
        return null;
    }

    @Override
    public List<Map<String, Object>> relatedMsg(String pCategory) {
        List<Map<String, Object>> relatedList = new ArrayList<>(10);
        conn = DBHelper.getConn();
        String sql = ProdSql.SEL_ID_NAME;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, pCategory);
            System.out.println("relatedMsg -> " + sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                HashMap<String, Object> hashMap = new HashMap<>(16);
                hashMap.put("id", rs.getInt("id"));
                hashMap.put("pName", rs.getString("p_name"));
                relatedList.add(hashMap);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBHelper.CloseConn(conn, ps, rs);
        }
        return relatedList;
    }

    @Override
    public Integer modifyRelated(String relatedPid, Integer pId) {
        conn = DBHelper.getConn();
        String sql = ProdSql.UP_RELATE_PID;
        System.out.println("modifyRelated -> " + sql);
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, relatedPid);
            ps.setInt(2, pId);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBHelper.CloseConn(conn, ps, null);
        }
        return null;
    }
}
