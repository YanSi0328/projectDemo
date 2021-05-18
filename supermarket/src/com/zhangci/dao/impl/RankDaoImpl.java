package com.zhangci.dao.impl;

import com.zhangci.dao.RankDao;
import com.zhangci.sql.CashierSql;
import com.zhangci.util.DBHelper;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * ClassName: RankDaoImpl
 * <p>
 * Author: ZhangCi
 * Description:
 * Date: 2021/4/16 22:34
 * Version: 0.1
 * Since: JDK 1.8
 */
public class RankDaoImpl implements RankDao {

    private Connection connection;
    private String sql;


    @Override
    public List<Map<String, Object>> selectSalesTopTen(int month) {

        connection = DBHelper.getMysqlConnection(true);
        sql = CashierSql.SELECT_TOPTEN_SALES;

        try {
            return new QueryRunner().query(connection, sql, new MapListHandler(), month);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBHelper.closeResources(connection, null, null);
        }
        return null;
    }

    @Override
    public BigDecimal selectTotalSales(int month) {


        connection = DBHelper.getMysqlConnection(true);
        sql = CashierSql.SELECT_TOSAL_MONTH;

        try {
            return new QueryRunner().query(connection, sql, new ScalarHandler<>(), month);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBHelper.closeResources(connection, null, null);
        }
        return null;
    }
}
