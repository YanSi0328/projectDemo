package com.zhangci02.util;

import java.util.Scanner;

/**
 * ClassName: InputUtil
 * <p>
 * Author: ZhangCi
 * Description: ����У����
 * Date: 2021/4/1 22:50
 * Version: 0.1
 * Since: JDK 1.8
 */
public class InputUtil {
    private Scanner input;

    public InputUtil() {
    }

    //��ȡ����
    public int nextInt() {
        int i = input.nextInt();
        return i;
    }

    //��ȡ�ַ���
    public String nextLine() {
        input.nextLine();
        return input.nextLine();
    }

}
