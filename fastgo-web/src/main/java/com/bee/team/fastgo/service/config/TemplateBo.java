package com.bee.team.fastgo.service.config;

import com.bee.team.fastgo.vo.config.req.InsertTemplateReqVo;
import com.bee.team.fastgo.vo.config.req.ListTemplatesReqVO;
import com.bee.team.fastgo.vo.config.req.RemoveTemplateDataReqVo;
import com.bee.team.fastgo.vo.config.req.UpdateTemplateReqVo;
import com.spring.simple.development.core.component.mvc.page.ResPageDTO;

import java.util.Map;

/**
 * @author xqx
 * @date 2020/7/22
 * @desc 模板管理
 **/
public interface TemplateBo {
    /**
     * 分页模糊查询模板列表信息
     *
     * @param listTemplatesReqVO
     * @return {@link ResPageDTO}
     * @author xqx
     * @date 2020/7/25
     * @desc 分页模糊查询模板列表信息
     */
    ResPageDTO getTemplateConfigsList(ListTemplatesReqVO listTemplatesReqVO);

    /**
     * 添加模板信息
     *
     * @param insertTemplateReqVo
     * @return
     * @author xqx
     * @date 2020/7/25
     * @desc 添加模板信息
     */
    void insetTemplate(InsertTemplateReqVo insertTemplateReqVo);

    /**
     * 根据code查询模板的详细信息
     *
     * @param code
     * @return {@link Map< String, Object>}
     * @author xqx
     * @date 2020/7/25
     * @desc 根据code查询模板的详细信息
     */
    Map<String, Object> getTemplateByCode(String code);

    /**
     * 删除模板信息
     *
     * @param code
     * @return
     * @author xqx
     * @date 2020/7/25
     * @desc 删除模板信息
     */
    void removeTemplate(String code);

    /**
     * 修改模板
     *
     * @param updateTemplateReqVo
     * @return
     * @author xqx
     * @date 2020/7/25
     * @desc 修改模板
     */
    void updateTemplate(UpdateTemplateReqVo updateTemplateReqVo);

    /**
     * 删除模板的某一个配置项
     *
     * @param removeTemplateDataReqVo
     * @return
     * @author xqx
     * @date 2020/7/25
     * @desc 删除模板的某一个配置项
     */
    void removeTemplateOneData(RemoveTemplateDataReqVo removeTemplateDataReqVo);
}
