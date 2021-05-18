package com.zhangci02.util;

import java.util.Scanner;

/**
 * ClassName: InputUtil
 * <p>
 * Author: ZhangCi
 * Description: 输入校验类
 * Date: 2021/4/1 22:50
 * Version: 0.1
 * Since: JDK 1.8
 */
public class InputUtil {
    private Scanner input;

    public InputUtil() {
    }

    //获取整数
    public int nextInt() {
        int i = input.nextInt();
        return i;
    }

    //获取字符串
    public String nextLine() {
        input.nextLine();
        return input.nextLine();
    }

}
