package com.bee.team.fastgo.vo.project;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author hs
 * @date 2020/7/27 14:41
 * @desc 运行环境展示
 **/

@Data
@ApiModel(value = "sofrwateProfileListVo",description = "软件环境展示vo")
public class SofrwateProfileListVo implements Serializable {

    @ApiModelProperty(value = "服务器ip",example = "['1.1.1.1']")
    private List<String> ips;

    @ApiModelProperty(value = "软件名称和版本对象vo",example = "['redis','mysql']")
    private  List<SoftwareVersionVo> softwares;

}
