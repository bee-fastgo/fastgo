package com.bee.team.fastgo.controller.server;

import com.bee.team.fastgo.model.ServerSourceDo;
import com.bee.team.fastgo.service.server.ServerSourceBo;
import com.bee.team.fastgo.vo.server.ResAddSoftResourceVo;
import com.spring.simple.development.core.annotation.base.ValidHandler;
import com.spring.simple.development.core.component.mvc.BaseSupport;
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
        serverSourceBo.insertSource(baseSupport.objectCopy(addSoftResourceResVo, ServerSourceDo.class));
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
}
