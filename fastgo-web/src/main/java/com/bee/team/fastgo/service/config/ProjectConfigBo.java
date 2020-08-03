package com.bee.team.fastgo.service.config;

import com.bee.team.fastgo.vo.config.req.DelOneDataReqVo;
import com.bee.team.fastgo.vo.config.req.FindProjectConfigVo;
import com.bee.team.fastgo.vo.config.req.ListProjectConfigsReqVo;
import com.bee.team.fastgo.vo.config.req.UpdateProjectConfigReqVo;
import com.spring.simple.development.core.component.mvc.page.ResPageDTO;

import java.util.Map;

/**
 * @author xqx
 * @date 2020/7/22
 * @desc 项目管理
 **/
public interface ProjectConfigBo {
    /**
     * 分页获取项目列表
     *
     * @param listProjectConfigsReqVo
     * @return {@link ResPageDTO}
     * @author xqx
     * @date 2020/7/25
     * @desc 分页获取项目列表
     */
    ResPageDTO getProjectConfigList(ListProjectConfigsReqVo listProjectConfigsReqVo);

    /**
     * 获取项目配置信息
     *
     * @param findProjectConfigVo
     * @return {@link Map< String, Object>}
     * @author xqx
     * @date 2020/7/25
     * @desc 获取项目配置信息
     */

    Map<String, Object> getProjectConfigByCode(FindProjectConfigVo findProjectConfigVo);

    /**
     * @param updateProjectConfigReqVo
     * @return
     * @author xqx
     * @date 2020/7/25
     * @desc 修改项目配置信息
     */
    void updateProjectConfig(UpdateProjectConfigReqVo updateProjectConfigReqVo);

    /**
     * 获取项目的配置信息（JSON）
     *
     * @param projectCode
     * @param branchName
     * @return {@link String}
     * @author xqx
     * @date 2020/7/25
     * @desc 获取项目的配置信息（JSON）
     */
    String getOneProjectConfigToJSON(String projectCode, String branchName);

    /**
     * 删除项目里面的一个配置项
     *
     * @param delOneDataReqVo
     * @return
     * @author xqx
     * @date 2020/8/3
     * @desc 删除项目里面的一个配置项
     */
    void delOneDataConfig(DelOneDataReqVo delOneDataReqVo);
}
