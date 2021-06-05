package com.finance.entity;

import lombok.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: MenuSql
 * Author: ZhangCi
 *
 * @description: 菜单实体类
 * @date: 2021/5/22 17:58
 * @version: 0.1
 * @since: 1.8
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Menu {
    private Integer id;           // 菜单编号
    private String name;         // 菜单名称
    private Integer pId;         // 父菜单id
    private String pName;       // 父菜单名称
    private String address;    // 访问地址
    private String icon;       // 菜单图标
    private List<Menu> submenu = new ArrayList<>();

    public Menu(Integer id, String name, Integer pId, String pName, String address, String icon) {
        this.id = id;
        this.name = name;
        this.pId = pId;
        this.pName = pName;
        this.address = address;
        this.icon = icon;
    }

    // 结果集
    public Menu(ResultSet rs) throws SQLException {
        this(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getInt("pid"),
                rs.getString("pname"),
                rs.getString("address"),
                rs.getString("icon")
        );
    }
}