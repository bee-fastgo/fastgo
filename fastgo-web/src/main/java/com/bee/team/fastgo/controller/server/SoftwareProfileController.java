package com.bee.team.fastgo.controller.server;

import com.bee.team.fastgo.service.server.ServerSoftwareProfileBo;
import com.bee.team.fastgo.vo.server.*;
import com.spring.simple.development.core.annotation.base.ValidHandler;
import com.spring.simple.development.core.component.mvc.page.ResPageDTO;
import com.spring.simple.development.core.component.mvc.res.ResBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jgz
 * @date 2020/7/27
 * @desc 软件环境控制层
 **/
@Api(tags = "软件环境相关")
@RestController
@RequestMapping("/softwareProfile")
public class SoftwareProfileController {

    @Autowired
    private ServerSoftwareProfileBo serverSoftwareProfileBo;

    @PostMapping(value = "/addEnvironment",consumes = "application/json")
    @ApiOperation(value = "添加软件环境")
    @ValidHandler(key = "addEnvironmentVo", value = ReqAddEnvironmentVo.class,isReqBody = false)
    public ResBody<Void> addEnvironment(@RequestBody ReqAddEnvironmentVo addEnvironmentVo){
        serverSoftwareProfileBo.createEnvironment(addEnvironmentVo);
        return new ResBody().buildSuccessResBody();
    }


    @PostMapping(value = "/deleteEnvironment",consumes = "application/json")
    @ApiOperation(value = "删除软件环境")
    @ValidHandler(key = "reqDeleteEnvironmentVo", value = ReqDeleteEnvironmentVo.class,isReqBody = false)
    public ResBody<Void> deleteEnvironment(@RequestBody ReqDeleteEnvironmentVo reqDeleteEnvironmentVo){
        serverSoftwareProfileBo.deleteServerSoftwareProfileBySoftwareCode(reqDeleteEnvironmentVo.getSoftwareCode());
        return new ResBody().buildSuccessResBody();
    }


    @PostMapping(value = "/getEnvironmentListPage",consumes = "application/json")
    @ApiOperation(value = "获取软件环境分页(分页)")
    @ValidHandler(key = "querySoftwareEnvironmentVo", value = QuerySoftwareEnvironmentVo.class,isReqBody = false)
    public ResBody<ResSoftwareEnvironmentVo> getSoftwareEnvironmentByPage(@RequestBody QuerySoftwareEnvironmentVo querySoftwareEnvironmentVo){
        ResPageDTO<ResSoftwareEnvironmentVo> resPageDTO = serverSoftwareProfileBo.getSoftwareEnvironmentByPage(querySoftwareEnvironmentVo);
        return new ResBody<ResSoftwareEnvironmentVo>().buildSuccessResBody(resPageDTO);
    }

    @PostMapping(value = "/updateEnvironment",consumes = "application/json")
    @ApiOperation(value = "修改环境配置(暂未实现)")
    @ValidHandler(key = "reqUpdateSoftwareEnvironmentVo", value = ReqUpdateSoftwareEnvironmentVo.class,isReqBody = false)
    public ResBody<Void> updateEnvironment(@RequestBody ReqUpdateSoftwareEnvironmentVo reqUpdateSoftwareEnvironmentVo){
        serverSoftwareProfileBo.updateSoftwareConfigBySoftwareCode(reqUpdateSoftwareEnvironmentVo.getSoftwareCode(),reqUpdateSoftwareEnvironmentVo.getSoftwareConfig());
        return new ResBody().buildSuccessResBody();
    }


}
