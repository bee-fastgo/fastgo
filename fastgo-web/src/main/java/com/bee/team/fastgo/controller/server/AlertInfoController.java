package com.bee.team.fastgo.controller.server;

import com.bee.team.fastgo.service.server.AlertInfoBo;
import com.bee.team.fastgo.vo.server.QueryAlertInfoVo;
import com.bee.team.fastgo.vo.server.ResAlertInfoVo;
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
 * @date 2020/8/4
 * @desc 告警消息控制层
 **/
@Api(tags = "告警相关")
@RestController
@RequestMapping("/alert")
public class AlertInfoController {

    @Autowired
    private AlertInfoBo alertInfoBo;

    @PostMapping(value = "/getAlertInfoByPage",consumes = "application/json")
    @ApiOperation(value = "获取告警消息分页(分页)")
    @ValidHandler(key = "queryAlertInfoVo", value = QueryAlertInfoVo.class,isReqBody = false)
    public ResBody<ResAlertInfoVo> getAlertInfoByPage(@RequestBody QueryAlertInfoVo queryAlertInfoVo){
        ResPageDTO<ResAlertInfoVo> alertInfoByPage = alertInfoBo.getAlertInfoByPage(queryAlertInfoVo);
        return new ResBody<ResAlertInfoVo>().buildSuccessResBody(alertInfoByPage);
    }


}
