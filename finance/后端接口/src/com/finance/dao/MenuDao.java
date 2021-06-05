package com.finance.dao;

import com.finance.entity.Menu;

import java.util.List;
import java.util.Map;

/**
 * ClassName: MenuDao
 * Author: ZhangCi
 *
 * @description: Menu 数据持久层
 * @date: 2021/5/22 19:52
 * @version: 0.1
 * @since: 1.8
 */
public interface MenuDao {


    /**
     * 多条件查询
     *
     * @param params 待查询的字段集合
     * @return 查询到的菜单集合
     */
    List<Menu> getMenuByPage(Integer page, Integer pageSize, Map<String, Object> params);

    /**
     * 查询所有的一级菜单信息
     *
     * @return 查询到的菜单信息
     */
    List<Menu> getParentMenu();

    /**
     * 查询所有二级菜单信息
     *
     * @return 对应的菜单信息
     */
    List<Menu> getChildMenu();


    /**
     * 修改菜单信息
     *
     * @param menu 待修改的菜单信息
     * @return 受影响的数据库记录数
     */
    Integer modifyMenu(Menu menu);

    /**
     * 查询总的菜单记录数
     *
     * @return 查询到记录结果
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

    //进行菜单分级//当level=0时表示要查询的是一级菜单，
    // roles表示的是该菜单自己的mid，但两者同时符合情况则查询出结果，否则查询出为空
    List<Menu> getMenuByLevel(Integer level, String role) throws Exception;

}
