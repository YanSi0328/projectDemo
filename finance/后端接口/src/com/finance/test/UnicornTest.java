package com.finance.test;

import com.finance.entity.ClientInfo;
import com.finance.entity.HistoryFinancing;
import com.finance.service.ClientInfoService;
import com.finance.service.EnterpriseInfoService;
import com.finance.service.HistoryFinancingService;
import com.finance.service.impl.ClientInfoServiceImpl;
import com.finance.service.impl.EnterpriseInfoServiceImpl;
import com.finance.service.impl.HistoryFinacingServiceImpl;
import org.junit.Test;

import java.util.List;

/**
 * @author:Wang
 * @className: UnicornTest
 * @description: 独角兽企业接口测试
 * @date: 2021/5/30 15:27
 * @version:0.1
 * @since:1.8
 */
public class UnicornTest {
    @Test
//    客户接口测试
    public void clientInfoTest() {
        ClientInfo clientInfo = new ClientInfo("李白", "");
        ClientInfoService clientDao = new ClientInfoServiceImpl();
        List<ClientInfo> list = clientDao.getClientByPage(1, 3, clientInfo);
        int totalPage = clientDao.getClientTotalPage(clientInfo);
        System.out.println(list);
        System.out.println(totalPage);

    }

    //    独角兽企业接口测试
    @Test
    public void enterprise() {
        EnterpriseInfoService enterpriseInfoDao = new EnterpriseInfoServiceImpl();
//        List<EnterpriseInfo> list = enterpriseInfoDao.getEnterpriseInfoByPage("", 1, 3);
//        Integer totalPage = enterpriseInfoDao.getEnterpriseTotalPage("");
//        EnterpriseInfo es = enterpriseInfoDao.getEnterpriseByParms("ZocDoc", "ZCDC.HMF");
//        TradingDao tradingDao = new TradingDaoImpl();
//        Integer result = tradingDao.addStockTrading(100);
//        List<String> enterpriseType = enterpriseInfoDao.getEnterpriseType();
//        System.out.println(enterpriseType);
//        System.out.println(result);
//        enterprise_info (enterprise_name,enterprise_code,enterprice_logo,enterprice_alogo,enterprice_type,enterprice_founding,enterprice_ceo,enterprice_adress,enterprise_rate,enterprice_desc) \n" +
//                "VALUES (?,?,?,?,?,?,?,?,?,?)
//        Map<String,Object> map = new TreeMap<>();
//
//        map.put("enterprice_founding",new Date());
//        map.put("enterprise_name","小红");
//        map.put("enterprise_code","4436");
//        map.put("enterprise_rate",0.2);
//        map.put("enterprise_itprice",33.5);
//        map.put("enterprice_desc","xxx");
//        map.put("enterprise_id",30);
//        Integer result1 = enterpriseInfoDao.addEnterpriseInfo(map);
//        System.out.println(result1);
//        System.out.println(list);
//        System.out.println(totalPage);
//        System.out.println(result);
//        System.out.println(es);
    }

    //历史融资测试
    @Test
    public void historyFinacing() {
        HistoryFinancingService hf = new HistoryFinacingServiceImpl();
        List<HistoryFinancing> historyFinancing = hf.getHistoryFinancing("Social Finance(SoFi)");
        historyFinancing.forEach(System.out::println);
    }
}
