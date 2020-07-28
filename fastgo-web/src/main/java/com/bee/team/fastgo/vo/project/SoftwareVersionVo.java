package com.bee.team.fastgo.vo.project;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author hs
 * @date 2020/7/28 14:12
 * @desc 软件和版本vo
 **/

@Data
public class SoftwareVersionVo implements Serializable {

    @ApiModelProperty(value = "软件名称",example = "mysql")
    private String SoftwareName;

    @ApiModelProperty(value = "软件版本号",example = "5.0.0")
    private String version;

}
