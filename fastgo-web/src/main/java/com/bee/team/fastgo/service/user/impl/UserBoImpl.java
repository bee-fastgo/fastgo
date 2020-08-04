package com.bee.team.fastgo.service.user.impl;//package com.bee.team.fastgo.service.user.impl;

import com.alibaba.lava.base.AbstractLavaBoImpl;
import com.bee.team.fastgo.common.CommonLoginValue;
import com.bee.team.fastgo.mapper.UserDoMapperExt;
import com.bee.team.fastgo.model.UserDo;
import com.bee.team.fastgo.model.UserDoExample;
import com.bee.team.fastgo.service.user.*;
import com.bee.team.fastgo.vo.user.UserInfoResVo;
import com.bee.team.fastgo.vo.user.UserListResVo;
import com.spring.simple.development.core.annotation.base.NoApiMethod;
import com.spring.simple.development.core.component.mvc.BaseSupport;
import com.spring.simple.development.core.component.mvc.page.ResPageDTO;
import com.spring.simple.development.support.exception.GlobalException;
import com.spring.simple.development.support.utils.Md5Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

import static com.spring.simple.development.support.exception.ResponseCode.*;

/**
 * @author xqx
 * @date 2020/7/17
 * @desc 用户管理
 **/
@Service
public class UserBoImpl extends AbstractLavaBoImpl<UserDo, UserDoMapperExt, UserDoExample> implements UserBo {
    @Autowired
    private BaseSupport baseSupport;

    @Autowired
    private UserRolePermissionBo userRolePermissionBo;
    @Autowired
    private UserRoleBo userRoleBo;
    @Autowired
    private UserPermissionBo userPermissionBo;
    @Autowired
    private DynamicMenuBo dynamicMenuBo;

    @Autowired
    @NoApiMethod
    public void setBaseMapper(UserDoMapperExt mapper) {
        setMapper(mapper);
    }

    @Override
    public UserInfoResVo login(HttpServletRequest request, String userName, String password) {
        UserDoExample example = new UserDoExample();
        example.createCriteria().andUserNameEqualTo(userName).andIsDeletedEqualTo("n");
        List<UserDo> list = selectByExample(example);
        if (CollectionUtils.isEmpty(list)) {
            throw new GlobalException(RES_DATA_NOT_EXIST, "用户不存在");
        }
        UserDo userDo = list.get(0);

        if (!StringUtils.equals(userDo.getUserPassword(), Md5Utils.getTwoMD5Str(password))) {
            throw new GlobalException(RES_PWD_OR_CODE_INVALID, "用户名或密码错误");
        }
        // 将信息保存到session
        HttpSession session = request.getSession();
        // 如果不存在session就新增session
        if (ObjectUtils.isEmpty(session.getAttribute(userName))) {
            session.setAttribute(CommonLoginValue.SESSION_LOGIN_KEY, userDo);
        }

        // 获取用户绑定的id列表
        List<Long> permissionIds = userRolePermissionBo.listRolePermissions(userDo.getRoleId());

        // 登录成功返回用户的动态权限信息，菜单信息，个人信息
        UserInfoResVo userInfoResVo = new UserInfoResVo();
        userInfoResVo.setUserName(userDo.getUserName());
        userInfoResVo.setPermissionResVos(userPermissionBo.getUserBindPermissionList(permissionIds));
        userInfoResVo.setMenuListResVos(dynamicMenuBo.getUserBindMenus(permissionIds));
        return userInfoResVo;

    }

    @Override
    public void insertUser(String userName, String password) {
        UserDoExample example = new UserDoExample();
        example.createCriteria().andUserNameEqualTo(userName).andIsDeletedEqualTo("n");
        List<UserDo> list = selectByExample(example);
        if (!CollectionUtils.isEmpty(list)) {
            throw new GlobalException(RES_DATA_EXIST, "用户已存在");
        }
        UserDo userDo = new UserDo();
        userDo.setUserName(userName);
        userDo.setUserPassword(Md5Utils.getTwoMD5Str(password));

        insert(userDo);
    }

    @Override
    public ResPageDTO ListUsers(Integer pageNum, Integer pageSize, String name) {
        // 设置默认分页
        ResPageDTO resPageDTO = new ResPageDTO();
        resPageDTO.setPageNum(pageNum);
        resPageDTO.setPageSize(pageSize);
        resPageDTO.setTotalCount(0);
        resPageDTO.setList(null);

        UserDoExample example = new UserDoExample();
        if (!StringUtils.isEmpty(name)) {
            example.createCriteria().andUserNameLike(name);
        }
        // 获取所有的用户信息
        List<UserDo> list = selectByExample(example);

        if (!CollectionUtils.isEmpty(list)) {
            // 返回用户信息，并查询角色名字
            List<UserListResVo> userListResVos = baseSupport.listCopy(list, UserListResVo.class);
            userListResVos.forEach(e -> e.setRoleName(userRoleBo.getRoleById(e.getRoleId()).getRoleName()));
            int skip = (pageNum - 1) * pageSize;
            resPageDTO.setTotalCount(userListResVos.size());
            resPageDTO.setList(userListResVos.stream().skip(skip).limit(pageSize).collect(Collectors.toList()));
        }
        return resPageDTO;
    }

    @Override
    public void updateRole(Long id, Long roleId) {
        UserDo userDo = selectByPrimaryKey(id);
        if (ObjectUtils.isEmpty(userDo)) {
            throw new GlobalException(RES_DATA_NOT_EXIST, "用户信息不存在");
        }
        userDo.setRoleId(roleId);

        update(userDo);
    }
}