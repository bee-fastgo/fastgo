package com.bee.team.fastgo.controller.server;

import com.bee.team.fastgo.common.SoftwareEnum;
import com.bee.team.fastgo.service.server.ServerScriptBo;
import com.bee.team.fastgo.service.server.ServerSourceBo;
import com.bee.team.fastgo.vo.server.*;
import com.spring.simple.development.core.annotation.base.ValidHandler;
import com.spring.simple.development.core.component.mvc.page.ResPageDTO;
import com.spring.simple.development.core.component.mvc.res.ResBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RestController
@RequestMapping("/script")
public class ScriptController {

    @Autowired
    private ServerScriptBo serverScriptBo;

    @Autowired
    private ServerSourceBo serverSourceBo;

    /**
     * 获取支持的软件类型
     * @param
     * @return {@link ResBody<ResSoftwareVo>}
     * @author jgz
     * @date 13:17 2020/7/24
     * @Description
     */
    @GetMapping("/getSoftwareList")
    @ApiOperation(value = "获取支持的软件类型")
    public ResBody<ResSoftwareVo> getSoftwareList(){
        List<ResSoftwareVo> collect = Stream.of(SoftwareEnum.values()).map(SoftwareEnum::name).map(String::toLowerCase).map(softwareName -> {
            ResSoftwareVo resSoftwareVo = new ResSoftwareVo();
            resSoftwareVo.setSoftwareName(softwareName);
            List<String> versionList = serverSourceBo.listVersions(softwareName);
            resSoftwareVo.setVersionList(versionList);
            return resSoftwareVo;
        }).collect(Collectors.toList());
        return new ResBody<>().buildSuccessResBody(collect);
    }

    /**
     * 添加脚本
     * @param reqAddScriptVo 请求体
     * @return {@link ResBody<Void>}
     * @author jgz
     * @date 10:03 2020/7/24
     * @Description
     */
    @PostMapping(value = "/addScript",consumes = "application/json")
    @ApiOperation(value = "添加脚本")
    @ValidHandler(key = "reqAddScriptVo", value = ReqAddScriptVo.class,isReqBody = false)
    public ResBody<Void> addScript(@RequestBody ReqAddScriptVo reqAddScriptVo){
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
    @PostMapping(value = "/getScriptListPage",consumes = "application/json")
    @ApiOperation(value = "获取脚本列表分页(分页)")
    @ValidHandler(key = "queryScriptVo", value = QueryScriptVo.class,isReqBody = false)
    public ResBody<ResScriptVo> getScriptByPage(@RequestBody QueryScriptVo queryScriptVo){
        ResPageDTO<ResScriptVo> resPageDTO = serverScriptBo.getScriptByPage(queryScriptVo);
        return new ResBody<ResScriptVo>().buildSuccessResBody(resPageDTO);
    }

    /**
     * 通过脚本key获取脚本信息
     * @param reqGetScriptInfoVo
     * @return {@link ResBody<ResScriptInfoVo>}
     * @author jgz
     * @date 13:18 2020/7/24
     * @Description
     */
    @PostMapping(value = "/getScriptInfo",consumes = "application/json")
    @ApiOperation(value = "获取脚本信息")
    @ValidHandler(key = "reqGetScriptInfoVo", value = ReqGetScriptInfoVo.class,isReqBody = false)
    public ResBody<ResScriptInfoVo> getScriptInfo(@RequestBody ReqGetScriptInfoVo reqGetScriptInfoVo){
        ResScriptInfoVo resScriptInfoVo = serverScriptBo.getScriptInfoByScriptKey(reqGetScriptInfoVo.getScriptKey());
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
    @PostMapping(value = "/updateScript",consumes = "application/json")
    @ApiOperation(value = "修改脚本内容")
    @ValidHandler(key = "reqUpdateScriptVo", value = ReqUpdateScriptVo.class,isReqBody = false)
    public ResBody<Void> updateScript(@RequestBody ReqUpdateScriptVo reqUpdateScriptVo){
        serverScriptBo.updateScript(reqUpdateScriptVo);
        return new ResBody().buildSuccessResBody();
    }


    /**
     * 删除脚本
     * @param reqDeleteScriptVo
     * @return {@link ResBody<Void>}
     * @author jgz
     * @date 18:49 2020/7/24
     * @Description
     */
    @PostMapping(value = "/deleteScript")
    @ApiOperation(value = "删除脚本")
    @ValidHandler(key = "reqDeleteScriptVo", value = ReqDeleteScriptVo.class,isReqBody = false)
    public ResBody<Void> deleteScript(@RequestBody ReqDeleteScriptVo reqDeleteScriptVo){
        serverScriptBo.deleteScriptByScriptKey(reqDeleteScriptVo.getScriptKey());
        return new ResBody().buildSuccessResBody();
    }



}
