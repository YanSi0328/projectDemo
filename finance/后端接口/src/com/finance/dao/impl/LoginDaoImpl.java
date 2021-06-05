package com.finance.dao.impl;

import com.finance.dao.LoginDao;
import com.finance.entity.AdminUser;
import com.finance.util.DBHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * @author:HuJingJing
 * @className: LoginDaoImpl
 * @deacription:
 * @date: 2021/5/28 16:15
 * @version: 0.1
 * @since: 1.8
 */
public class LoginDaoImpl implements LoginDao {
    private static PreparedStatement ps;
    private static Connection conn;
    private static String sql;
    ResultSet rs = null;
    AdminUser loginAd = null;

//    @Override
//    public Integer login(String adminName, String adminPassword) {
//        conn = DBHelper.getConn();
//        Integer roleId=null;
//        sql="SELECT a.role_id FROM admin_user a where a.admin_name=? and a.admin_password=?";
//        try {
//            ps=conn.prepareStatement(sql);
//            ps.setString(1,adminName);
//            ps.setString(2,adminPassword);
//            rs=ps.executeQuery();
//            while (rs.next()){
//                roleId= rs.getInt("role_id");
//                }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }finally {
//            DBHelper.CloseConn(conn,ps,rs);
//        }
//        return roleId;
//    }


    @Override
    public AdminUser Login(AdminUser insertAd) {
        conn = DBHelper.getConn();
        sql = "SELECT a.admin_id,a.admin_name,a.admin_create_time,a.admin_password FROM admin_user a where a.admin_name=? and a.admin_password=?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, insertAd.getAdminName());
            ps.setString(2, insertAd.getAdminPassword());
            rs = ps.executeQuery();
            while (rs.next()) {
                Integer adminId = rs.getInt("admin_id");
                String adminName = rs.getString("admin_name");
                Date adminCreateTime = new Date(rs.getTimestamp("admin_create_time").getTime());
                String adminPassword = rs.getString("admin_password");
                loginAd = new AdminUser(adminId, adminName, adminCreateTime, adminPassword);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBHelper.CloseConn(conn, ps, rs);
        }
        return loginAd;
    }

    @Override
    public String getAdminRole(AdminUser insertAd) {
        conn = DBHelper.getConn();
        String menuId = "";
//        sql="SELECT a.menu_id FROM admin_user a where a.admin_name=?and a.admin_password=?";
        sql = "SELECT a.menu_id FROM admin_user a where a.admin_id=?";
        try {
            ps = conn.prepareStatement(sql);
//            ps.setString(1,insertAd.getAdminName());
//            ps.setString(2,insertAd.getAdminPassword());
            ps.setInt(1, insertAd.getAdminId());

            rs = ps.executeQuery();
            while (rs.next()) {
                menuId = rs.getString("menu_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBHelper.CloseConn(conn, ps, rs);
        }
        return menuId;
    }
}

