package com.zhangci.dao.impl;

import com.zhangci.dao.ProductDao;
import com.zhangci.etity.Product;
import com.zhangci.utils.DBHelper;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: ProductDaoImpl
 * Author: ZhangCi
 *
 * @description:
 * @date: 2021/5/14 10:04
 * @version: 0.1
 * @since: 1.8
 */
public class ProductDaoImpl implements ProductDao {
    private Connection connection;
    private PreparedStatement ps;
    private ResultSet rs;
    private String sql;

    @Override
    public Product selProductById(String pordId) {
        Product prod = null;
        connection = DBHelper.getConn();
        sql = "SELECT p.pid,p.pname,p.price,p.pimg,p.pdesc FROM product p WHERE p.pid=?";
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, pordId);
            rs = ps.executeQuery();
            // pid、pname、price、pimg、pdesc
            while (rs.next()) {
                String pid = rs.getString("pid");
                String pname = rs.getString("pname");
                BigDecimal price = rs.getBigDecimal("price");
                String pimg = rs.getString("pimg");
                String pdesc = rs.getString("pdesc");
                prod = new Product(pid, pname, price, pimg, pdesc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBHelper.closeConn(connection, null, rs, ps);
        }
        return prod;
    }

    @Override
    public List<Product> selAllProdMsg(Integer page, Integer pageSize, Product queryProd) {
        List<Product> provList = new ArrayList<>(10);
        List paramList = new ArrayList(10);
        connection = DBHelper.getConn();
        sql = "SELECT p.pid,p.pname,p.price,p.pimg,p.pdesc FROM product p";

        // 模糊查询
        Boolean isWhere = true;
        // 根据 id 查询
        if (queryProd.getPid() != null && !"".equals(queryProd.getPid())) {
            if (isWhere) {
                sql += " WHERE ";
                isWhere = false;
            } else {
                sql += " AND ";
            }
            sql += " p.pid=?";
            paramList.add(queryProd.getPid());
        }

        // 根据 name 模糊查询
        if (queryProd.getPname() != null && !"".equals(queryProd.getPname())) {
            if (isWhere) {
                sql += " WHERE ";
                isWhere = false;
            } else {
                sql += " AND ";
            }
            sql += " p.pname LIKE ? ";
            paramList.add("%" + queryProd.getPname() + "%");
        }

        // 分页条件
        sql += " limit " + (page - 1) * pageSize + "," + pageSize;
        System.out.println("执行的sql语句 " + sql);

        try {
            ps = connection.prepareStatement(sql);
            // 占位符赋值
            for (int i = 0; i < paramList.size(); i++) {
                ps.setObject(i + 1, paramList.get(i));
            }
            rs = ps.executeQuery();
            // pid、pname、price、pimg、pdesc
            while (rs.next()) {
                String pid = rs.getString("pid");
                String pname = rs.getString("pname");
                BigDecimal price = rs.getBigDecimal("price");
                String pimg = rs.getString("pimg");
                String pdesc = rs.getString("pdesc");
                provList.add(new Product(pid, pname, price, pimg, pdesc));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBHelper.closeConn(connection, null, rs, ps);
        }
        return provList;
    }

    @Override
    public Integer selAllProdCount(Product queryProd) {
        connection = DBHelper.getConn();
        sql = "SELECT COUNT(0) AS totalNum FROM product ";
        Integer totalNum = null;
        List paramList = new ArrayList(10);

        // 模糊查询
        Boolean isWhere = true;
        // 根据 id 查询
        if (queryProd.getPid() != null && !"".equals(queryProd.getPid())) {
            if (isWhere) {
                sql += " WHERE ";
                isWhere = false;
            } else {
                sql += " AND ";
            }
            sql += " pid=?";
            paramList.add(queryProd.getPid());
        }

        // 根据 name 模糊查询
        if (queryProd.getPname() != null && !"".equals(queryProd.getPname())) {
            if (isWhere) {
                sql += " WHERE ";
                isWhere = false;
            } else {
                sql += " AND ";
            }
            sql += " pname LIKE ? ";
            paramList.add("%" + queryProd.getPname() + "%");
        }

        System.out.println("执行的sql语句 " + sql);

        try {
            ps = connection.prepareStatement(sql);
            // 占位符赋值
            for (int i = 0; i < paramList.size(); i++) {
                ps.setObject(i + 1, paramList.get(i));
            }
            rs = ps.executeQuery();
            while (rs.next()) {
                totalNum = rs.getInt("totalNum");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBHelper.closeConn(connection, null, rs, ps);
        }
        return totalNum;
    }

    @Override
    public Integer addProd(Product addProd) {
        connection = DBHelper.getConn();
        sql = "INSERT INTO product(pid,pname,price,pimg,pdesc) VALUES(?,?,?,?,?)";
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, addProd.getPid());
            ps.setString(2, addProd.getPname());
            ps.setBigDecimal(3, addProd.getPrice());
            ps.setString(4, addProd.getPimg());
            ps.setString(5, addProd.getPdesc());
            System.out.println("sql " + sql);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBHelper.closeConn(connection, null, null, ps);
        }
        return 0;
    }

    @Override
    public Integer updateProd(Product updaProd) {
        connection = DBHelper.getConn();
//        pname、price、pimg、pdesc
        sql = "UPDATE product set pname=?,price=?,pimg=?,pdesc=? where pid=?";
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, updaProd.getPname());
            ps.setBigDecimal(2, updaProd.getPrice());
            ps.setString(3, updaProd.getPimg());
            ps.setString(4, updaProd.getPdesc());
            ps.setString(5, updaProd.getPid());
            System.out.println("sql " + sql);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBHelper.closeConn(connection, null, null, ps);
        }
        return 0;
    }

    @Override
    public Integer delProdById(String pid) {
        connection = DBHelper.getConn();
        sql = "DELETE FROM product where pid=?";
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, pid);
            System.out.println("sql " + sql);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBHelper.closeConn(connection, null, null, ps);
        }
        return 0;
    }
}
