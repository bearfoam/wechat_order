package com.ly.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author ly
 * @ 2018-09-30
 */
public class JsonUtil {
    public static String toJson(Object object){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        return gson.toJson(object);
    }

}
