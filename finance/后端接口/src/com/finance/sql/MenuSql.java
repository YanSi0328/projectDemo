package com.finance.sql;

/**
 * ClassName: MenuSql
 * Author: ZhangCi
 *
 * @description: 关于菜单操作的一些sql语句
 * @date: 2021/5/22 19:53
 * @version: 0.1
 * @since: 1.8
 */
public interface MenuSql {
    // 查询所有的子菜单信息
    String SEL_BY_ID = "SELECT m.id,m.name,m.pid,m.pname,m.address,m.icon FROM menu m WHERE m.pid!=0";

    // 根据指定 id 修改菜单信息，用于保存修改后的菜单信息
    String UPDATE_BY_ID = "UPDATE menu SET name=?,pid=?,pname=?,address=?,icon=? WHERE id=?";

    // 根据菜单的 父id 查询所有的信息，因为菜单只有二级故一级菜单为父菜单
    String SEL_BY_PID = "SELECT m.id,m.name,m.pid,m.pname,m.address,m.icon FROM menu m WHERE m.pid=0";

    // 查询总的菜单记录数
    String SEL_TOTAL = "SELECT COUNT(1) total FROM menu";

    // 新增菜单项
    String ADD_MSG = "INSERT INTO menu(id,name,pid,pname,address,icon)VALUES(?,?,?,?,?,?)";

    // 根据id 删除菜单信息
    String DEL_BY_ID = "DELETE FROM menu WHERE id=?";


}
