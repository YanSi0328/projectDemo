package com.zhangci.util;

import java.io.*;

/**
 * ClassName: SubsprintUtil
 * <p>
 * Author: ZhangCi
 * Description: 截取字符串操作
 * Date: 2021/4/15 9:07
 * Version: 0.1
 * Since: JDK 1.8
 */
public class StringUtil {
    public StringUtil() {
    }



    public static String storePhone(String phone){
        if (phone.length() != 11) {
            System.out.println("电话号码必须为11位");
            return null;
        }
        return phone;
    }

    /**
     * 将手机号的中间五位替换成 *****
     *
     * @param phone 待处理的手机号
     * @return 处理后的手机号
     */
    public static String dealPhoneNum(String phone) {


        //获得前三位
        String begin = phone.substring(0, 3);
        String end = phone.substring(phone.length() - 3);

        return (begin + "*****" + end);
    }

    /**
     * 判断该头像地址是否存在
     *
     * @param headImgPath 头像地址
     * @return 是否存在
     */
    public static boolean dealHeadImg(String headImgPath) {
        //是否是文件
        return new File(headImgPath).isFile();
    }

    /**
     * 将密码显示为十四位的 **********
     *
     * @param pass 用户密码
     * @return 处理后的密码
     */
    public static String showPass(String pass) {
        return pass.replace(pass, "**********");
    }
}
