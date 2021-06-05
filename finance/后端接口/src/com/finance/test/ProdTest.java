package com.finance.test;

import com.finance.cons.PageInfo;
import com.finance.dao.ProdBasicDao;
import com.finance.dao.ProdSeriesDao;
import com.finance.dao.impl.ProdBasicDaoImpl;
import com.finance.dao.impl.ProdSeriesDaoImpl;
import com.finance.entity.ProdSeries;
import com.finance.service.ProdSeriesService;
import com.finance.service.impl.ProdSeriesServiceImpl;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: ProdTest
 * Author: ZhangCi
 *
 * @description: 产品相关的测试信息
 * @date: 2021/5/27 18:11
 * @version: 0.1
 * @since: 1.8
 */
public class ProdTest {
    private ProdSeriesDao psDao = new ProdSeriesDaoImpl();
    private ProdSeriesService psService = new ProdSeriesServiceImpl();
    private ProdBasicDao pbDao = new ProdBasicDaoImpl();


    @Test
    public void testSubStr() {
        String str = "0=170004&1=170005";
        System.out.println(str);
        String[] split = str.split("&");
        StringBuilder builder = new StringBuilder();
        for (String aSplit : split) {
            builder.append(aSplit.split("=")[1]);
            builder.append(",");
        }
        String substring = builder.substring(0, builder.lastIndexOf(","));
        System.out.println(substring);
    }


    @Test
    public void testByProdName() {
        System.out.println(pbDao.getProdByProdName("Test1"));
    }

    @Test
    public void testGetProdName() {
        System.out.println(pbDao.getProdName());
    }

    @Test
    public void testPBTotal() {
        Map<String, Object> params = new HashMap<>(16);
        System.out.println(pbDao.getTotal(params));
    }

    @Test
    public void testAddProdSeries() {
        System.out.println(psService.addProdSeries(new ProdSeries("香港资管", "保险", "test001", "zc0002")));
    }

    @Test
    public void testGetMsgByPage() {
        List<ProdSeries> byPage = psDao.getMsgByPage("保险", new PageInfo());
        Integer psId = byPage.get(byPage.size() - 1).getPsId();

        System.out.println(psId);
    }

//    @Test
//    public void testAddProd() {
//        Integer num = productService.addProd(new ProdBasic("1", "1", "1", "1", 1.0, "1", "2021-05-28", "1", 1.0, 1.0, 1.0, "1", "1", 1.0, 1.0, 1, "1"));
//        System.out.println(num);
//    }
}
