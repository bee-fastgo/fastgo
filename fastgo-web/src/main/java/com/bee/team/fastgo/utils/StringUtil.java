package com.bee.team.fastgo.utils;


import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class StringUtil {

    /**
     * String 转 map
     */
    public static Map<String,Object> strToMap(String str){
        Gson gson = new Gson();
        Map<String, Object> map = new HashMap<String, Object>();
        map = gson.fromJson(str, map.getClass());
        return map;
    }

}
