package com.bee.team.fastgo.utils;


import com.alibaba.fastjson.JSONObject;

import java.util.Map;
import java.util.UUID;

public class StringUtil {

    /**
     * String 转 map
     */
    public static Map<String,String> strToMap(String str){
        return (Map<String,String>)JSONObject.parseObject(str, Map.class);
    }

    public static String getRandomUUID(){
        return UUID.randomUUID().toString().replace("-","").substring(0,16);
    }

    public static void main(String[] args) {
        System.out.println(getRandomUUID());
    }
}
