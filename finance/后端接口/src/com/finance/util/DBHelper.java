package com.finance.util;

import com.alibaba.druid.pool.DruidDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * ClassName: DBHelper
 * Author: ZhangCi
 *
 * @description: druid辅助连接
 * @date: 2021/5/20 15:09
 * @version: 0.1
 * @since: 1.8
 */
public class DBHelper {
    public DBHelper() {
    }

    // 1 获得数据源
    private static DruidDataSource dataSource = new DruidDataSource();

    // 2 通过工厂模式创建数据库连接对象
    static {
        // 2.1 加载核心配置资源数据
        Properties prop = new Properties();
        try {
            // 2.2 加载资源
            prop.load(DBHelper.class.getResourceAsStream("/druid.properties"));
            // 2.3 获得数据源，默认初始化为0个最大为8个数据源
            dataSource.setDriverClassName(prop.getProperty("driverClassName"));// 驱动名
            dataSource.setUrl(prop.getProperty("dd.url"));
            dataSource.setUsername(prop.getProperty("username"));
            dataSource.setPassword(prop.getProperty("password"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获得数据源：获得随机一个可用的连接对象，连接一直够用
     *
     * @return 数据源
     */
//    public static DataSource getDataSource() {
//        return dataSource;
//    }

    /**
     * 从数据源中获得连接
     *
     * @return 数据库连接对象
     */
    public static Connection getConn() {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(connection);
        return connection;
    }

    public static void CloseConn(Connection conn, PreparedStatement ps, ResultSet rs) {
        try {
            // 资源先调用后关闭
            if (ps != null) ps.close();
            if (rs != null) rs.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println(getConn());
    }
}
