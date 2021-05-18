package com.zhangci.test;

import com.zhangci.dao.ProductDao;
import com.zhangci.dao.impl.ProductDaoImpl;

/**
 * ClassName: SqlTest
 * Author: ZhangCi
 *
 * @description: 测试查询结果
 * @date: 2021/5/14 15:06
 * @version: 0.1
 * @since: 1.8
 */
public class SqlTest {
    private ProductDao productDao = new ProductDaoImpl();

//    @Test
//    public void testRecord(){
//        int i = productDao.selAllProdCount();
//        System.out.println(i);
//    }

//    @Test
//    public void testAllProv(){
//        List<Product> productList = productDao.selAllProdMsg();
//        System.out.println(productList);
//    }
}
