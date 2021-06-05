package com.finance.dao.impl;

import com.finance.dao.MenuDao;
import com.finance.entity.Menu;
import com.finance.sql.MenuSql;
import com.finance.util.DBHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ClassName: MenuDaoImpl
 * Author: ZhangCi
 *
 * @description:
 * @date: 2021/5/22 19:52
 * @version: 0.1
 * @since: 1.8
 */
public class MenuDaoImpl implements MenuDao {
    private PreparedStatement ps;
    private Connection conn;
    private ResultSet rs;
    private String sql;

    @Override
    public List<Menu> getMenuByLevel(Integer level, String role) throws Exception {
        List<Menu> menuList = new ArrayList<>(10);
        conn = DBHelper.getConn();
        sql = "select mu.id,mu.name,mu.pid,mu.pname,mu.address,mu.icon from menu mu where ";
        if (level == 0) {
            sql += "mu.pid=0 ";
        } else {
            sql += "mu.pid!=0 ";
        }

        if (role != null && !"".equals(role)) {
            sql += " and  mu.id in (" + role + ") ";
        }
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            // 结果集的封装
            while (rs.next()) {
                menuList.add(new Menu(rs));
            }
            return menuList;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBHelper.CloseConn(conn, ps, rs);
        }
        return null;
    }

    @Override
    public List<Menu> getMenuByPage(Integer page, Integer pageSize, Map<String, Object> params) {
        List<Menu> menuList = new ArrayList<>(10);
        conn = DBHelper.getConn();
        // sql 语句的拼接：SELECT m.id,m.name,m.pid,m.pname,m.address,m.icon FROM menu m WHERE m.pid=0 AND m.`name`='系统管理'
        StringBuilder builder = new StringBuilder("SELECT id,name,pid,pname,address,icon FROM menu");
        // 需要拼接的条件：查询条件集合长度不为空
        if (params.size() != 0) {
            builder.append(" WHERE ");
            // 怎么拼接：根据参数列表中的键值对
            params.forEach((k, v) -> {
                if (k.contains("id")) {
                    // 一种情况，数据库中对于编号存储的都是 Integer 类型此处需要进行处理
                    System.out.println("you id");
                    builder.append(k);
                    builder.append(" LIKE " + v);
                    builder.append(" AND ");
                } else {
                    builder.append(k + " LIKE '" + "%" + v + "%");
                    builder.append("' AND ");
                }
            });
            // 处理最后的 AND
            sql = builder.substring(0, builder.lastIndexOf("AND") - 1);
        } else {
            sql = builder.toString();
        }
        // 分页条件
        sql += " limit " + (page - 1) * pageSize + "," + pageSize;


        System.out.println("sql= " + sql);
        //  预编译
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            // 结果集的封装
            while (rs.next()) {
                menuList.add(new Menu(rs));
            }
//            System.out.println(menuList.size());
            return menuList;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBHelper.CloseConn(conn, ps, rs);
        }
        return null;
    }

    @Override
    public List<Menu> getParentMenu() {
        List<Menu> parMenuList = new ArrayList<>(10);
        // 获得数据源
        conn = DBHelper.getConn();
        // 准备 sql 语句
        sql = MenuSql.SEL_BY_PID;
        System.out.println("getParentMenu" + sql);
        // 预编译sql
        try {
            ps = conn.prepareStatement(sql);
            // 查询到的结果集
            rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int pid = rs.getInt("pid");
                String pname = rs.getString("pname");
                String address = rs.getString("address");
                String icon = rs.getString("icon");
                // 将查询到的信息放入菜单集合
                parMenuList.add(new Menu(id, name, pid, pname, address, icon));
            }
//            System.out.println("menuList " + menuList);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBHelper.CloseConn(conn, ps, rs);
        }
        return parMenuList;
    }

    @Override
    public List<Menu> getChildMenu() {
        List<Menu> childMenuList = new ArrayList<>(10);
        conn = DBHelper.getConn();
        sql = MenuSql.SEL_BY_ID;
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                // 将查询到的信息放入菜单集合
                childMenuList.add(new Menu(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBHelper.CloseConn(conn, ps, rs);
        }
        return childMenuList;
    }

    @Override
    public Integer modifyMenu(Menu menu) {
        conn = DBHelper.getConn();
        sql = MenuSql.UPDATE_BY_ID;
        try {
            // name=?,pid=?,pname=?,address=?,icon=? WHERE id=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, menu.getName());
            ps.setInt(2, menu.getPId());
            ps.setString(3, menu.getPName());
            ps.setString(4, menu.getAddress());
            ps.setString(5, menu.getIcon());
            ps.setInt(6, menu.getId());
            System.out.println("sql = " + sql);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBHelper.CloseConn(conn, ps, null);
        }
        return null;
    }


    @Override
    public Integer getMenuTotal(Map<String, Object> params) {
        conn = DBHelper.getConn();
        StringBuilder builder = new StringBuilder(MenuSql.SEL_TOTAL);
        Integer total = null;
        if (params.size() != 0) {
            builder.append(" WHERE ");
            // 怎么拼接：根据参数列表中的键值对
            params.forEach((k, v) -> {
                if (k.contains("id")) {
                    // 一种情况，数据库中对于编号存储的都是 Integer 类型此处需要进行处理
                    System.out.println("you id");
                    builder.append(k);
                    builder.append(" LIKE " + v);
                    builder.append(" AND ");
                } else {
                    builder.append(k + " LIKE '" + "%" + v + "%");
                    builder.append("' AND ");
                }
            });
            // 处理最后的 AND
            sql = builder.substring(0, builder.lastIndexOf("AND") - 1);
        } else {
            sql = builder.toString();
        }

        System.out.println("getMenuTotal sql= " + sql);
        // 预编译sql
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                total = rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBHelper.CloseConn(conn, ps, null);
        }
        return total;
    }

    @Override
    public Integer addMenuMsg(Menu menu) {
        conn = DBHelper.getConn();
        sql = MenuSql.ADD_MSG;
        // 预编译sql
        System.out.println("新增 ");
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, menu.getId());
            ps.setString(2, menu.getName());
            ps.setInt(3, menu.getPId());
            ps.setString(4, menu.getPName());
            ps.setString(5, menu.getAddress());
            ps.setString(6, menu.getIcon());
            // 执行新增，并返回
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBHelper.CloseConn(conn, ps, null);
        }
        return 0;
    }

    @Override
    public Integer delMenuById(Integer id) {
        conn = DBHelper.getConn();
        sql = MenuSql.DEL_BY_ID;
        // 预编译sql
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            // 执行删除，并返回
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBHelper.CloseConn(conn, ps, null);
        }
        return null;
    }
}
