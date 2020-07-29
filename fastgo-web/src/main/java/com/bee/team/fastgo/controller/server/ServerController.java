package com.bee.team.fastgo.controller.server;

import com.bee.team.fastgo.service.server.ServerBo;
import com.bee.team.fastgo.vo.server.AddServerVo;
import com.bee.team.fastgo.vo.server.ModifyServerVo;
import com.bee.team.fastgo.vo.server.QueryServerVo;
import com.bee.team.fastgo.vo.server.ServerVo;
import com.spring.simple.development.core.annotation.base.ValidHandler;
import com.spring.simple.development.core.component.mvc.page.ResPageDTO;
import com.spring.simple.development.core.component.mvc.res.ResBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @description:
 * @author: luke
 * @time: 2020/7/21 0021 8:51
 */
@Api(tags = "服务器相关")
@RestController
@RequestMapping("/server")
public class ServerController {

    @Autowired
    private ServerBo serverBo;

    /**
     * @return com.spring.simple.development.core.component.mvc.res.ResBody
     * @Author luke
     * @Description 添加server
     * @Date 9:26 2020/7/21 0021
     * @Param [addServerVo]
     **/
    @RequestMapping(value = "/addServer", method = RequestMethod.POST)
    @ValidHandler(key = "addServerVo", value = AddServerVo.class,isReqBody=false)
    @ApiOperation(value = "添加服务器")
    public ResBody<Void> addServer(@ApiParam(name = "addServerVo", value = "添加服务器") @RequestBody AddServerVo addServerVo) {
        serverBo.addServerDo(addServerVo);
        return new ResBody().buildSuccessResBody();
    }

    /**
     * @return com.spring.simple.development.core.component.mvc.res.ResBody
     * @Author luke
     * @Description 修改server
     * @Date 9:26 2020/7/21 0021
     * @Param [modifyServerVo]
     **/
    @RequestMapping(value = "/modifyServer", method = RequestMethod.POST)
    @ValidHandler(key = "modifyServer", value = ModifyServerVo.class)
    @ApiOperation(value = "修改服务器")
    public ResBody<Void> modifyServer(@ApiParam(name = "modifyServerVo", value = "修改服务器Vo对象") @RequestBody ModifyServerVo modifyServerVo) {
        serverBo.modifyServerDo(modifyServerVo);
        return new ResBody().buildSuccessResBody();
    }

    @RequestMapping(value = "/getServerPage", method = RequestMethod.POST)
    @ValidHandler(key = "queryServerVo", value = QueryServerVo.class)
    @ApiOperation(value = "查询服务器(分页)")
    public ResBody<ServerVo> getServerPage(@ApiParam(name = "queryServerVo", value = "服务器查询对象") @RequestBody QueryServerVo queryServerVo) {
        ResPageDTO resPageDTO = serverBo.queryPageServer(queryServerVo);
        return new ResBody().buildSuccessResBody(resPageDTO);
    }

    @RequestMapping(value = "/getServerList", method = RequestMethod.POST)
    @ApiOperation(value = "查询服务器(列表)")
    public ResBody<ServerVo> getServerList() {
        List<ServerVo> serverVos = serverBo.queryListServer();
        return new ResBody().buildSuccessResBody(serverVos);
    }
}
