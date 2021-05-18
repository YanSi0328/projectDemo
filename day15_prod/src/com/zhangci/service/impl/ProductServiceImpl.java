package com.zhangci.service.impl;

import com.zhangci.dao.ProductDao;
import com.zhangci.dao.impl.ProductDaoImpl;
import com.zhangci.etity.Product;
import com.zhangci.service.ProductService;

import java.util.List;

/**
 * ClassName: ProductServiceImpl
 * Author: ZhangCi
 *
 * @description:
 * @date: 2021/5/14 10:05
 * @version: 0.1
 * @since: 1.8
 */
public class ProductServiceImpl implements ProductService {
    private ProductDao productDao = new ProductDaoImpl();

    @Override
    public Integer updatProd(Product updaProd) {
        return productDao.updateProd(updaProd);
    }

    @Override
    public Integer delProdById(String pid) {
        return productDao.delProdById(pid);
    }

    @Override
    public Product selProductById(String pordId) {
        return productDao.selProductById(pordId);
    }

    @Override
    public Integer selAllProdCount(Integer pageSize, Product queryProd) {
        // 业务逻辑处理
        Integer totalPage = 0;
        Integer allNum = productDao.selAllProdCount(queryProd);
        // 总页数根据总记录数做除法运算，若有余数，则需要多一也显示不是满记录的页面
        totalPage = allNum % pageSize > 0 ? (allNum / pageSize) + 1 : (allNum / pageSize);
        return totalPage;
    }

    @Override
    public List<Product> selAllProdMsg(Integer page, Integer pageSize, Product queryProd) {
        return productDao.selAllProdMsg(page, pageSize, queryProd);
    }

    @Override
    public Integer addProd(Product addProd) {
        return productDao.addProd(addProd);
    }
}
