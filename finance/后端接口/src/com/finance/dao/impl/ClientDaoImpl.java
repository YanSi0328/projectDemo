package com.finance.dao.impl;

import com.finance.dao.ClientDao;
import com.finance.entity.ClientInfo;
import com.finance.util.DBHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @author:Wang
 * @className: ClientDaoImpl
 * @description:
 * @date: 2021/5/30 15:00
 * @version:0.1
 * @since:1.8
 */
public class ClientDaoImpl implements ClientDao {

    @Override
    public List<ClientInfo> getClientByPage(int page, int pageSize, ClientInfo clientInfo) throws Exception {
        Connection conn = DBHelper.getConn();
        List<ClientInfo> list = new ArrayList<>(10);
        List parmList = new ArrayList(10);
//        不要用异常压制，
        String sql = "SELECT c.client_id , c.client_name,c.client_phone,e.enterprise_name,c.stock_amount,e.enterprise_itprice FROM client_info c LEFT JOIN enterprise_info e ON c.enterprise_id = e.enterprise_id ";
        StringBuilder builder = new StringBuilder(sql);
        boolean flag = true;
        if (clientInfo.getClientName() != null && !"".equals(clientInfo.getClientName())) {
            if (flag) {
                builder.append(" where");
                flag = false;
            } else {
                builder.append(" and");
            }

            builder.append(" c.client_name like ? ");
            //注意百分号之间的空格
            parmList.add("%" + clientInfo.getClientName() + "%");
        }
        if (clientInfo.getClientPhone() != null && !"".equals(clientInfo.getClientPhone())) {
            if (flag) {
                builder.append(" where");
                flag = false;
            } else {
                builder.append(" and");
            }
            builder.append(" c.client_phone like ? ");
            //注意百分号之间的空格
            parmList.add("%" + clientInfo.getClientPhone() + "%");
        }
        builder.append(" ORDER BY c.client_name LIMIT " + (page - 1) * pageSize + " ," + pageSize);
        //验证sql语句
        //System.out.println(builder.toString());

        PreparedStatement ps = conn.prepareStatement(builder.toString());
        for (int i = 0; i < parmList.size(); i++) {
            ps.setObject(i + 1, parmList.get(i));
        }

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            list.add(new ClientInfo(rs));
        }
        DBHelper.CloseConn(conn, ps, rs);
        return list;
    }


    @Override
    public int getClientTotalPage(ClientInfo clientInfo) throws Exception {
        List parmList = new ArrayList(10);
        Connection conn = DBHelper.getConn();
        int totalPage = 0;
        String sql = "SELECT count(1)as total_page FROM client_info c LEFT JOIN enterprise_info e ON c.enterprise_id = e.enterprise_id";
        StringBuilder builder = new StringBuilder(sql);
        boolean flag = true;
        if (clientInfo.getClientName() != null && !"".equals(clientInfo.getClientName())) {
            if (flag) {
                builder.append(" where");
                flag = false;
            } else {
                builder.append(" and");
            }

            builder.append(" c.client_name like ? ");
            //注意百分号之间的空格
            parmList.add("%" + clientInfo.getClientName() + "%");
        }
        if (clientInfo.getClientPhone() != null && !"".equals(clientInfo.getClientPhone())) {
            if (flag) {
                builder.append(" where");
                flag = false;
            } else {
                builder.append(" and");
            }
            builder.append(" c.client_phone like ? ");
            //注意百分号之间的空格
            parmList.add("%" + clientInfo.getClientPhone() + "%");
        }

        //验证sql语句
        System.out.println(builder.toString());

        PreparedStatement ps = conn.prepareStatement(builder.toString());
        for (int i = 0; i < parmList.size(); i++) {
            ps.setObject(i + 1, parmList.get(i));
        }

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            totalPage = rs.getInt("total_page");
        }
        DBHelper.CloseConn(conn, ps, rs);
        return totalPage;
    }
}
