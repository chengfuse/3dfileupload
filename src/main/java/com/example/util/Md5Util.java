package com.example.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * @version 1.0
 * @Author: sunjb
 * @Descriptin:
 * @Date: 2021-04-24 22:03
 */
public class Md5Util {

    public static String md5(String str){
        StringBuffer stringBuffer = new StringBuffer();
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(str.getBytes(StandardCharsets.UTF_8));
            byte s[] = m.digest();
            for (int i = 0; i < s.length; i++) {
                stringBuffer.append(Integer.toHexString((0x000000FF & s[i]) | 0xFFFFFF00).substring(6));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuffer.toString();
    }


}
