package com.bee.team.fastgo.service.api.config;

import com.bee.team.fastgo.service.api.config.dto.UpdateConfigDTO;

import java.util.List;
import java.util.Map;

/**
 * @ClassName ConfigApi
 * @Description 配置中心的api
 * @Author xqx
 * @Date 2020/7/25 16:47
 * @Version 1.0
 **/
public interface ConfigApi {
    /**
     * 添加项目信息
     *
     * @param map
     * @return {@link String}
     * @author xqx
     * @date 2020/7/25
     * @desc 添加项目
     */
    String insertProject(Map<String, Map<String, Object>> map);

    /**
     * 修改项目信息
     *
     * @param configCode
     * @param list
     * @return
     * @author xqx
     * @date 2020/7/25
     * @desc 修改项目信息
     */
    void updateOneProject(String configCode, List<UpdateConfigDTO> list);

    /**
     * 删除一个项目
     *
     * @param configCode
     * @return
     * @author xqx
     * @date 2020/7/25
     * @desc 删除一个项目
     */

    void removeOneProject(String configCode);

    /**
     * 删除项目里面的一个配置项
     *
     * @param configCode
     * @param softName
     * @param key
     * @return
     * @author xqx
     * @date 2020/7/25
     * @desc 删除项目里面的一个配置项
     */

    void removeOneDataByCondition(String configCode, String softName, String key);

    /**
     * 获取项目的配置信息（JSON）
     *
     * @param configCode
     * @return {@link String}
     * @author xqx
     * @date 2020/7/25
     * @desc 获取项目的配置信息（JSON）
     */
    String getOneProjectConfigToJSON(String configCode);

}
