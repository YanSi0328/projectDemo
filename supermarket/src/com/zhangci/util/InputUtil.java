package com.zhangci.util;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ClassName: InputUtil
 * <p>
 * Author: ZhangCi
 * Description: 对输入进行管理
 * Date: 2021/4/12 22:48
 * Version: 0.1
 * Since: JDK 1.8
 */
public class InputUtil {
    //验证输入的是否是数字
    private static final String checkNumPattern = "[1-9]\\d*";
    //验证输入的是否是字符串
    private static final String checkStringPattern = "[A-Za-z0-9_\\-\\u4e00-\\u9fa5]+";

    private static Scanner input;

    public InputUtil() {
        input = new Scanner(System.in);
    }

    //对输入的整型进行控制
    public int nextInt() {
        //正则匹配处理
        String line = input.nextLine();
        Pattern pattern = Pattern.compile(checkNumPattern);
        Matcher match = pattern.matcher(line);
        //对转换结果进行判断
        if (!match.matches()) {
            return 0;
        }
        return Integer.parseInt(line);
    }

//    //对输入的字符串进行控制
//    public String nextLine() {
//        //正则匹配处理
//        String str = input.nextLine();
//        Pattern pattern = Pattern.compile(checkStringPattern);
//        Matcher match = pattern.matcher(str);
//        //判断转换结果
//        if (!match.matches()) {
//            //false:不匹配返回空值
//            return null;
//        }
//        return str;
//    }

    public String nextLine(int next) {
        input.nextLine();
        return input.nextLine();
    }


    public String nextLine() {
        return input.nextLine();
    }
}
