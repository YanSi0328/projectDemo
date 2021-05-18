package com.zhangci.util;

import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

/**
 * ClassName: PropsUtil
 * <p>
 * Author: ZhangCi
 * Description: 读取配置文件(PropsUtil)信息
 * Date: 2021/4/11 17:44
 * Version: 0.1
 * Since: JDK 1.8
 */
public class PropsUtil {

    //变为保护类型
    protected PropsUtil(){
    }

    private static Properties properties;

    // 一般不停，只需配置文件加载一次
    static {
        properties = new Properties();
        //加载配置文件
        try {
            properties.load(PropsUtil.class.getClassLoader().getResourceAsStream("jdbc.properties"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据key获得value
     * @param key 键值
     * @return 返回键对应的值
     */
    public static String getValue(String key) {
        Objects.requireNonNull(key);
        //为防止后期莫=莫名停止服务，传递空串进行管理
        return properties.getProperty(key,"");
    }

}
