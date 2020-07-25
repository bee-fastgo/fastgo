package com.bee.team.fastgo.service.server;

import com.alibaba.lava.base.LavaBo;
import com.bee.team.fastgo.model.ServerScriptDo;
import com.bee.team.fastgo.model.ServerScriptDoExample;
import com.bee.team.fastgo.vo.server.*;
import com.spring.simple.development.core.component.mvc.page.ResPageDTO;

public interface ServerScriptBo extends LavaBo<ServerScriptDo, ServerScriptDoExample> {

    /**
     * 添加脚本
     * @param reqAddScriptVo 添加脚本实体
     * @return
     * @author jgz
     * @date 10:25 2020/7/22
     * @Description
     */
    void saveScript(ReqAddScriptVo reqAddScriptVo);


    /**
     * 查询脚本分页
     * @param queryScriptVo 查询条件实体
     * @return {@link ResPageDTO<ResScriptVo>}
     * @author jgz
     * @date 11:13 2020/7/22
     * @Description
     */
    ResPageDTO<ResScriptVo> getScriptByPage(QueryScriptVo queryScriptVo);


    /**
     * 获取脚本信息
     * @param scriptKey 脚本key
     * @return {@link com.bee.team.fastgo.vo.server.ResScriptInfoVo}
     * @author jgz
     * @date 11:22 2020/7/24
     * @Description
     */
    ResScriptInfoVo getScriptInfoByScriptKey(String scriptKey);

    /**
     * 修改脚本信息
     * @param reqUpdateScriptVo 修改内容实体
     * @return
     * @author jgz
     * @date 11:49 2020/7/24
     * @Description
     */
    void updateScript(ReqUpdateScriptVo reqUpdateScriptVo);

    /**
     * 通过脚本key删除脚本
     * @param scriptKey 脚本key
     * @return
     * @author jgz
     * @date 13:24 2020/7/24
     * @Description
     */
    void deleteScriptByScriptKey(String scriptKey);

    /**
     * 通过脚本名,版本,脚本类型获取脚本信息
     * @param softwareName
     * @param version
     * @param type
     * @return {@link ServerScriptDo}
     * @author jgz
     * @date 13:46 2020/7/25
     * @Description
     */
    ServerScriptDo getScriptBySoftwareNameAndVersionAndType(String softwareName, String version, String type);
}