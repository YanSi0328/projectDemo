package com.zhangci.util;

import java.sql.*;


/**
 * ClassName: DBHelper
 * <p>
 * Author: ZhangCi
 * Description: 数据库辅助连接类
 * Date: 2021/4/11 17:01
 * Version: 0.1
 * Since: JDK 1.8
 */
public class DBHelper {
    public DBHelper() {
    }

    //数据库基本信息
    private static String name;
    private static String password;
    private static String url;

    //连接对象的获得交给ThreadLocal来维护
    private static final ThreadLocal<Connection> CONNECTION_THREAD_LOCAL = new ThreadLocal<>();


    //通过配置PropsUtil获得数据库的基本信息
    static {
        name = PropsUtil.getValue("jdbc.name");
        password = PropsUtil.getValue("jdbc.pass");
        url = PropsUtil.getValue("jdbc.url");

        //加载驱动：也是在只注册一次
        try {
            Class.forName(PropsUtil.getValue("jdbc.driver"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    /**
     * 获得数据库的连接
     */
    public static Connection getMysqlConnection(boolean flag) {
        Connection connection = CONNECTION_THREAD_LOCAL.get();

        //利用jdbc获取数据库连接，先开的后关
        try {
            if (connection == null) {
                connection = DriverManager.getConnection(url, name, password);
                connection.setAutoCommit(flag);
                CONNECTION_THREAD_LOCAL.set(connection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("数据库连接失败");
        }
        return connection;
    }

    /**
     * 关闭资源
     */
    public static void closeResources(Connection connection, PreparedStatement ps, ResultSet rs) {

        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();

            if (connection != null) {
                connection.close();
                //移除连接线程
                CONNECTION_THREAD_LOCAL.remove();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("关闭连接异常");
        }
    }
}
