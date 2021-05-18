package com.zhangci.util;

import com.zhangci.consts.MarketConstants;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

/**
 * ClassName: MD5
 * <p>
 * Author: ZhangCi
 * Description: Md5加密
 * Date: 2021/4/12 20:53
 * Version: 0.1
 * Since: JDK 1.8
 */
public class MD5Util {
    public MD5Util() {
    }


    /**
     * 加密方法
     *
     * @param userPass 用户密码
     * @return 返回加密后的数据
     */
    public static String md5EncodePassword(String userPass) {
        if ((userPass.length() < 10) || (userPass.length() > 20)) {
            System.out.println("密码长度必须在10至20位");
            return null;
        }
        Objects.requireNonNull(userPass);
        try {
            //加密对象
            MessageDigest md5 = MessageDigest.getInstance("MD5");

            //开始加密，先加盐，
            userPass = userPass + MarketConstants.MD5SALT;
            md5.update(userPass.getBytes(Charset.forName("UTF-8")));

            //再加密，加密后返回的是一个数组
            byte[] digest = md5.digest();
//            System.out.println(Arrays.toString(digest));

            //将数组转换为字符串并将全部字符串转换为大写，方便后期处理
            BigInteger bigInteger = new BigInteger(1, digest);//1 正整数  -1 负整数
            return bigInteger.toString(16).toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密
     *
     * @param userPass 加密后的密码
     * @return 解密后的密码
     */
    public static String md5DecodePassword(String userPass) {
        char[] chars = userPass.toCharArray();
        for (int index = 0; index < chars.length; index++) {
            chars[index] = (char) (chars[index] ^ 't');
        }
        System.out.println(chars);
        String s = new String(chars);
        return s;
    }
}
