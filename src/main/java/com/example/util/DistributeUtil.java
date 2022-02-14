package com.example.util;

/**
 * @version 1.0
 * @Author: sunjb
 * @Descriptin:
 * @Date: 2021-06-13 11:58
 */
public class DistributeUtil {

    /**
     * 方法描述 ：randomId
     * @author : sunjb
     */
    public static String randomId(){
        IdWorker idWorker = new IdWorker();
        String randomStr = idWorker.nextId().toString();
        return randomStr;
    }

}
