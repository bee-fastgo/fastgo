package com.bee.team.fastgo.service.config.impl;

import com.bee.team.fastgo.config.service.ConfigProjectBo;
import com.bee.team.fastgo.service.config.ProjectConfigBo;
import com.bee.team.fastgo.vo.config.req.ListProjectConfigsReqVo;
import com.bee.team.fastgo.vo.config.req.SoftReqVo;
import com.bee.team.fastgo.vo.config.req.UpdateProjectConfigReqVo;
import com.spring.simple.development.core.component.mvc.page.ResPageDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName ConfigBoImpl
 * @Description
 * @Author xqx
 * @Date 2020/7/17 15:56
 * @Version 1.0
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
            map.put("name", "config");
            list = configProjectBo.getProjectConfigList(map, Map.class);
        }

        Integer total = list.size();
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
        map.put("projectCode", projectCode);
        return (Map<String, Object>) configProjectBo.getOneProjectConfigInfo(map, Map.class);
    }

    @Override
    public void updateProjectConfig(UpdateProjectConfigReqVo updateProjectConfigReqVo) {
        // 条件参数
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("projectCode", updateProjectConfigReqVo.getProjectCode());
        // 要修改的参数
        Map<String, Object> updateMap = new HashMap<>();
        List<SoftReqVo> list = updateProjectConfigReqVo.getSoftReqVoList();
        // 提出软件名和key值，作为要修改的新参数，并且把key值的.换成-，例如mysql.spring-datasource****
        list.stream().forEach(e->updateMap.put((e.getSoftName()+e.getMapReqVo().getKey().replace(".","-")),e.getMapReqVo().getValue()));
        configProjectBo.updateOneProject(queryMap, updateMap);
    }


}
