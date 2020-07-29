package com.bee.team.fastgo.controller.server;

import com.alibaba.fastjson.JSONObject;
import com.bee.team.fastgo.model.ServerSourceDo;
import com.bee.team.fastgo.service.server.ServerSourceBo;
import com.bee.team.fastgo.vo.config.req.MapReqVo;
import com.bee.team.fastgo.vo.server.PageResourceReqVo;
import com.bee.team.fastgo.vo.server.ResAddSoftResourceVo;
import com.spring.simple.development.core.annotation.base.ValidHandler;
import com.spring.simple.development.core.component.mvc.BaseSupport;
import com.spring.simple.development.core.component.mvc.page.ResPageDTO;
import com.spring.simple.development.core.component.mvc.res.ResBody;
import com.spring.simple.development.support.exception.GlobalException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.spring.simple.development.support.exception.ResponseCode.RES_PARAM_IS_EMPTY;

/**
 * @author xqx
 * @date 2020/7/28 17:59
 * @desc 软件资源相关
 **/
@RequestMapping("/soft")
@RestController
@Api(tags = "软件资源相关")
public class SoftSourceController {
    @Autowired
    private ServerSourceBo serverSourceBo;
    @Autowired
    private BaseSupport baseSupport;

    @RequestMapping(value = "/addSoftResource", method = RequestMethod.POST)
    @ApiOperation(value = "添加软件资源")
    @ValidHandler(key = "addSoftResourceResVo", value = ResAddSoftResourceVo.class, isReqBody = false)
    public ResBody addSoftResource(@RequestBody ResAddSoftResourceVo addSoftResourceResVo) {
        ServerSourceDo serverSourceDo = baseSupport.objectCopy(addSoftResourceResVo, ServerSourceDo.class);
        List<MapReqVo> list = addSoftResourceResVo.getMapReqVos();
        Map<String, String> map = new HashMap<>();
        list.stream().forEach(e -> map.put(e.getKey(), e.getValue()));
        serverSourceDo.setSourceConfig(JSONObject.toJSONString(map));
        serverSourceBo.insertSource(serverSourceDo);
        return new ResBody().buildSuccessResBody();
    }

    @RequestMapping(value = "/removeResource", method = RequestMethod.POST)
    @ApiOperation(value = "删除软件资源")
    @ApiImplicitParam(name = "sourceCode", value = "唯一标识", dataTypeClass = String.class)
    public ResBody removeResource(@RequestBody String sourceCode) {
        if (StringUtils.isEmpty(sourceCode)) {
            throw new GlobalException(RES_PARAM_IS_EMPTY, "标识不能为空");
        }
        serverSourceBo.deleteSource(sourceCode);
        return new ResBody().buildSuccessResBody();
    }

    @RequestMapping(value = "/getResourceList", method = RequestMethod.POST)
    @ApiOperation(value = "查询软件资源列表（分页）")
    @ValidHandler(key = "pageResourceReqVo", value = PageResourceReqVo.class, isReqBody = false)
    public ResBody getResourceList(@RequestBody PageResourceReqVo pageResourceReqVo) {
        ResPageDTO resPageDTO = serverSourceBo.listResources(pageResourceReqVo);
        return new ResBody().buildSuccessResBody(resPageDTO);
    }
}
