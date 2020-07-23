package com.bee.team.fastgo.service.config.impl;

import com.bee.team.fastgo.config.common.MongoCommonValue;
import com.bee.team.fastgo.config.service.ConfigTemplateBo;
import com.bee.team.fastgo.service.config.TemplateBo;
import com.bee.team.fastgo.vo.config.req.*;
import com.mongodb.client.result.DeleteResult;
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

import static com.bee.team.fastgo.exception.config.TemplateException.*;
import static com.spring.simple.development.support.exception.ResponseCode.RES_DATA_NOT_EXIST;

/**
 * @ClassName ConfigTemplateBoImpl
 * @Description 模板管理
 * @Author xqx
 * @Date 2020/7/22 14:56
 * @Version 1.0
 **/
@Service
public class TemplateBoImpl implements TemplateBo {
    @Autowired
    private ConfigTemplateBo configTemplateBo;

    @Override
    public ResPageDTO getTemplateConfigsList(ListTemplatesReqVO listTemplatesReqVO) {
        ResPageDTO resPageDTO = new ResPageDTO();
        // 分页参数设置
        Integer skip = (listTemplatesReqVO.getPageNum() - 1) * listTemplatesReqVO.getPageSize();

        // 返回数据列表
        List<Map<String, Object>> list = null;

        // 如果模板名是空的就查询所有的数据
        if (StringUtils.isEmpty(listTemplatesReqVO.getName())) {
            list = configTemplateBo.findAllTemplateList(Map.class);
        } else {
            Map<String, Object> map = new HashMap<>();
            map.put(MongoCommonValue.TEMPLATE_NAME, listTemplatesReqVO.getName());
            list = configTemplateBo.findTemplateListByCondition(map, Map.class);
        }

        Integer total = list.size();
        // 如果list不为空，对list进行分页
        if (total > 0) {
            // 将分页后的数据放到返回对象中
            resPageDTO.setList(list.stream().skip(skip).limit(listTemplatesReqVO.getPageSize()).collect(Collectors.toList()));
        }

        // 设置分页的数据
        resPageDTO.setTotalCount(total);
        resPageDTO.setPageNum(listTemplatesReqVO.getPageNum());
        resPageDTO.setPageSize(listTemplatesReqVO.getPageSize());
        return resPageDTO;
    }

    @Override
    public void insetTemplate(InsertTemplateReqVo insertTemplateReqVo) {
        Map<String, Object> map = new HashMap<>();
        map.put(MongoCommonValue.TEMPLATE_NAME, insertTemplateReqVo.getTemplateName());
        map.put(MongoCommonValue.TEMPLATE_DESCRIPTION, insertTemplateReqVo.getDescription());
        List<MapReqVo> list = insertTemplateReqVo.getList();
        list.stream().forEach(e -> map.put(e.getKey(), e.getValue()));
        Map<String, Object> resultMap = configTemplateBo.insertTemplate(map);
        if (resultMap.isEmpty()) {
            throw new GlobalException(INSERT_TEMPLATE_FAILED);
        }
        System.out.println();
    }

    @Override
    public Map<String, Object> getTemplateByCode(String code) {
        Map<String, Object> map = new HashMap<>();
        map.put(MongoCommonValue.TEMPLATE_CODE, code);
        return (Map<String, Object>) configTemplateBo.findTemplateByCondition(map, Map.class);
    }

    @Override
    public void removeTemplate(String code) {
        Map<String, Object> map = new HashMap<>();
        map.put(MongoCommonValue.TEMPLATE_CODE, code);
        DeleteResult result = configTemplateBo.removeTemplateByCondition(map);
        if (result.getDeletedCount() < 1) {
            throw new GlobalException(REMOVE_TEMPLATE_FAILED);
        }
    }

    @Override
    public void updateTemplate(UpdateTemplateReqVo updateTemplateReqVo) {
        // 条件，相当于mysql语句中的where子句里面的键值对
        Map<String, Object> conditionMap = new HashMap<>();
        conditionMap.put(MongoCommonValue.TEMPLATE_CODE, updateTemplateReqVo.getCode());
        // 修改的值，相当于set子句里面键值对
        Map<String, Object> setMap = new HashMap<>();
        updateTemplateReqVo.getMapReqVos().stream().forEach(e -> setMap.put(e.getKey(), e.getValue()));
        UpdateResult result = configTemplateBo.updateTemplate(conditionMap, setMap);

        if (result.getMatchedCount() < 1) {
            throw new GlobalException(RES_DATA_NOT_EXIST, "要修改的项目不存在");
        }

        if (result.getModifiedCount() < 1) {
            throw new GlobalException(UPDATE_TEMPLATE_FAILED);
        }
    }

    @Override
    public void removeTemplateOneData(RemoveTemplateDataReqVo removeTemplateDataReqVo) {
        UpdateResult result = configTemplateBo.removeOneDataByCondition(removeTemplateDataReqVo.getCode(), removeTemplateDataReqVo.getKey());
        if (result.getMatchedCount() < 1) {
            throw new GlobalException(RES_DATA_NOT_EXIST, "模板不存在");
        }

        if (result.getModifiedCount() < 1) {
            throw new GlobalException(REMOVE_TEMPLATE_FAILED);
        }


    }
}
