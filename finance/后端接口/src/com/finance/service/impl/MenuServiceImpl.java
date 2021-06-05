package com.finance.service.impl;

import com.finance.dao.MenuDao;
import com.finance.dao.impl.MenuDaoImpl;
import com.finance.entity.Menu;
import com.finance.service.MenuService;

import java.util.List;
import java.util.Map;

/**
 * ClassName: MenuServiceImpl
 * Author: ZhangCi
 *
 * @description:
 * @date: 2021/5/22 19:54
 * @version: 0.1
 * @since: 1.8
 */
public class MenuServiceImpl implements MenuService {
    private MenuDao menuDao = new MenuDaoImpl();


    @Override
    public List<Menu> getMenuByPage(Integer page, Integer pageSize, Map<String, Object> params) {
        return menuDao.getMenuByPage(page, pageSize, params);
    }

    @Override
    public List<Menu> getAllMenu() {
        // 1 查询出来父菜单
        List<Menu> parentMenu = menuDao.getParentMenu();
        // 2 查询出来子菜单
        List<Menu> childMenu = menuDao.getChildMenu();
        // 3 通过集合的双重遍历得到含有二级菜单封装后的菜单信息
        // 比较规则：二级菜单的pid与一级菜单的id进行比较，相同则将二级菜单放入一级菜单的子菜单中
        for (Menu pm : parentMenu) {
            for (Menu cm : childMenu) {
                if (cm.getPId().equals(pm.getId())) {
                    // 二者相同，将子菜单信息放入父菜单的集合中
                    pm.getSubmenu().add(cm);
                }
            }
        }
        return parentMenu;
    }

    @Override
    public List<Menu> getParentMenu() {
        return menuDao.getParentMenu();
    }


    @Override
    public Integer modifyMenu(Menu menu) {
        return menuDao.modifyMenu(menu);
    }


    @Override
    public Integer getMenuTotal(Map<String, Object> params) {
        return menuDao.getMenuTotal(params);
    }

    @Override
    public Integer addMenuMsg(Menu menu) {
        return menuDao.addMenuMsg(menu);
    }

    @Override
    public Integer delMenuById(Integer id) {
        return menuDao.delMenuById(id);
    }

    @Override
    public List<Menu> getMenuByLevel(Integer level, String role) {
        MenuDao menuDao = new MenuDaoImpl();
        List<Menu> menuByLevel = null;
        try {
            menuByLevel = menuDao.getMenuByLevel(level, role);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return menuByLevel;
    }
}

