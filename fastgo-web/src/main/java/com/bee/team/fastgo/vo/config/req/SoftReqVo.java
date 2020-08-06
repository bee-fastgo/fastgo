package com.bee.team.fastgo.vo.config.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @ClassName SoftReqVo
 * @Description 软件参数
 * @Author xqx
 * @Date 2020/7/22 15:34
 * @Version 1.0
 **/
@ApiModel(value = "softReqVo", description = "软件参数")
@Data
public class SoftReqVo {
    @ApiModelProperty(value = "软件名", example = "mysql", required = true)
    @NotNull(message = "软件名不能为空")
    private String softName;

    @ApiModelProperty(value = "要修改的参数键值对", required = true)
    private List<MapReqVo> mapReqVos;
}
