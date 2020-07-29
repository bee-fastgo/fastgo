package com.bee.team.fastgo.vo.server;

import com.spring.simple.development.core.component.mvc.page.ReqPageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author luke
 * @desc 运行环境元配置
 * @date 2020-07-29
 **/
@Data
@ApiModel(value = "queryRunProfileVo", description = "查询运行环境")
public class QueryRunProfileVo extends ReqPageDTO {

    @ApiModelProperty(value = "查询参数", required = false, example = "xxx")
    private String params;
}
