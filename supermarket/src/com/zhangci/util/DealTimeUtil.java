package com.zhangci.util;


import com.zhangci.consts.MarketConstants;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * ClassName: DealTimeUtil
 * <p>
 * Author: ZhangCi
 * Description: 时间处理工具类
 * Date: 2021/4/13 22:36
 * Version: 0.1
 * Since: JDK 1.8
 */
public class DealTimeUtil {

    //将long类型的数据先转换为日期Date
    public static String longToTime() {
        long millis = System.currentTimeMillis();

        //格式化时间
        SimpleDateFormat dateFormat = new SimpleDateFormat(MarketConstants.TIME_FORMAT_PATTERN);
        return dateFormat.format(new Date(millis));
    }

    //转换为String
    public static String forString() {
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(MarketConstants.TIME_FORMAT_PATTERN);
        return date.format(dtf);
    }

    //转换为LocalDateTime
    public static String toLocalDateTime() {

//        String str = "2017-11-21 14:41:06:612";
//        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        LocalDateTime time = LocalDateTime.parse(str, fmt);
//
//        System.out.println("time:" + time);

        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(MarketConstants.TIME_FORMAT_PATTERN);
        return date.format(dtf);
    }
}
