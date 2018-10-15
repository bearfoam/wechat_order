package com.ly.utils;

import com.ly.enums.CodeEnum;

/**
 * @author ly
 * @ 2018-10-15
 */
public class EnumUtil {
    public static <T extends CodeEnum>T getByCode(Integer code,Class<T>enumClsaa){
        for(T each : enumClsaa.getEnumConstants()){
            if(code.equals(each.getCode())){
                return each;
            }
        }
        return null;
    }
}
