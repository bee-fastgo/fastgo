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
import org.springframework.web.bind.annotation.RequestBody;
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
    @RequestMapping(value = "/listProjectConfigs", method = RequestMethod.POST)
    @ApiOperation(value = "分页显示项目列表信息（模糊搜索）")
    @ValidHandler(key = "listProjectConfigsReqVo", value = ListProjectConfigsReqVo.class, isReqBody = false)
    public ResBody getListProjectConfigs(@RequestBody ListProjectConfigsReqVo listProjectConfigsReqVo) {
        ResPageDTO resPageDTO = projectConfigBo.getProjectConfigList(listProjectConfigsReqVo);
        return new ResBody().buildSuccessResBody(resPageDTO);
    }

    @RequestMapping(value = "/getProjectConfigByCode", method = RequestMethod.POST)
    @ApiOperation(value = "项目的配置信息详情")
    @ApiImplicitParam(name = "projectCode", value = "项目配置code", dataTypeClass = String.class)
    public ResBody getProjectConfigByCode(@RequestBody String projectCode) {
        if (StringUtils.isEmpty(projectCode)) {
            throw new GlobalException(RES_PARAM_IS_EMPTY, "code不能为空");
        }
        Map<String, Object> map = projectConfigBo.getProjectConfigByCode(projectCode);
        return new ResBody().buildSuccessResBody(map);
    }

    @RequestMapping(value = "/updateProjectConfig", method = RequestMethod.POST)
    @ApiOperation(value = "修改项目配置信息")
    @ValidHandler(key = "updateProjectConfigReqVo", value = UpdateProjectConfigReqVo.class, isReqBody = false)
    public ResBody updateProjectConfig(@RequestBody UpdateProjectConfigReqVo updateProjectConfigReqVo) {
        if (CollectionUtils.isEmpty(updateProjectConfigReqVo.getSoftReqVoList())) {
            throw new GlobalException(RES_PARAM_IS_EMPTY, "参数不能为空");
        }
        projectConfigBo.updateProjectConfig(updateProjectConfigReqVo);
        return new ResBody().buildSuccessResBody();
    }

    @RequestMapping(value = "/removeProjectConfigOneData", method = RequestMethod.POST)
    @ApiOperation(value = "删除项目的某一个配置项")
    @ValidHandler(key = "removeProjectDataReqVo", value = RemoveProjectDataReqVo.class, isReqBody = false)
    public ResBody removeProjectConfigOneDate(@RequestBody RemoveProjectDataReqVo removeProjectDataReqVo) {
        projectConfigBo.removeProjectConfigOneData(removeProjectDataReqVo);
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
    @RequestMapping(value = "/listTemplates", method = RequestMethod.POST)
    @ApiOperation(value = "分页显示模板列表信息（模糊搜索）")
    @ValidHandler(key = "listTemplatesReqVO", value = ListTemplatesReqVO.class, isReqBody = false)
    public ResBody getListTemplates(@RequestBody ListTemplatesReqVO listTemplatesReqVO) {
        ResPageDTO resPageDTO = templateBo.getTemplateConfigsList(listTemplatesReqVO);
        return new ResBody().buildSuccessResBody(resPageDTO);
    }

    @RequestMapping(value = "/getTemplateByCode", method = RequestMethod.POST)
    @ApiOperation(value = "获取模板的详细信息")
    @ApiImplicitParam(name = "code", value = "模板code", dataTypeClass = String.class)
    public ResBody getTemplateByCode(@RequestBody String code) {
        if (StringUtils.isEmpty(code)) {
            throw new GlobalException(RES_PARAM_IS_EMPTY, "code不能为空");
        }
        Map<String, Object> map = templateBo.getTemplateByCode(code);
        return new ResBody().buildSuccessResBody(map);
    }

    @RequestMapping(value = "/insertTemplate", method = RequestMethod.POST)
    @ApiOperation(value = "新增模板")
    @ValidHandler(key = "insertTemplateReqVo", value = InsertTemplateReqVo.class, isReqBody = false)
    public ResBody insetTemplate(@RequestBody InsertTemplateReqVo insertTemplateReqVo) {
        templateBo.insetTemplate(insertTemplateReqVo);
        return new ResBody().buildSuccessResBody();

    }

    @RequestMapping(value = "/updateTemplate", method = RequestMethod.POST)
    @ApiOperation(value = "修改模板")
    @ValidHandler(key = "updateTemplateReqVo", value = UpdateTemplateReqVo.class, isReqBody = false)
    public ResBody updateTemplate(@RequestBody UpdateTemplateReqVo updateTemplateReqVo) {
        if (CollectionUtils.isEmpty(updateTemplateReqVo.getMapReqVos())) {
            throw new GlobalException(RES_PARAM_IS_EMPTY, "参数不能为空");
        }
        templateBo.updateTemplate(updateTemplateReqVo);
        return new ResBody().buildSuccessResBody();
    }

    @RequestMapping(value = "/removeTemplateOneData", method = RequestMethod.POST)
    @ApiOperation(value = "删除模板的某一个配置项")
    @ValidHandler(key = "removeTemplateDataReqVo", value = RemoveTemplateDataReqVo.class, isReqBody = false)
    public ResBody removeTemplateOneData(@RequestBody RemoveTemplateDataReqVo removeTemplateDataReqVo) {
        templateBo.removeTemplateOneData(removeTemplateDataReqVo);
        return new ResBody().buildSuccessResBody();
    }

    @RequestMapping(value = "/removeTemplate", method = RequestMethod.POST)
    @ApiOperation(value = "删除模板")
    @ApiImplicitParam(name = "code", value = "模板code", dataTypeClass = String.class)
    public ResBody removeTemplate(@RequestBody String code) {
        templateBo.removeTemplate(code);
        return new ResBody().buildSuccessResBody();
    }

}
