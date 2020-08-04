package com.bee.team.fastgo.controller.user;//package com.bee.team.fastgo.controller.user;

import com.bee.team.fastgo.service.user.UserBo;
import com.bee.team.fastgo.vo.user.PageReqVo;
import com.bee.team.fastgo.vo.user.UpdateUserRoleReqVo;
import com.spring.simple.development.core.annotation.base.ValidHandler;
import com.spring.simple.development.core.component.mvc.res.ResBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xqx
 * @date 2020/8/3 15:12
 * @desc 用户管理
 **/
@RequestMapping("/user")
@RestController
@Api(tags = "用户管理")
public class UserController {
    @Autowired
    private UserBo userBo;

    @RequestMapping(value = "/listUsers", method = RequestMethod.POST)
    @ApiOperation(value = "获取用户列表（可根据用户名模糊查询）")
    @ValidHandler(key = "pageReqVo", value = PageReqVo.class, isReqBody = false)
    public ResBody listUsers(@RequestBody PageReqVo pageReqVo) {
        return new ResBody().buildSuccessResBody(userBo.ListUsers(pageReqVo.getPageNum(), pageReqVo.getPageSize(), pageReqVo.getCondition()));
    }

    @RequestMapping(value = "/updateUserRole", method = RequestMethod.POST)
    @ApiOperation(value = "修改用户的角色信息")
    @ValidHandler(key = "updateUserRoleReqVo", value = UpdateUserRoleReqVo.class, isReqBody = false)
    public ResBody updateUserRole(@RequestBody UpdateUserRoleReqVo updateUserRoleReqVo) {
        userBo.updateRole(updateUserRoleReqVo.getId(), updateUserRoleReqVo.getRoleId());
        return new ResBody().buildSuccessResBody();
    }

}
