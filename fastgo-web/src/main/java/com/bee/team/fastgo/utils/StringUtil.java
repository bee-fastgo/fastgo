package com.bee.team.fastgo.utils;


import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class StringUtil {

    /**
     * String 转 map
     */
    public static Map<String,String> strToMap(String str){
        Gson gson = new Gson();
        Map<String, String> map = new HashMap<>();
        map = gson.fromJson(str, map.getClass());
        return map;
    }

}
