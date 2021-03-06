package com.bee.team.fastgo.vo.project;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;


/**
 * @author hs
 * @date 2020/7/28 10:24
 * @desc 运行环境展示
 **/

@Data
@ApiModel(value = "runProfileListVo",description = "运行环境展示vo")
public class RunProfileListVo implements Serializable {

    @ApiModelProperty(value = "服务器ip",example = "1.1.1.1")
    private List<String> ips;

}
