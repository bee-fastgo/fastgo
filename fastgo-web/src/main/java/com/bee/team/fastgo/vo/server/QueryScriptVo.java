package com.bee.team.fastgo.vo.server;

import com.spring.simple.development.core.component.mvc.page.ReqPageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author jgz
 * @version 1.0
 * @date 2020/7/22 11:01
 * @ClassName QueryScriptVo
 * @Description 查询脚本分页对象
 **/
@Data
@ApiModel(value = "QueryScriptVo", description = "查询脚本分页对象")
public class QueryScriptVo extends ReqPageDTO {

    @ApiModelProperty(value = "软件名称", example = "mysql")
    private String softwareName;
}
