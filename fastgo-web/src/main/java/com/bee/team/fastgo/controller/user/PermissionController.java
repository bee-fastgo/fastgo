package com.bee.team.fastgo.controller.user;//package com.bee.team.fastgo.controller.user;

import com.bee.team.fastgo.service.user.UserPermissionBo;
import com.bee.team.fastgo.vo.user.AddPermissionReqVo;
import com.bee.team.fastgo.vo.user.PageReqVo;
import com.bee.team.fastgo.vo.user.UpdatePermissionReqVo;
import com.spring.simple.development.core.annotation.base.ValidHandler;
import com.spring.simple.development.core.component.mvc.res.ResBody;
import com.spring.simple.development.support.exception.GlobalException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static com.spring.simple.development.support.exception.ResponseCode.RES_PARAM_IS_EMPTY;

/**
 * @author xqx
 * @date 2020/8/3 15:13
 * @desc 权限管理
 **/

@Api(tags = "权限管理")
@RestController
@RequestMapping("/permission")
public class PermissionController {
    @Autowired
    private UserPermissionBo userPermissionBo;

    @RequestMapping(value = "/listPermissions", method = RequestMethod.POST)
    @ApiOperation(value = "权限列表信息（不按条件查询）")
    @ValidHandler(key = "pageReqVo", value = PageReqVo.class, isReqBody = false)
    public ResBody listPermissions(@RequestBody PageReqVo pageReqVo) {
        return new ResBody().buildSuccessResBody(userPermissionBo.listPermissions(pageReqVo.getPageNum(), pageReqVo.getPageSize()));
    }

    @RequestMapping(value = "/insertPermission", method = RequestMethod.POST)
    @ApiOperation(value = "添加权限信息")
    @ValidHandler(key = "addPermissionReqVo", value = AddPermissionReqVo.class, isReqBody = false)
    public ResBody insertPermission(@RequestBody AddPermissionReqVo addPermissionReqVo) {
        userPermissionBo.insertPermission(addPermissionReqVo);
        return new ResBody().buildSuccessResBody();
    }

    @RequestMapping(value = "/updatePermission", method = RequestMethod.POST)
    @ApiOperation(value = "修改权限信息")
    @ValidHandler(key = "updatePermissionReqVo", value = UpdatePermissionReqVo.class, isReqBody = false)
    public ResBody updatePermission(@RequestBody UpdatePermissionReqVo updatePermissionReqVo) {
        userPermissionBo.updatePermission(updatePermissionReqVo);
        return new ResBody().buildSuccessResBody();
    }

    @RequestMapping(value = "/deletePermission", method = RequestMethod.POST)
    @ApiOperation(value = "删除权限信息")
    @ApiImplicitParam(name = "id", value = "权限id", dataTypeClass = Long.class)
    public ResBody deletePermission(@RequestBody Long id) {
        if (ObjectUtils.isEmpty(id)) {
            throw new GlobalException(RES_PARAM_IS_EMPTY, "权限id不能为空");
        }
        userPermissionBo.deletePermission(id);
        return new ResBody().buildSuccessResBody();
    }

    @RequestMapping(value = "/getAllPermissionList", method = RequestMethod.GET)
    @ApiOperation(value = "获取所有的权限信息")
    public ResBody getAllPermissionList(HttpServletRequest request) {
        return new ResBody().buildSuccessResBody(userPermissionBo.getAllPermissionList());
    }

}
