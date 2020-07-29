package com.bee.team.fastgo.vo.project;

import com.bee.team.fastgo.service.api.server.dto.res.ResSourceListDTO;
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

    private List<ResSourceListDTO> resSourceListDTOList;

}
