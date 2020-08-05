package com.bee.team.fastgo.controller.user;//package com.bee.team.fastgo.controller.user;

import com.bee.team.fastgo.model.UserRoleDo;
import com.bee.team.fastgo.service.user.UserRoleBo;
import com.bee.team.fastgo.service.user.UserRolePermissionBo;
import com.bee.team.fastgo.vo.user.*;
import com.spring.simple.development.core.annotation.base.ValidHandler;
import com.spring.simple.development.core.component.mvc.BaseSupport;
import com.spring.simple.development.core.component.mvc.res.ResBody;
import com.spring.simple.development.support.exception.GlobalException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.spring.simple.development.support.exception.ResponseCode.RES_PARAM_IS_EMPTY;

/**
 * @author xqx
 * @date 2020/8/3 15:12
 * @desc 角色管理
 **/
@Api(tags = "角色管理")
@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private BaseSupport baseSupport;

    @Autowired
    private UserRoleBo userRoleBo;
    @Autowired
    private UserRolePermissionBo userRolePermissionBo;

    @RequestMapping(value = "/listRoles", method = RequestMethod.POST)
    @ApiOperation(value = "角色列表信息（不按条件查询）")
    @ValidHandler(key = "pageReqVo", value = PageReqVo.class, isReqBody = false)
    public ResBody listRoles(@RequestBody PageReqVo pageReqVo) {
        return new ResBody().buildSuccessResBody(userRoleBo.ListRoles(pageReqVo.getPageNum(), pageReqVo.getPageSize()));
    }

    @RequestMapping(value = "/addRole", method = RequestMethod.POST)
    @ApiOperation(value = "添加角色信息")
    @ValidHandler(key = "addRoleReqVo", value = AddRoleReqVo.class, isReqBody = false)
    public ResBody addRole(@RequestBody AddRoleReqVo addRoleReqVo) {
        userRoleBo.insertRole(addRoleReqVo);
        return new ResBody().buildSuccessResBody();
    }

    @RequestMapping(value = "/updateRole", method = RequestMethod.POST)
    @ApiOperation(value = "修改角色信息")
    @ValidHandler(key = "updateRoleReqVo", value = UpdateRoleReqVo.class, isReqBody = false)
    public ResBody updateRole(@RequestBody UpdateRoleReqVo updateRoleReqVo) {
        userRoleBo.updateRole(updateRoleReqVo);
        return new ResBody().buildSuccessResBody();
    }

    @RequestMapping(value = "/deleteRole", method = RequestMethod.POST)
    @ApiOperation(value = "删除角色信息")
    @ApiImplicitParam(name = "id", value = "角色id", dataTypeClass = Long.class)
    public ResBody deleteRole(@RequestBody Long id) {
        if (ObjectUtils.isEmpty(id)) {
            throw new GlobalException(RES_PARAM_IS_EMPTY, "角色id不能为空");
        }
        userRoleBo.deleteRole(id);
        return new ResBody().buildSuccessResBody();
    }

    @RequestMapping(value = "/listRolePermissions", method = RequestMethod.POST)
    @ApiOperation(value = "获取角色已经绑定的权限id列表（集合）")
    @ApiImplicitParam(name = "id", value = "角色id", dataTypeClass = Long.class)
    public ResBody listRolePermissions(@RequestBody Long id) {
        if (ObjectUtils.isEmpty(id)) {
            throw new GlobalException(RES_PARAM_IS_EMPTY, "角色id不能为空");
        }
        return new ResBody().buildSuccessResBody(userRolePermissionBo.listRolePermissions(id));
    }

    @RequestMapping(value = "/updateRolePermission", method = RequestMethod.POST)
    @ApiOperation(value = "给角色绑定权限")
    @ValidHandler(key = "updateRolePermission", value = UpdateRolePermission.class, isReqBody = false)
    public ResBody updateRolePermission(@RequestBody UpdateRolePermission updateRolePermission) {
        userRolePermissionBo.updateRolePermission(updateRolePermission.getRoleId(), updateRolePermission.getPermissionIds());
        return new ResBody().buildSuccessResBody();
    }

    @RequestMapping(value = "/getAllRoles", method = RequestMethod.GET)
    @ApiOperation(value = "获取所有的角色信息")
    public ResBody getAllRoles(HttpServletRequest request) {
        List<UserRoleDo> list = userRoleBo.getAllRoles();
        List<ListRoleResVo> listRoleResVos = null;
        if (!CollectionUtils.isEmpty(list)) {
            listRoleResVos = baseSupport.listCopy(userRoleBo.getAllRoles(), ListRoleResVo.class);
        }
        return new ResBody().buildSuccessResBody(listRoleResVos);
    }
}
