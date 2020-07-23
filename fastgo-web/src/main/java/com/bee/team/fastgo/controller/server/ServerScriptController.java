package com.bee.team.fastgo.controller.server;

import com.bee.team.fastgo.service.server.ServerScriptBo;
import com.bee.team.fastgo.vo.server.QueryScriptVo;
import com.bee.team.fastgo.vo.server.ReqAddScriptVo;
import com.bee.team.fastgo.vo.server.ResScriptVo;
import com.spring.simple.development.core.annotation.base.ValidHandler;
import com.spring.simple.development.core.component.mvc.page.ResPageDTO;
import com.spring.simple.development.core.component.mvc.res.ResBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jgz
 * @version 1.0
 * @date 2020/7/22 10:10
 * @ClassName ServiceScriptController
 * @Description 服务器脚本相关
 **/
@Api(tags = "脚本相关")
@RestController("/script")
public class ServerScriptController {

    @Autowired
    private ServerScriptBo serverScriptBo;

    @PostMapping(value = "/addScript")
    @ApiOperation(value = "添加脚本")
    @ValidHandler(key = "reqAddScriptVo", value = ReqAddScriptVo.class,isReqBody = false)
    @ApiImplicitParam(name = "reqAddScriptVo", value = "添加脚本实体", dataTypeClass = ReqAddScriptVo.class)
    public ResBody<Void> addScript(ReqAddScriptVo reqAddScriptVo){
        serverScriptBo.saveScript(reqAddScriptVo);
        return new ResBody().buildSuccessResBody();
    }

    @PostMapping(value = "/getScriptListPage")
    @ApiOperation(value = "获取脚本列表分页(分页)")
    @ValidHandler(key = "queryScriptVo", value = QueryScriptVo.class,isReqBody = false)
    @ApiImplicitParam(name = "queryScriptVo", value = "查询脚本分页对象", dataTypeClass = QueryScriptVo.class)
    public ResBody<ResScriptVo> getScriptByPage(QueryScriptVo queryScriptVo){
        ResPageDTO<ResScriptVo> resPageDTO = serverScriptBo.getScriptByPage(queryScriptVo);
        return new ResBody<ResScriptVo>().buildSuccessResBody(resPageDTO);
    }


}
