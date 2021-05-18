import com.zhangci.consts.MarketConstants;
import com.zhangci.dao.GoodsDao;
import com.zhangci.dao.impl.GoodsDaoImpl;
import com.zhangci.dao.impl.OrderDaoImpl;
import com.zhangci.entity.Goods;
import com.zhangci.entity.OrderMsg;
import com.zhangci.service.impl.GoodsServiceImpl;
import com.zhangci.util.StringUtil;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * ClassName: GoodsTerst
 * <p>
 * Author: ZhangCi
 * Description:
 * Date: 2021/4/13 22:00
 * Version: 0.1
 * Since: JDK 1.8
 */
public class GoodsTest {




    //Dao层测试orderMsg表新插入记录的id
    @Test
    public void getOrderMsgIncrementId(){

        new OrderDaoImpl().addOrderMsg(new OrderMsg(1002,200,1));
    }

    //service测试单值查询
    @Test
    public void serviceSelectId(){
        new GoodsServiceImpl().selectGoodsById();
    }

    //测试密码
    @Test
    public void encodePass(){
        System.out.println(StringUtil.showPass("12"));
    }

    //测试头像文件是否存在
    @Test
    public void isFile(){
        System.out.println(StringUtil.dealHeadImg("D:\\workspace\\one\\supermarket\\files\\imgs\\2045431.jpg"));
    }

    //测试手机号截取操作
    @Test
    public void subString(){
        System.out.println(StringUtil.dealPhoneNum("13526373149"));
    }

    //测试根据拼接的sql语句进行修改商品信息
    @Test
    public void updateGoods(){
        GoodsDao goodsDao = new GoodsDaoImpl();
        Map<String,Object> goodMap = new HashMap<>(16);
        goodMap.put("goods_name","tom");
        goodMap.put("goods_type","人");
        System.out.println(goodsDao.updateGoods(goodMap,10));
    }


    //测试根据商品ID删除商品
    @Test
    public void deleteGoodsById(){
        GoodsDao goodsDao = new GoodsDaoImpl();
        goodsDao.deleteGoodsById(2);
    }


    @Test
    public void selectGoods() {
        GoodsDao goodsDao = new GoodsDaoImpl();
        System.out.println(goodsDao.selectAllGoods().size());
    }

    //测试LocalDateTime转换String
    @Test
    public void forString() {
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(MarketConstants.TIME_FORMAT_PATTERN);
        System.out.println(date.format(dtf));
    }

    //String转LocalDate和LocalDateTime
    @Test
    public void toLocalDateTime() {
        String str = "2017-11-21 14:41:06:612";
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime time = LocalDateTime.parse(str, fmt);

        System.out.println("time:" + time);
    }


    //商品信息的检测
    @Test
    public void goodsMsg() {
        Scanner input = new Scanner(System.in);
        System.out.println("请输入商品名称：");
        System.out.println("商品信息录入格式: 商品名称,商品类型,新增数量,单价,状态(1-正常，2-下架，3-删除),折扣");
        String line = input.nextLine();
        String[] split = line.split(",");

        //苹果,水果,30,5,1,10
        Map<Integer, Object> goodMap = new HashMap<>(16);

//        goodMap.put(1, split[0]);
//        goodMap.put(2, split[1]);
//        goodMap.put(3, split[2]);
//        goodMap.put(4, split[3]);
//        goodMap.put(5, split[4]);
//        goodMap.put(6, split[5]);

        for (int index = 0; index < split.length; index++) {
            goodMap.put(index + 1, split[index]);
        }

        System.out.println(goodMap);


        for (String s : split) {
            System.out.println(s);
        }
        System.out.println(split.length);
//        System.out.println(split.toString());
    }

    @Test
    public void goods() {
        GoodsDao goodsDao = new GoodsDaoImpl();
        Goods goods = new Goods();
        goods.setGoodsName("苹果");
        goods.setGoodsType("水果");
        goods.setGoodsNum(10);
        System.out.println(goodsDao.addGoods(goods));
    }
}
