package com.bee.team.fastgo.service.server;

import com.alibaba.lava.base.LavaBo;
import com.bee.team.fastgo.model.ServerScriptDo;
import com.bee.team.fastgo.model.ServerScriptDoExample;
import com.bee.team.fastgo.vo.server.QueryScriptVo;
import com.bee.team.fastgo.vo.server.ReqAddScriptVo;
import com.bee.team.fastgo.vo.server.ResScriptVo;
import com.spring.simple.development.core.component.mvc.page.ResPageDTO;

public interface ServerScriptBo extends LavaBo<ServerScriptDo, ServerScriptDoExample> {

    /**
     * 添加脚本
     * @param reqAddScriptVo 添加脚本
     * @return
     * @author jgz
     * @date 10:25 2020/7/22
     * @Description
     */
    void saveScript(ReqAddScriptVo reqAddScriptVo);


    /**
     * 查询脚本分页
     * @param queryScriptVo
     * @return {@link ResPageDTO<ResScriptVo>}
     * @author jgz
     * @date 11:13 2020/7/22
     * @Description
     */
    ResPageDTO<ResScriptVo> getScriptByPage(QueryScriptVo queryScriptVo);
}