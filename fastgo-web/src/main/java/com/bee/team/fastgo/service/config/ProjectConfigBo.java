package com.bee.team.fastgo.service.config;

import com.bee.team.fastgo.vo.config.req.ListProjectConfigsReqVo;
import com.bee.team.fastgo.vo.config.req.RemoveProjectDataReqVo;
import com.bee.team.fastgo.vo.config.req.UpdateProjectConfigReqVo;
import com.spring.simple.development.core.component.mvc.page.ResPageDTO;

import java.util.Map;

/**
 * @description:
 * @author: luke
 * @time: 2020/7/17 0017 10:32
 */
public interface ProjectConfigBo {
    /**
     * @return ResPageDTO
     * @Author xqx
     * @Description 分页模糊查询项目列表信息
     * @Date 10:22 2020/7/22
     * @Param listProjectConfigsReqVo
     **/
    ResPageDTO getProjectConfigList(ListProjectConfigsReqVo listProjectConfigsReqVo);


    /**
     * @return Map
     * @Author xqx
     * @Description 获取配置信息
     * @Date 15:18 2020/7/22
     * @Param projectCode
     **/
    Map<String, Object> getProjectConfigByCode(String projectCode);

    /**
     * @return
     * @Author xqx
     * @Description 修改项目配置信息
     * @Date 15:41 2020/7/22
     * @Param updateProjectConfigReqVo
     **/
    void updateProjectConfig(UpdateProjectConfigReqVo updateProjectConfigReqVo);

}
