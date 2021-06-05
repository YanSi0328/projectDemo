package com.finance.dao.impl;

import com.finance.dao.EnterpriseInfoDao;
import com.finance.entity.EnterpriseInfo;
import com.finance.util.DBHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @author:Wang
 * @className: EnterpriseInfoDaoImpl
 * @description:
 * @date: 2021/5/31 9:48
 * @version:0.1
 * @since:1.8
 */
public class EnterpriseInfoDaoImpl implements EnterpriseInfoDao {
    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    @Override
    public List<EnterpriseInfo> getEnterpriseInfoByPage(String ename, int page, int pageSize) {
        conn = DBHelper.getConn();
        List<EnterpriseInfo> list = new ArrayList<>(10);
        String sql = "SELECT e.enterprise_name,e.enterprise_code,e.enterprise_sprice,e.enterprise_rate,e.enterprise_id FROM enterprise_info e";
        if (ename != null && !"".equals(ename)) {
            sql = sql + " where e.enterprise_name like ?";
        }
        sql = sql + " limit " + (page - 1) * pageSize + " , " + pageSize;
        System.out.println(sql);
        try {
            ps = conn.prepareStatement(sql);
            if (ename != null && !"".equals(ename)) {
                ps.setString(1, ename);
            }
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new EnterpriseInfo(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //必须释放资源
        DBHelper.CloseConn(conn, ps, rs);
        return list;
    }

    @Override
    public Integer getEnterpriseTotalPage(String ename) {
        conn = DBHelper.getConn();
        int totalPage = 0;
        String sql = "SELECT count(1) AS totalPage FROM enterprise_info e";
        if (ename != null && !"".equals(ename)) {
            sql = sql + " where e.enterprise_name like ?";
        }
        System.out.println(sql);
        try {
            ps = conn.prepareStatement(sql);

            if (ename != null && !"".equals(ename)) {
                ps.setString(1, ename);
            }
            rs = ps.executeQuery();
            while (rs.next()) {
                totalPage = rs.getInt("totalPage");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DBHelper.CloseConn(conn, ps, rs);
        return totalPage;
    }

    @Override
    public Integer addEnterpriseInfo(Map<String, Object> paramMap) {
        conn = DBHelper.getConn();
        String sql = "INSERT INTO enterprise_info (";
        StringBuilder builder = new StringBuilder(sql);
        paramMap.forEach((s, o) -> {
            builder.append(s);
            builder.append(",");
        });
        builder.deleteCharAt(builder.lastIndexOf(","));
        builder.append(") ");
        builder.append("values (");

        List<Object> list = new ArrayList();
        System.out.println(list);
        paramMap.forEach((s, o) -> {
            list.add(o);
            builder.append("?");
            builder.append(",");
        });
        builder.deleteCharAt(builder.lastIndexOf(","));
        builder.append(") ");


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
    public EnterpriseInfo getEnterpriseByParms(String name, String code) {
        conn = DBHelper.getConn();
        String sql = "SELECT * FROM enterprise_info WHERE enterprise_name = ? AND enterprise_code = ?";
        EnterpriseInfo es = new EnterpriseInfo();
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, code);
            rs = ps.executeQuery();
            if (rs.next()) {
                es.setEnterpriseName(rs.getString("enterprise_name"));
                es.setEnterpriseCode(rs.getString("enterprise_code"));
                es.setEnterpriseId(rs.getInt("enterprise_id"));
                es.setEnterpriseRate(rs.getDouble("enterprise_rate"));
                es.setEnterpriseAddress(rs.getString("enterprise_address"));
                es.setEnterpriseLogo(rs.getString("enterprise_logo"));
                es.setEnterpriseAlogo(rs.getString("enterprise_alogo"));
                es.setEnterpriseType(rs.getString("enterprise_type"));
                es.setEnterpriseFounding(rs.getDate("enterprise_founding"));
                es.setEnterpriseCeo(rs.getString("enterprise_ceo"));
                es.setEnterpriseDesc(rs.getString("enterprise_desc"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DBHelper.CloseConn(conn, ps, rs);
        return es;
    }

    @Override
    public Integer editEnterpriseInfo(Map<String, Object> paramMap) {
        System.out.println(paramMap.get("enterpriseRate") + "?????");
        double rate = (double) paramMap.get("enterpriseRate");
        double id = (double) paramMap.get("enterpriseId");
        conn = DBHelper.getConn();
        String sql = "UPDATE enterprise_info SET enterprise_address = ?, enterprise_alogo = ?,enterprise_ceo = ?," +
                "enterprise_code = ?,enterprise_desc = ?,enterprise_founding = ?,enterprise_logo = ?,enterprise_rate = ?," +
                "enterprise_type = ?, enterprise_name = ? where enterprise_id = ?";
        int result = 0;
        try {
            ps = conn.prepareStatement(sql);
            ps.setObject(1, paramMap.get("enterpriseAddress"));
            ps.setObject(2, paramMap.get("enterpriseAlogo"));
            ps.setObject(3, paramMap.get("enterpriseCeo"));
            ps.setObject(4, paramMap.get("enterpriseCode"));
            ps.setObject(5, paramMap.get("enterpriseDesc"));
            ps.setObject(6, paramMap.get("enterpriseFounding"));
            ps.setObject(7, paramMap.get("enterpriseLogo"));
            ps.setObject(9, paramMap.get("enterpriseType"));

            ps.setDouble(8, rate);

            ps.setObject(10, paramMap.get("enterpriseName"));
            ps.setDouble(11, id);
            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        DBHelper.CloseConn(conn, ps, null);
        return result;
    }

    @Override
    public List<String> getEnterpriseType() {
        List<String> typeList = new ArrayList<>(10);
        conn = DBHelper.getConn();
        String sql = "SELECT DISTINCT enterprise_type FROM enterprise_info ";
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                typeList.add(rs.getString("enterprise_type"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DBHelper.CloseConn(conn, ps, rs);
        return typeList;
    }


}
