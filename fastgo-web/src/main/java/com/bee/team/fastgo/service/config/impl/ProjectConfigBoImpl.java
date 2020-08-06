package com.bee.team.fastgo.service.config.impl;

import com.bee.team.fastgo.config.common.MongoCommonValue;
import com.bee.team.fastgo.config.service.ConfigProjectBo;
import com.bee.team.fastgo.mapper.ProjectDoMapperExt;
import com.bee.team.fastgo.service.config.ProjectConfigBo;
import com.bee.team.fastgo.vo.config.req.*;
import com.mongodb.client.result.UpdateResult;
import com.spring.simple.development.core.component.mvc.page.ResPageDTO;
import com.spring.simple.development.support.exception.GlobalException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.bee.team.fastgo.exception.config.ProjectConfigException.*;
import static com.spring.simple.development.support.exception.ResponseCode.RES_DATA_NOT_EXIST;
import static com.spring.simple.development.support.exception.ResponseCode.RES_PARAM_IS_EMPTY;

/**
 * @author xqx
 * @date 2020/7/17
 * @desc 项目配置的实现类
 **/
@Service
public class ProjectConfigBoImpl implements ProjectConfigBo {
    @Autowired
    private ConfigProjectBo configProjectBo;

    @Autowired
    private ProjectDoMapperExt projectDoMapperExt;


    @Override
    public ResPageDTO getProjectConfigList(ListProjectConfigsReqVo listProjectConfigsReqVo) {
        ResPageDTO resPageDTO = new ResPageDTO();
        // 分页参数设置
        Integer skip = (listProjectConfigsReqVo.getPageNum() - 1) * listProjectConfigsReqVo.getPageSize();

        // 返回数据列表
        List<Map<String, Object>> list = null;

        // 如果项目名是空的就查询所有的数据
        if (StringUtils.isEmpty(listProjectConfigsReqVo.getProjectName())) {
            list = configProjectBo.getAllProjectConfigList(Map.class);
        } else {
            Map<String, Object> map = new HashMap<>();
            // 根据项目名模糊搜索，key:项目名 value:关键字
            // MongoCommonValue.PROJECT_BASE_KEY + "." + MongoCommonValue.PROJECT_NAME==base.name
            map.put(MongoCommonValue.PROJECT_BASE_KEY + "." + MongoCommonValue.PROJECT_NAME, listProjectConfigsReqVo.getProjectName());
            list = configProjectBo.getProjectConfigList(map, Map.class);
        }

        int total = list.size();
        // 如果list不为空，对list进行分页
        if (total > 0) {
            // 将分页后的数据放到返回对象中
            resPageDTO.setList(list.stream().skip(skip).limit(listProjectConfigsReqVo.getPageSize()).collect(Collectors.toList()));
        }

        // 设置分页的数据
        resPageDTO.setTotalCount(total);
        resPageDTO.setPageNum(listProjectConfigsReqVo.getPageNum());
        resPageDTO.setPageSize(listProjectConfigsReqVo.getPageSize());

        return resPageDTO;
    }

    @Override
    public Map<String, Object> getProjectConfigByCode(FindProjectConfigVo findProjectConfigVo) {
        Map<String, Object> projectMap = new HashMap<>();
        projectMap.put("projectCode", findProjectConfigVo.getProjectCode());
        projectMap.put("branchName", findProjectConfigVo.getBranchName());
        String configCode = projectDoMapperExt.findProjectConfigCode(projectMap);
        Map<String, Object> map = new HashMap<>();
        // MongoCommonValue.PROJECT_BASE_KEY + "." + MongoCommonValue.PROJECT_CODE== base.configCode
        map.put(MongoCommonValue.PROJECT_BASE_KEY + "." + MongoCommonValue.PROJECT_CODE, configCode);
        return (Map<String, Object>) configProjectBo.getOneProjectConfigInfo(map, Map.class);
    }

    @Override
    public void updateProjectConfig(UpdateProjectConfigReqVo updateProjectConfigReqVo) {
        // 条件参数
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put(MongoCommonValue.PROJECT_BASE_KEY + "." + MongoCommonValue.PROJECT_CODE, updateProjectConfigReqVo.getProjectCode());
        // 要修改的参数
        Map<String, Object> updateMap = new HashMap<>();
        List<SoftReqVo> list = updateProjectConfigReqVo.getSoftReqVoList();

        // 查到该项目的所有key值，如果不存在要修改的key就不让修改，项目配置修改只支持修改已有的配置项的值，不能新增配置项和修改key
        Map<String, Object> objectConfig = (Map<String, Object>) configProjectBo.getOneProjectConfigInfo(queryMap, Map.class);
        objectConfig.remove(MongoCommonValue.CONFIG_TEMPLATE_ID);

        if (objectConfig.isEmpty()) {
            throw new GlobalException(RES_DATA_NOT_EXIST, "项目不存在");
        }

        // 提出软件名和key值，作为要修改的新参数，并且把key值的.换成-，例如mysql.spring-datasource****
        list.forEach(e -> updateMap.put((e.getSoftName() + "." + e.getMapReqVo().getKey().replace(".", "-")), e.getMapReqVo().getValue()));
        UpdateResult result = configProjectBo.updateOneProject(queryMap, updateMap);

        // 修改失败
        if (result.getMatchedCount() < 1 || result.getModifiedCount() < 1) {
            throw new GlobalException(UPDATE_PROJECT_FAILED);
        }
    }

    @Override
    public String getOneProjectConfigToJSON(String projectCode, String branchName) {
        if (StringUtils.isEmpty(projectCode) || StringUtils.isEmpty(branchName)) {
            throw new GlobalException(RES_PARAM_IS_EMPTY, "项目的code不能为空");
        }
        Map<String, Object> projectMap = new HashMap<>();
        projectMap.put("projectCode", projectCode);
        projectMap.put("branchName", branchName);
        String configCode = projectDoMapperExt.findProjectConfigCode(projectMap);
        // 封装查询条件 按 配置中心的唯一标识 configCode查询
        Map<String, Object> queryMap = new HashMap<>();
        if (!StringUtils.isEmpty(configCode)) {
            queryMap.put(MongoCommonValue.PROJECT_BASE_KEY + "." + MongoCommonValue.PROJECT_CODE, configCode);
        }
        return configProjectBo.getOneProjectConfigToJSON(queryMap);
    }

    @Override
    public void delOneDataConfig(DelOneDataReqVo delOneDataReqVo) {
        // 设置要移除的key
        String key = delOneDataReqVo.getSoftName();
        if (!StringUtils.isEmpty(delOneDataReqVo.getKey())) {
            // 如果delOneDataReqVo.getKey()是空的，表示要删除的是软件信息，不为空表示删除某个软件的某个配置项
            key = key + "." + delOneDataReqVo.getKey().replace(".", "-");
        }
        UpdateResult result = configProjectBo.removeOneDataByCondition(delOneDataReqVo.getProjectCode(), key);
        if (result.getModifiedCount() < 1) {
            throw new GlobalException(REMOVE_PROJECT_CONFIG_FAILED);
        }
    }
}
