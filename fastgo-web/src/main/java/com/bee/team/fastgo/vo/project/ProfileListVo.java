package com.bee.team.fastgo.vo.project;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author hs
 * @date 2020/7/27 14:41
 * @desc 软件环境/运行环境展示
 **/

@Data
@ApiModel(value = "profileListVo",description = "软件环境/运行环境展示vo")
public class ProfileListVo implements Serializable {

    private List<String> ips;

}
