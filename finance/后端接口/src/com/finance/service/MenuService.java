package com.finance.service;

import com.finance.entity.Menu;

import java.util.List;
import java.util.Map;

/**
 * ClassName: MenuService
 * Author: ZhangCi
 *
 * @description:
 * @date: 2021/5/22 19:54
 * @version: 0.1
 * @since: 1.8
 */
public interface MenuService {

    /**
     * 条件查询
     *
     * @param params 待查询的字段集合
     * @return 查询到的菜单集合
     */
    List<Menu> getMenuByPage(Integer page, Integer pageSize, Map<String, Object> params);

    /**
     * 获得所有的菜单信息
     *
     * @return
     */
    List<Menu> getAllMenu();

    /**
     * 查询所有的一级菜单信息
     *
     * @return 查询到的菜单信息
     */
    List<Menu> getParentMenu();

    /**
     * 根据id修改菜单信息
     *
     * @param menu 待修改的菜单信息
     * @return 受影响的数据库记录数
     */
    Integer modifyMenu(Menu menu);


    /**
     * 根据特定菜单对象查询菜单记录数
     *
     * @param params 菜单查询参数
     * @return 记录数
     */
    Integer getMenuTotal(Map<String, Object> params);

    /**
     * 新增菜单信息
     *
     * @param menu 菜单对象
     * @return 受影响的记录条数
     */
    Integer addMenuMsg(Menu menu);

    /**
     * 根据 id 删除菜单信息
     *
     * @param id 菜单id
     * @return 受影响的记录条数
     */
    Integer delMenuById(Integer id);

    //进行人员权限的左侧菜单展示
    List<Menu> getMenuByLevel(Integer level, String role);

}
