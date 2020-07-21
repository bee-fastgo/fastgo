package com.bee.team.fastgo.vo.server;

import com.spring.simple.development.core.component.mvc.page.ReqPageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * @author liko
 * @description MyBatis Generator 自动创建,对应数据表为：t_server
 * @date 2020/07/20
 */
@Data
@ApiModel(value = "addServerVo", description = "添加服务对象")
public class QueryServerVo extends ReqPageDTO {

    @ApiModelProperty(value = "查询参数", required = false, example = "xxx")
    private String params;
}