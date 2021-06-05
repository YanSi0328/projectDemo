package com.finance.service.impl;

import com.finance.cons.PageInfo;
import com.finance.dao.ProdSeriesDao;
import com.finance.dao.impl.ProdSeriesDaoImpl;
import com.finance.entity.ProdSeries;
import com.finance.service.ProdSeriesService;

import java.util.List;

/**
 * ClassName: ProdSeriesServiceImpl
 * Author: ZhangCi
 *
 * @description: 产品系列管理实现类
 * @date: 2021/5/29 10:33
 * @version: 0.1
 * @since: 1.8
 */
public class ProdSeriesServiceImpl implements ProdSeriesService {
    private ProdSeriesDao psDao = new ProdSeriesDaoImpl();

    @Override
    public Integer getTotalRecord(String serName) {
        return psDao.getTotalRecord(serName);
    }

    @Override
    public List<ProdSeries> getMsgByPage(String serName, PageInfo pInfo) {
        return psDao.getMsgByPage(serName, pInfo);
    }

    @Override
    public Integer addProdSeries(ProdSeries ps) {
        /**业务需求:
         *  基金产品编号使用 17 开头
         *  保险产品编号使用 19 开头
         *  证券产品编号使用 21 开头
         * 并且编号实现递增
         */
        // 1 初始参数
        String psName = ps.getPsName();
        // 2 根据指定系列进行查询
        List<ProdSeries> msgByPage = psDao.getMsgByPage(psName, new PageInfo());
        // 3 获得该系列最后一条记录的产品编号，并且执行编号自增 1
        Integer psId = msgByPage.get(msgByPage.size() - 1).getPsId() + 1;
        System.out.println("psId " + psId);

        // 4 根据系列名称放入产品系列id
        ps.setPsId(psId);
        ps.setPCname(ps.getPCname() + "【" + psName + "】");

        System.out.println("根据业务需求更改后的" + ps);
        return psDao.addProdSeries(ps);
    }

    @Override
    public ProdSeries getMsgById(Integer psId) {
        return psDao.getMsgById(psId);
    }

    @Override
    public Integer updateById(ProdSeries pSeries) {
        return psDao.updateById(pSeries);
    }

    @Override
    public Integer addRemitMsg(ProdSeries pSeries) {
        return psDao.addRemitMsg(pSeries);
    }
}
