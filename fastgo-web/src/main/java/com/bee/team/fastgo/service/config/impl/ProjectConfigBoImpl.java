package com.bee.team.fastgo.service.config.impl;

import com.bee.team.fastgo.config.common.MongoCommonValue;
import com.bee.team.fastgo.config.service.ConfigProjectBo;
import com.bee.team.fastgo.service.config.ProjectConfigBo;
import com.bee.team.fastgo.vo.config.req.ListProjectConfigsReqVo;
import com.bee.team.fastgo.vo.config.req.SoftReqVo;
import com.bee.team.fastgo.vo.config.req.UpdateProjectConfigReqVo;
import com.mongodb.client.result.UpdateResult;
import com.spring.simple.development.core.component.mvc.page.ResPageDTO;
import com.spring.simple.development.support.exception.GlobalException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.bee.team.fastgo.exception.config.ProjectConfigException.UPDATE_PROJECT_FAILED;
import static com.spring.simple.development.support.exception.ResponseCode.RES_DATA_NOT_EXIST;

/**
 * @author xqx
 * @date 2020/7/17
 * @desc 项目配置的实现类
 **/
@Service
public class ProjectConfigBoImpl implements ProjectConfigBo {
    @Autowired
    private ConfigProjectBo configProjectBo;


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
    public Map<String, Object> getProjectConfigByCode(String projectCode) {
        Map<String, Object> map = new HashMap<>();
        // MongoCommonValue.PROJECT_BASE_KEY + "." + MongoCommonValue.PROJECT_CODE== base.configCode
        map.put(MongoCommonValue.PROJECT_BASE_KEY + "." + MongoCommonValue.PROJECT_CODE, projectCode);
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

        // 提取所有的软件名
        List<Object> softs = Arrays.asList(objectConfig.keySet().toArray());

        // 判断是要修改的软件是否存在
        list.stream().forEach(e -> {
            if (!softs.contains(e.getSoftName())) {
                throw new GlobalException(RES_DATA_NOT_EXIST, "软件不存在");
            }
        });

        // 判断软件下面的key是否存在
        list.stream().forEach(e -> {
            softs.stream().forEach(soft -> {
                Map<String, Object> map = (Map<String, Object>) objectConfig.get(soft);
                if (!map.containsKey(e.getMapReqVo().getKey())) {
                    throw new GlobalException(RES_DATA_NOT_EXIST, "配置项不存在");
                }
            });
        });


        // 提出软件名和key值，作为要修改的新参数，并且把key值的.换成-，例如mysql.spring-datasource****
        list.stream().forEach(e -> updateMap.put((e.getSoftName() + "." + e.getMapReqVo().getKey().replace(".", "-")), e.getMapReqVo().getValue()));
        UpdateResult result = configProjectBo.updateOneProject(queryMap, updateMap);

        // 修改失败
        if (result.getMatchedCount() < 1 || result.getModifiedCount() < 1) {
            throw new GlobalException(UPDATE_PROJECT_FAILED);
        }
    }
}
