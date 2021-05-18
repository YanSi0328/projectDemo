package com.zhangci.utils;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * ClassName: DBHelper
 *
 * Author: ZhangCi
 * Description: 数据库辅助连接类
 * Date: 2021/5/10 17:45
 * Version: 0.1
 * Since: JDK 1.8
 */
public class DBHelper {

    // 数据库相关信息
    private static String username;
    private static String password;
    private static String url;
    private static String driver;

    static {
        Properties prop = new Properties();
        try {
            prop.load(DBHelper.class.getResourceAsStream("/jdbc.properties"));
            username = prop.getProperty("jdbc.username");
            password = prop.getProperty("jdbc.userPass");
            url = prop.getProperty("jdbc.mysqlUrl");
            driver = prop.getProperty("jdbc.driverName");
            // 加载数据库驱动
            Class.forName(driver);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // 打开数据库连接
    public static Connection getConn() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(conn);
        return conn;

    }

    // 关闭数据库连接
    public static void closeConn(Connection conn, Statement stat, ResultSet rs, PreparedStatement ps) {
        try {
            // 先调用的资源，后关闭
            if (stat != null) stat.close();
            if (ps != null) ps.close();
            if (rs != null) rs.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
//
//    public static void main(String[] args) {
//        Connection conn = DBHelper.getConn();
//        System.out.println(conn);
//    }
}