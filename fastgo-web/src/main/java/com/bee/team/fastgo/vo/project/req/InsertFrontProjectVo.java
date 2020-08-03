package com.bee.team.fastgo.vo.project.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author hs
 * @date 2020/7/25 17:12
 * @desc 新增前台项目vo
 **/
@Data
@ApiModel(value = "insertFrontProjectVo",description = "新增前台项目vo")
public class InsertFrontProjectVo implements Serializable {

    @ApiModelProperty(value = "项目名称", example = "testProject")
    @NotBlank(message = "项目名称不能为空")
    private String projectName;

    @ApiModelProperty(value = "项目类型:1-backendTemplate,2-h5Template", example = "1")
    @NotNull(message = "项目类型不能为空")
    private Integer projectType;

    @ApiModelProperty(value = "项目描述", example = "测试项目")
    @NotBlank(message = "项目描述不能为空")
    private String projectDesc;

    @ApiModelProperty(value = "项目git地址", example = "172.22.5.222/gitlab")
    private String gitUrl;

    @ApiModelProperty(value = "项目分支", example = "dev")
    private String branchName;

    @ApiModelProperty(value = "运行服务器ip", example = "127.0.0.1")
    @NotNull(message = "运行服务器ip不能为空")
    private String runServerIp;

    @ApiModelProperty(value = "是否生成vue框架：0-不生成，1-生成", example = "1")
    private Integer isConfirm;

}
