package com.finance.util;


import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author:HuJingJing
 * @className: MD5
 * @deacription:
 * @date: 2021/5/28 17:58
 * @version: 0.1
 * @since: 1.8
 */
public class MD5 {
    public static String encryption(String metaData) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(metaData.getBytes());
        String encryptionDate = new BigInteger(1, md5.digest()).toString(16);
        return encryptionDate;
    }

}
