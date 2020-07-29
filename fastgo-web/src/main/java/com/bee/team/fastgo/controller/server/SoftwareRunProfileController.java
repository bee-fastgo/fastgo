package com.bee.team.fastgo.controller.server;

import com.bee.team.fastgo.service.server.ServerRunProfileBo;
import com.bee.team.fastgo.service.server.ServerSoftwareProfileBo;
import com.bee.team.fastgo.vo.server.*;
import com.spring.simple.development.core.annotation.base.ValidHandler;
import com.spring.simple.development.core.component.mvc.page.ResPageDTO;
import com.spring.simple.development.core.component.mvc.res.ResBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author luke
 * @date 2020/7/27
 * @desc 运行环境控制层
 **/
@Api(tags = "运行环境相关")
@RestController
@RequestMapping("/softwareRunProfile")
public class SoftwareRunProfileController {

    @Autowired
    private ServerRunProfileBo serverRunProfileBo;


    @PostMapping(value = "/delServerRunProfileVo", consumes = "application/json")
    @ApiOperation(value = "销毁运行环境")
    @ValidHandler(key = "delServerRunProfileVo", value = DelServerRunProfileVo.class)
    public ResBody<Void> delServerRunProfileVo(@RequestBody DelServerRunProfileVo delServerRunProfileVo) {
        serverRunProfileBo.delServerRunProfileDo(delServerRunProfileVo);
        return new ResBody().buildSuccessResBody();
    }

    @RequestMapping(value = "/queryPageServerRunProfileVo", method = RequestMethod.POST)
    @ValidHandler(key = "queryRunProfileVo", value = QueryRunProfileVo.class)
    @ApiOperation(value = "查询运行环境(分页)")
    public ResBody<ServerRunProfileVo> queryPageServerRunProfileVo(@ApiParam(name = "queryRunProfileVo", value = "查询运行环境") @RequestBody QueryRunProfileVo queryRunProfileVo) {
        ResPageDTO resPageDTO = serverRunProfileBo.queryPageServerRunProfileDo(queryRunProfileVo);
        return new ResBody().buildSuccessResBody(resPageDTO);
    }

}
