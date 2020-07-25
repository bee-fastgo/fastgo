package com.bee.team.fastgo.service.api.config.impl;

import com.bee.team.fastgo.config.common.MongoCommonValue;
import com.bee.team.fastgo.config.service.ConfigProjectBo;
import com.bee.team.fastgo.exception.config.ProjectConfigException;
import com.bee.team.fastgo.service.api.config.ConfigApi;
import com.bee.team.fastgo.service.api.config.dto.UpdateConfigDTO;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.spring.simple.development.support.exception.GlobalException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.spring.simple.development.support.exception.ResponseCode.RES_PARAM_IS_EMPTY;

/**
 * @author xqx
 * @date 2020/7/25 16:48
 * @desc 配置中心api实现类
 **/
@Service
public class ConfigApiImpl implements ConfigApi {
    @Autowired
    private ConfigProjectBo configProjectBo;

    @Override
    public String insertProject(Map<String, Map<String, Object>> map) {
        if (map.isEmpty()) {
            throw new GlobalException(RES_PARAM_IS_EMPTY, "请求参数不能为空");
        }
        return configProjectBo.insertProject(map);
    }

    @Override
    public void updateOneProject(String configCode, List<UpdateConfigDTO> list) {
        if (StringUtils.isEmpty(configCode) || CollectionUtils.isEmpty(list)) {
            throw new GlobalException(RES_PARAM_IS_EMPTY, "请求参数不能为空");
        }
        // 封装查询条件 按 配置中心的唯一标识 configCode查询
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put(MongoCommonValue.PROJECT_BASE_KEY + "." + MongoCommonValue.PROJECT_CODE, configCode);

        // 封装要修改的配置项格式为 mysql.spring-datasource-port
        Map<String, Object> updateMap = new HashMap<>();
        list.stream().forEach(e -> updateMap.put(e.getSoftName() + "." + e.getKey().replace(".", "-"), e.getValue()));
        UpdateResult result = configProjectBo.updateOneProject(queryMap, updateMap);

        // 修改失败
        if (result.getModifiedCount() < 1) {
            throw new GlobalException(ProjectConfigException.UPDATE_PROJECT_FAILED);
        }
    }

    @Override
    public void removeOneProject(String configCode) {
        if (StringUtils.isEmpty(configCode)) {
            throw new GlobalException(RES_PARAM_IS_EMPTY, "请求参数不能为空");
        }

        // 封装查询条件
        Map<String, Object> map = new HashMap<>();
        map.put(MongoCommonValue.PROJECT_BASE_KEY + "." + MongoCommonValue.PROJECT_CODE, configCode);

        // 返回删除结果
        DeleteResult result = configProjectBo.removeOneProject(map);

        // 删除失败
        if (result.getDeletedCount() < 1) {
            throw new GlobalException(ProjectConfigException.REMOVE_PROJECT_FAILED);
        }
    }

    @Override
    public void removeOneDataByCondition(String configCode, String softName, String key) {
        if (StringUtils.isEmpty(configCode) || StringUtils.isEmpty(softName) || StringUtils.isEmpty(key)) {
            throw new GlobalException(RES_PARAM_IS_EMPTY, "请求参数不能为空");
        }

        // 要删除的键 mysql.Spring-datasource-**
        String removeKey = softName + "." + key.replace(".", "-");
        UpdateResult result = configProjectBo.removeOneDataByCondition(configCode, removeKey);

        // 删除失败
        if (result.getModifiedCount() < 1) {
            throw new GlobalException(ProjectConfigException.UPDATE_PROJECT_FAILED);
        }
    }


    @Override
    public String getOneProjectConfigToJSON(String configCode) {
        if (StringUtils.isEmpty(configCode)) {
            throw new GlobalException(RES_PARAM_IS_EMPTY, "项目的code不能为空");
        }
        // 封装查询条件 按 配置中心的唯一标识 configCode查询
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put(MongoCommonValue.PROJECT_BASE_KEY + "." + MongoCommonValue.PROJECT_CODE, configCode);
        return configProjectBo.getOneProjectConfigToJSON(queryMap);
    }
}
