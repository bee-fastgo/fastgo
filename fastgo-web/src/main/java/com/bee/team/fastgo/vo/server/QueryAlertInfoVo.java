package com.bee.team.fastgo.vo.server;

import com.spring.simple.development.core.component.mvc.page.ReqPageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author jgz
 * @version 1.0
 * @date 2020/7/22 11:01
 * @ClassName QueryAlertInfoVo
 * @Description 查询告警消息分页对象
 **/
@Data
@ApiModel(value = "QueryAlertInfoVo", description = "查询告警消息分页对象")
public class QueryAlertInfoVo extends ReqPageDTO {

    @ApiModelProperty(value = "告警类型: 软件SOFTWARE 项目PROJECT 服务器SERVER", example = "SOFTWARE")
    public String type;

}
