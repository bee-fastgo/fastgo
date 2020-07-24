package com.bee.team.fastgo.controller.server;

import com.bee.team.fastgo.common.SoftwareEnum;
import com.bee.team.fastgo.service.server.ServerScriptBo;
import com.bee.team.fastgo.vo.server.*;
import com.spring.simple.development.core.annotation.base.ValidHandler;
import com.spring.simple.development.core.component.mvc.page.ResPageDTO;
import com.spring.simple.development.core.component.mvc.res.ResBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author jgz
 * @version 1.0
 * @date 2020/7/22 10:10
 * @ClassName ServiceScriptController
 * @Description 服务器脚本相关
 **/
@Api(tags = "脚本相关")
@RestController("/script")
public class ScriptController {

    @Autowired
    private ServerScriptBo serverScriptBo;

    /**
     * 获取支持的软件类型
     * @param
     * @return {@link ResBody<String>}
     * @author jgz
     * @date 13:17 2020/7/24
     * @Description
     */
    @GetMapping("/getSoftwareList")
    @ApiOperation(value = "获取支持的软件类型")
    public ResBody<String> getSoftwareList(){
        List<String> collect = Stream.of(SoftwareEnum.values()).map(SoftwareEnum::name).map(String::toLowerCase).collect(Collectors.toList());
        return new ResBody().buildSuccessResBody(collect);
    }

    /**
     * 添加脚本
     * @param reqAddScriptVo 请求体
     * @return {@link ResBody<Void>}
     * @author jgz
     * @date 10:03 2020/7/24
     * @Description
     */
    @PostMapping(value = "/addScript")
    @ApiOperation(value = "添加脚本")
    @ValidHandler(key = "reqAddScriptVo", value = ReqAddScriptVo.class,isReqBody = false)
    @ApiImplicitParam(name = "reqAddScriptVo", value = "添加脚本实体", dataTypeClass = ReqAddScriptVo.class)
    public ResBody<Void> addScript(ReqAddScriptVo reqAddScriptVo){
        serverScriptBo.saveScript(reqAddScriptVo);
        return new ResBody().buildSuccessResBody();
    }

    /**
     * 查询脚本分页
     * @param queryScriptVo 请求体
     * @return {@link ResBody<ResScriptVo>}
     * @author jgz
     * @date 10:04 2020/7/24
     * @Description
     */
    @PostMapping(value = "/getScriptListPage")
    @ApiOperation(value = "获取脚本列表分页(分页)")
    @ValidHandler(key = "queryScriptVo", value = QueryScriptVo.class,isReqBody = false)
    @ApiImplicitParam(name = "queryScriptVo", value = "查询脚本分页对象", dataTypeClass = QueryScriptVo.class)
    public ResBody<ResScriptVo> getScriptByPage(QueryScriptVo queryScriptVo){
        ResPageDTO<ResScriptVo> resPageDTO = serverScriptBo.getScriptByPage(queryScriptVo);
        return new ResBody<ResScriptVo>().buildSuccessResBody(resPageDTO);
    }

    /**
     * 通过脚本key获取脚本信息
     * @param scriptKey
     * @return {@link ResBody<ResScriptInfoVo>}
     * @author jgz
     * @date 13:18 2020/7/24
     * @Description
     */
    @GetMapping(value = "/getScriptInfo")
    @ApiOperation(value = "获取脚本信息")
    @ApiImplicitParam(name = "scriptKey", value = "脚本key")
    public ResBody<ResScriptInfoVo> getScriptInfo(String scriptKey){
        ResScriptInfoVo resScriptInfoVo = serverScriptBo.getScriptInfoByScriptKey(scriptKey);
        return  new ResBody<ResScriptInfoVo>().buildSuccessResBody(resScriptInfoVo);
    }

    /**
     * 修改脚本内容
     * @param reqUpdateScriptVo
     * @return {@link ResBody<Void>}
     * @author jgz
     * @date 13:18 2020/7/24
     * @Description
     */
    @PostMapping(value = "/updateScript")
    @ApiOperation(value = "修改脚本内容")
    @ValidHandler(key = "reqUpdateScriptVo", value = ReqUpdateScriptVo.class,isReqBody = false)
    @ApiImplicitParam(name = "reqUpdateScriptVo", value = "修改脚本内容对象", dataTypeClass = ReqUpdateScriptVo.class)
    public ResBody<Void> updateScript(ReqUpdateScriptVo reqUpdateScriptVo){
        serverScriptBo.updateScript(reqUpdateScriptVo);
        return new ResBody().buildSuccessResBody();
    }


    @PostMapping(value = "/deleteScript")
    @ApiOperation(value = "删除脚本")
    @ApiImplicitParam(name = "scriptKey", value = "脚本key")
    public ResBody<Void> deleteScript(String scriptKey){
        serverScriptBo.deleteScriptByScriptKey(scriptKey);
        return new ResBody().buildSuccessResBody();
    }

}
