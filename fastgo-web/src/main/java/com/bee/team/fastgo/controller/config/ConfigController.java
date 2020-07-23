package com.bee.team.fastgo.controller.config;

import com.bee.team.fastgo.service.config.ProjectConfigBo;
import com.bee.team.fastgo.service.config.TemplateBo;
import com.bee.team.fastgo.vo.config.req.*;
import com.spring.simple.development.core.annotation.base.ValidHandler;
import com.spring.simple.development.core.component.mvc.page.ResPageDTO;
import com.spring.simple.development.core.component.mvc.res.ResBody;
import com.spring.simple.development.support.exception.GlobalException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static com.spring.simple.development.support.exception.ResponseCode.RES_PARAM_IS_EMPTY;

/**
 * @description: 配置中心
 * @author: luke
 * @time: 2020/7/17 0017 10:32
 */
@Api(tags = "配置中心")
@RestController("/config")
public class ConfigController {
    @Autowired
    private ProjectConfigBo projectConfigBo;
    @Autowired
    private TemplateBo templateBo;

    /*
     一、项目配置管理
     */
    // 1、分页显示项目列表
    @RequestMapping(value = "/listProjectConfigs", method = RequestMethod.POST)
    @ApiOperation("分页显示项目列表信息（模糊搜索）")
    @ApiImplicitParam(name = "listProjectConfigsReqVo", value = "参数信息", dataTypeClass = ListProjectConfigsReqVo.class)
    @ValidHandler(key = "listProjectConfigsReqVo", value = ListProjectConfigsReqVo.class, isReqBody = false)
    public ResBody getListProjectConfigs(ListProjectConfigsReqVo listProjectConfigsReqVo) {
        ResPageDTO resPageDTO = projectConfigBo.getProjectConfigList(listProjectConfigsReqVo);
        return new ResBody().buildSuccessResBody(resPageDTO);
    }

    // 2、项目的配置信息详情
    @RequestMapping(value = "/getProjectConfigByCode", method = RequestMethod.POST)
    @ApiOperation("项目的配置信息详情")
    @ApiImplicitParam(name = "projectCode", value = "项目配置code", dataTypeClass = String.class)
    public ResBody getProjectConfigByCode(String projectCode) {
        if (StringUtils.isEmpty(projectCode)) {
            throw new GlobalException(RES_PARAM_IS_EMPTY, "code不能为空");
        }
        Map<String, Object> map = projectConfigBo.getProjectConfigByCode(projectCode);
        return new ResBody().buildSuccessResBody(map);
    }

    // 3、修改项目的配置信息
    @RequestMapping(value = "/updateProjectConfig", method = RequestMethod.POST)
    @ApiOperation("修改项目配置信息")
    @ApiImplicitParam(name = "updateProjectConfigReqVo", value = "参数信息", dataTypeClass = UpdateProjectConfigReqVo.class)
    @ValidHandler(key = "updateProjectConfigReqVo", value = UpdateProjectConfigReqVo.class, isReqBody = false)
    public ResBody updateProjectConfig(UpdateProjectConfigReqVo updateProjectConfigReqVo) {
        if (CollectionUtils.isEmpty(updateProjectConfigReqVo.getSoftReqVoList())) {
            throw new GlobalException(RES_PARAM_IS_EMPTY, "参数不能为空");
        }
        projectConfigBo.updateProjectConfig(updateProjectConfigReqVo);
        return new ResBody().buildSuccessResBody();
    }

    /*
    二、自定义配置方案管理
     */
    // 1、方案列表（分页）
    // 2、方案的详细信息
    // 3、新增方案
    // 4、修改方案
    // 5、删除方案

    /*
    三、模板库管理
     */
    // 1、模板库列表（分页）
    @RequestMapping(value = "/listTemplates", method = RequestMethod.POST)
    @ApiOperation("分页显示模板列表信息（模糊搜索）")
    @ApiImplicitParam(name = "listTemplatesReqVO", value = "参数信息", dataTypeClass = ListTemplatesReqVO.class)
    @ValidHandler(key = "listTemplatesReqVO", value = ListTemplatesReqVO.class, isReqBody = false)
    public ResBody getListTemplates(ListTemplatesReqVO listTemplatesReqVO) {
        ResPageDTO resPageDTO = templateBo.getTemplateConfigsList(listTemplatesReqVO);
        return new ResBody().buildSuccessResBody(resPageDTO);
    }

    // 2、模板的详细信息
    @RequestMapping(value = "/getTemplateByCode", method = RequestMethod.POST)
    @ApiOperation("获取模板的详细信息")
    @ApiImplicitParam(name = "code", value = "模板code", dataTypeClass = String.class)
    public ResBody getTemplateByCode(String code) {
        if (StringUtils.isEmpty(code)) {
            throw new GlobalException(RES_PARAM_IS_EMPTY, "code不能为空");
        }
        Map<String, Object> map = templateBo.getTemplateByCode(code);
        return new ResBody().buildSuccessResBody(map);
    }

    // 3、模板新增
    @RequestMapping(value = "/insertTemplate", method = RequestMethod.POST)
    @ApiOperation("新增模板")
    @ApiImplicitParam(name = "insertTemplateReqVo", value = "参数信息", dataTypeClass = InsertTemplateReqVo.class)
    @ValidHandler(key = "insertTemplateReqVo", value = InsertTemplateReqVo.class, isReqBody = false)
    public ResBody insetTemplate(InsertTemplateReqVo insertTemplateReqVo) {
        templateBo.insetTemplate(insertTemplateReqVo);
        return new ResBody().buildSuccessResBody();

    }

    // 4、模板修改
    @RequestMapping(value = "/updateTemplate", method = RequestMethod.POST)
    @ApiOperation("修改模板")
    @ApiImplicitParam(name = "updateTemplateReqVo", value = "参数信息", dataTypeClass = UpdateTemplateReqVo.class)
    @ValidHandler(key = "updateTemplateReqVo", value = UpdateTemplateReqVo.class, isReqBody = false)
    public ResBody updateTemplate(UpdateTemplateReqVo updateTemplateReqVo) {
        if (CollectionUtils.isEmpty(updateTemplateReqVo.getMapReqVos())) {
            throw new GlobalException(RES_PARAM_IS_EMPTY, "参数不能为空");
        }
        templateBo.updateTemplate(updateTemplateReqVo);
        return new ResBody().buildSuccessResBody();
    }

    // 5、模板删除
    @RequestMapping(value = "/removeTemplate", method = RequestMethod.POST)
    @ApiOperation("删除模板")
    @ApiImplicitParam(name = "code", value = "模板code", dataTypeClass = String.class)
    public ResBody removeTemplate(String code) {
        templateBo.removeTemplate(code);
        return new ResBody().buildSuccessResBody();
    }

}
