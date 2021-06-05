package com.finance.service.impl;

import com.finance.cons.PageInfo;
import com.finance.dao.ProdBasicDao;
import com.finance.dao.ProdRecommendDao;
import com.finance.dao.impl.ProdBasicDaoImpl;
import com.finance.dao.impl.ProdRecommendDaoImpl;
import com.finance.entity.ProdBasic;
import com.finance.entity.ProdRecommend;
import com.finance.service.ProdRecommendService;

import java.util.List;
import java.util.Map;

/**
 * ClassName: ProdRecommendServiceImpl
 * Author: ZhangCi
 *
 * @description: 业务逻辑层实现类-产品推荐
 * @date: 2021/6/1 21:14
 * @version: 0.1
 * @since: 1.8
 */
public class ProdRecommendServiceImpl implements ProdRecommendService {
    private ProdRecommendDao prDao = new ProdRecommendDaoImpl();
    private ProdBasicDao pbDao = new ProdBasicDaoImpl();

    @Override
    public Integer getTotal(String seriesName) {
        return prDao.getTotal(seriesName);
    }

    @Override
    public List<ProdRecommend> getMsgByParams(String seriesName, PageInfo pInfo) {
        return prDao.getMsgByParams(seriesName, pInfo);
    }

    @Override
    public Integer addPRMsg(ProdRecommend prodRecommend) {
        /**业务需求:
         *  基金产品编号使用 17 开头
         *  保险产品编号使用 19 开头
         *  证券产品编号使用 21 开头
         * 并且编号实现递增
         */
        String psName = prodRecommend.getProdName();
        // 怎么根据产品名称获取对应的产品系列
        ProdBasic prodByProdName = pbDao.getProdByProdName(psName);
        String prodCategory = prodByProdName.getProdCategory();
        // 需要执行两次有时会报出异常，应该是上次执行还未完毕，下次执行却开始了，造成信息未能及时放入集合中
        List<ProdRecommend> msgByPage = prDao.getMsgByParams(prodCategory, new PageInfo());
        List<ProdRecommend> msgByParams = prDao.getMsgByParams("", new PageInfo());
        System.out.println("msgByParams " + msgByParams);
        // 3 获得该系列最后一条记录的产品编号，并且执行编号自增 1
        Integer psId = msgByPage.get(msgByPage.size() - 1).getId() + 1;
        Integer rank = msgByParams.get(msgByParams.size() - 1).getRank() + 1;
        System.out.println("rank " + rank);


        // 装入编号、类别、排行
        prodRecommend.setId(psId);
        prodRecommend.setProdCategory(prodCategory);
        prodRecommend.setRank(rank);
        return prDao.addPRMsg(prodRecommend);
    }

    @Override
    public Integer modifyPRMsg(ProdRecommend prodRecommend) {
        return prDao.modifyMsg(prodRecommend);
    }

    @Override
    public List<String> getByProdName() {
        return pbDao.getProdName();
    }

    @Override
    public List<Map<String, Object>> relatedMsg(String pCategory) {
        return prDao.relatedMsg(pCategory);
    }

    @Override
    public Integer modifyRelated(String pidStr, Integer pId) {
        // 对pidStr进行处理 0=170004&1=170005

        return prDao.modifyRelated(pidStr, pId);
    }
}
