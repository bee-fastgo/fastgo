package com.bee.team.fastgo.service.config;

import com.bee.team.fastgo.vo.config.req.InsertTemplateReqVo;
import com.bee.team.fastgo.vo.config.req.ListTemplatesReqVO;
import com.bee.team.fastgo.vo.config.req.RemoveTemplateDataReqVo;
import com.bee.team.fastgo.vo.config.req.UpdateTemplateReqVo;
import com.spring.simple.development.core.component.mvc.page.ResPageDTO;

import java.util.Map;

/**
 * @ClassName ConfigTemplateBo
 * @Description 模板管理
 * @Author xqx
 * @Date 2020/7/22 14:56
 * @Version 1.0
 **/
public interface TemplateBo {
    /**
     * @return ResPageDTO
     * @Author xqx
     * @Description 分页模糊查询模板列表信息
     * @Date 13:49 2020/7/22
     * @Param listTemplatesReqVO
     **/
    ResPageDTO getTemplateConfigsList(ListTemplatesReqVO listTemplatesReqVO);

    /**
     * @return
     * @Author xqx
     * @Description 添加模板信息
     * @Date 13:50 2020/7/22
     * @Param insertTemplateReqVo
     **/
    void insetTemplate(InsertTemplateReqVo insertTemplateReqVo);

    /**
     * @return Map
     * @Author xqx
     * @Description 根据code查询模板的详细信息
     * @Date 14:23 2020/7/22
     * @Param code
     **/
    Map<String, Object> getTemplateByCode(String code);

    /**
     * @return
     * @Author xqx
     * @Description 删除模板信息
     * @Date 14:36 2020/7/22
     * @Param code
     **/
    void removeTemplate(String code);

    /**
     * @return
     * @Author xqx
     * @Description 修改模板
     * @Date 14:51 2020/7/22
     * @Param updateTemplateReqVo
     **/
    void updateTemplate(UpdateTemplateReqVo updateTemplateReqVo);

    /**
     * @return
     * @Author xqx
     * @Description 删除模板的某一个配置项
     * @Date 16:32 2020/7/23
     * @Param removeTemplateDataReqVo
     **/
    void removeTemplateOneData(RemoveTemplateDataReqVo removeTemplateDataReqVo);
}
