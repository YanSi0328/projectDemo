package com.zhangci.util;

import com.zhangci.consts.MarketConstants;

import java.io.*;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

/**
 * ClassName: FileUtil
 * <p>
 * Author: ZhangCi
 * Description: 用户头像文件服务
 * Date: 2021/4/19 16:34
 * Version: 0.1
 * Since: JDK 1.8
 */
public class FileUtil {


    private FileUtil() {
    }


    /**
     * 用户头像上传服务
     *
     * @param sourcePath 源文件地址
     * @return 上传到服务器后的地址
     */
    public static String dealFile(String sourcePath) {
        Objects.requireNonNull(sourcePath);

        //转换文件
        File file = new File(sourcePath);
        //当前时间作为文件夹
        String nowTimePath = LocalDate.now().toString();

        //服务器端文件夹是否存在
        File serDir = new File(MarketConstants.PARENT_DIRECTORY, nowTimePath);
        if (!serDir.exists()) {
            //不存在创建文件夹
            serDir.mkdirs();
        }

        //对文件唯一性的处理
        String serFileName = UUID.randomUUID().toString().replaceAll("-", "") + "-" + file.getName();

        // 文件的读写
        try (
                InputStream inputStream = new FileInputStream(new File(sourcePath));
                OutputStream outputStream = new FileOutputStream(new File(serDir, serFileName));
        ) {
            byte[] bytes = new byte[1024];
            int len;
            while ((len = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //返回最后的服务端文件地址
        return serDir.getPath() + File.separatorChar + serFileName;
    }
}
