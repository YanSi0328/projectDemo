package com.finance.test;

import com.finance.dao.MenuDao;
import com.finance.dao.impl.MenuDaoImpl;
import com.finance.entity.Menu;
import com.finance.service.MenuService;
import com.finance.service.impl.MenuServiceImpl;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: MenuTest
 * Author: ZhangCi
 *
 * @description: 菜单相关操作的测试
 * @date: 2021/5/22 20:41
 * @version: 0.1
 * @since: 1.8
 */
public class MenuTest {

    private MenuService menuService = new MenuServiceImpl();
    private MenuDao menuDao = new MenuDaoImpl();


    @Test
    public void testAddMenuMsg() {
        System.out.println(menuService.addMenuMsg(new Menu(111111, "11111", 11111, "11111", "11111", "111111")));
    }

    @Test
    public void testDelMenuById() {
        System.out.println(menuService.delMenuById(111111));
    }

    @Test
    public void testGetMenuTotal() {
        Map<String, Object> params = new HashMap<>(16);
        params.put("name", "管理");
        params.put("pid", 0);
        System.out.println(menuService.getMenuTotal(params));
    }

    @Test
    public void testGetMenuByFactor() {
        Map<String, Object> params = new HashMap<>(16);
//        params.put("name", "管理");
//        params.put("pname", "系统管理");
        System.out.println(menuService.getMenuByPage(1, 20, params));
    }

    @Test
    public void testParentAndChildMenu() {
        System.out.println("父菜单：" + menuDao.getParentMenu());
        System.out.println("------------------------------------------");
        System.out.println("子菜单：" + menuDao.getChildMenu());
    }
}
