package com.finance.service.impl;

import com.finance.dao.ProdEquityDao;
import com.finance.dao.impl.ProdEquityDaoImpl;
import com.finance.entity.ProdEquity;
import com.finance.service.ProdEquityService;

/**
 * ClassName: ProdEquityServiceImpl
 * Author: ZhangCi
 *
 * @description: 业务逻辑层实现类-产品净值
 * @date: 2021/6/1 20:00
 * @version: 0.1
 * @since: 1.8
 */
public class ProdEquityServiceImpl implements ProdEquityService {
    private ProdEquityDao peDao = new ProdEquityDaoImpl();

    @Override
    public Integer dealEquity(ProdEquity prodEquity) {
        Integer result = null;
        /**业务需求分析:
         *  该产品已经有了净值信息 -> 执行新增操作
         *  该产品还没有净值信息 -> 执行修改操作
         *  问题：怎么知道有没有净值信息 -> 在净值表中根据产品名称进行查询
         *
         */
        // 1 根据产品名称在净值表进行查询
        String prodName = prodEquity.getProdName();
        Integer record = peDao.getProdName(prodName);
        System.out.println("当前数据库中的记录数 " + record);
        // 2 根据是否有记录来判断是执行新增操作还是修改操作
        if (record == 0) {
            // 此时说明产品净值表没有相关的数据，执行新增产品净值操作
            result = peDao.addPEMsg(prodEquity);
        } else {
            // 执行修改操作
            result = peDao.modifyByPName(prodEquity);
        }
        return result;
    }
}
