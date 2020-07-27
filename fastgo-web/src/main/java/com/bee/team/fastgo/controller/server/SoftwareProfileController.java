package com.bee.team.fastgo.controller.server;

import com.bee.team.fastgo.service.server.ServerSoftwareProfileBo;
import com.bee.team.fastgo.vo.server.AddEnvironmentVo;
import com.bee.team.fastgo.vo.server.ReqAddScriptVo;
import com.spring.simple.development.core.annotation.base.ValidHandler;
import com.spring.simple.development.core.component.mvc.res.ResBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
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
    @ApiOperation(value = "添加环境")
    @ValidHandler(key = "reqAddScriptVo", value = ReqAddScriptVo.class,isReqBody = false)
    private ResBody<Void> addEnvironment(AddEnvironmentVo addEnvironmentVo){
        serverSoftwareProfileBo.createEnvironment(addEnvironmentVo);
        return new ResBody().buildSuccessResBody();
    }





}
