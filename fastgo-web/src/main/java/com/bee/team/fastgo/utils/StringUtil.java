package com.bee.team.fastgo.utils;


import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class StringUtil {

    /**
     * String 转 map
     */
    public static Map<String,String> strToMap(String str){
        return (Map<String,String>)JSONObject.parseObject(str, Map.class);
    }
}
