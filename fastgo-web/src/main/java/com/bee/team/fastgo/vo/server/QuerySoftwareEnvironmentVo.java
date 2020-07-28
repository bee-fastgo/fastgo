package com.bee.team.fastgo.vo.server;

import com.spring.simple.development.core.component.mvc.page.ReqPageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author jgz
 * @date 2020/7/28
 * @desc 请求获取软件环境的实体
 **/
@Data
@ApiModel(value = "QuerySoftwareEnvironmentVo", description = "请求获取软件环境的实体")
public class QuerySoftwareEnvironmentVo extends ReqPageDTO {

    @ApiModelProperty(value = "软件名称", example = "mysql")
    private String softwareName;
}
