package com.ly.utils;

import java.util.Random;



/**
 * @author ly
 * @ 2018-09-13
 */
public class KeyUtil {

    /**
     * 生成唯一主键
     * 时间加随机数
     * 多线程并发时生成的主键有可能一样，所以添加一个同步锁
     * @return
     */
    public static synchronized String getUniqueKey(){
        Random random = new Random();
        Integer number = random.nextInt(900000)+100000;
        return System.currentTimeMillis()+String.valueOf(number);
    }

}
