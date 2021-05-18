package com.zhangci.dao.impl;

import com.zhangci.dao.SelectOrderDao;
import com.zhangci.sql.CashierSql;
import com.zhangci.util.DBHelper;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * ClassName: SelectOrderDaoImpl
 * <p>
 * Author: ZhangCi
 * Description:
 * Date: 2021/4/16 17:49
 * Version: 0.1
 * Since: JDK 1.8
 */
public class SelectOrderDaoImpl implements SelectOrderDao {

    private Connection connection;
    private String sql;

    @Override
    public List<Map<String, Object>> selectOrderMsgByUid(int uid) {
        connection = DBHelper.getMysqlConnection(true);

        sql = CashierSql.SELECT_BY_UID;

        //DBUtils
        QueryRunner queryRunner = new QueryRunner();
        //返回的是多行记录
        MapListHandler uidMapList = new MapListHandler();
        try {
            //执行sql语句，返回的结果集是map，一个会员可以多次购买商品，因此在订单表中会员编号不具有唯一性
            return  queryRunner.query(connection,sql,uidMapList,uid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Map<String, Object>> selectOrderMsgBuOid(int oid) {
        connection = DBHelper.getMysqlConnection(true);

        sql = CashierSql.SELECT_BU_OID;

        try {
            //因为订单编号的唯一性，故每次最多可以查询到一条记录
            return new QueryRunner().query(connection,sql,new MapListHandler(),oid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
