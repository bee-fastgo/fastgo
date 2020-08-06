package com.bee.team.fastgo.service.api.config.dto;

import lombok.Data;

/**
 * @author xqx
 * @date 2020/8/6 15:34
 * @desc 键值对
 **/
@Data
public class KeyAndValueDto {
    /*
   配置项的key
    */
    private String key;
    /*
    配置项的value
     */
    private String value;
}
