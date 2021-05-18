package com.zhangci02.util;

import com.zhangci02.consts.BikeConstants;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ClassName: CalculateTime
 * <p>
 * Author: ZhangCi
 * Description: 计算日期类
 * Date: 2021/4/1 22:51
 * Version: 0.1
 * Since: JDK 1.8
 */
public class CalculateTime {

    public int calTime(String oldTime, String newTime) {

        //格式化日期
        DateFormat dateFormat = new SimpleDateFormat(BikeConstants.PATTERN);
        long allTime = 0;
        try {
            Date borrowTime = dateFormat.parse(oldTime);
            Date returnTime = dateFormat.parse(newTime);
            allTime = returnTime.getTime() - borrowTime.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (int) allTime / 3600 / 1000;
    }
}
