package com.bee.team.fastgo.service.api.config.dto;

import lombok.Data;

import java.util.List;

/**
 * @author xqx
 * @date 2020/7/25 17:03
 * @desc 修改项目的参数
 **/
@Data
public class UpdateConfigDTO {
    /*
    软件名
     */
    private String softName;

    private List<KeyAndValueDto> list;
}
