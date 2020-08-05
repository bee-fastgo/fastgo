package com.bee.team.fastgo.service.user.impl;//package com.bee.team.fastgo.service.user.impl;

import com.alibaba.lava.base.AbstractLavaBoImpl;
import com.bee.team.fastgo.mapper.UserPermissionDoMapperExt;
import com.bee.team.fastgo.model.*;
import com.bee.team.fastgo.service.user.UserPermissionBo;
import com.bee.team.fastgo.service.user.UserRoleBo;
import com.bee.team.fastgo.service.user.UserRolePermissionBo;
import com.bee.team.fastgo.vo.user.AddPermissionReqVo;
import com.bee.team.fastgo.vo.user.ListPermissionResVo;
import com.bee.team.fastgo.vo.user.UpdatePermissionReqVo;
import com.spring.simple.development.core.annotation.base.NoApiMethod;
import com.spring.simple.development.core.component.mvc.BaseSupport;
import com.spring.simple.development.core.component.mvc.page.ResPageDTO;
import com.spring.simple.development.support.exception.GlobalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.spring.simple.development.support.exception.ResponseCode.RES_DATA_EXIST;
import static com.spring.simple.development.support.exception.ResponseCode.RES_DATA_NOT_EXIST;

/**
 * @author xqx
 * @date 2020/7/17
 * @desc 权限管理
 **/
@Service
public class UserPermissionBoImpl extends AbstractLavaBoImpl<UserPermissionDo, UserPermissionDoMapperExt, UserPermissionDoExample> implements UserPermissionBo {
    @Autowired
    private BaseSupport baseSupport;
    @Autowired
    private UserRoleBo userRoleBo;
    @Autowired
    private UserRolePermissionBo userRolePermissionBo;

    @Autowired
    @NoApiMethod
    public void setBaseMapper(UserPermissionDoMapperExt mapper) {
        setMapper(mapper);
    }

    @Override
    public ResPageDTO listPermissions(Integer pageNum, Integer pageSize) {
        // 设置默认的分页参数
        ResPageDTO resPageDTO = new ResPageDTO();
        resPageDTO.setPageNum(pageNum);
        resPageDTO.setPageSize(pageSize);
        resPageDTO.setList(null);
        resPageDTO.setTotalCount(0);

        // 获取所有的权限信息
        List<UserPermissionDo> list = selectByExample(new UserPermissionDoExample());

        // 封装数据
        if (!CollectionUtils.isEmpty(list)) {
            List<ListPermissionResVo> listPermissionResVos = baseSupport.listCopy(list, ListPermissionResVo.class);

            // 获取所有的根权限信息
            List<ListPermissionResVo> rootPermissions = listPermissionResVos.stream().filter(e -> ObjectUtils.isEmpty(e.getParentPermissionId())).collect(Collectors.toList());
            int skip = (pageNum - 1) * pageSize;
            // 对根结果集进行分页，再将结果集遍历成树
            resPageDTO.setList(getTree(rootPermissions.stream().skip(skip).limit(pageSize).collect(Collectors.toList()), listPermissionResVos));
            resPageDTO.setTotalCount(rootPermissions.size());
        }
        return resPageDTO;
    }

    @Override
    public List<ListPermissionResVo> getUserBindPermissionList(List<Long> permissionIds) {
        // 没有绑定的权限就返回空
        if (CollectionUtils.isEmpty(permissionIds)) {
            return null;
        }
        UserPermissionDoExample example = new UserPermissionDoExample();
        example.createCriteria().andIdIn(permissionIds);
        List<UserPermissionDo> list = selectByExample(example);
        return baseSupport.listCopy(list, ListPermissionResVo.class);
    }

    @Override
    public List<ListPermissionResVo> getAllPermissionList() {
        // 获取所有的权限信息
        List<UserPermissionDo> list = selectByExample(new UserPermissionDoExample());
        // 封装数据
        if (!CollectionUtils.isEmpty(list)) {
            List<ListPermissionResVo> listPermissionResVos = baseSupport.listCopy(list, ListPermissionResVo.class);
            // 获取所有的根权限信息
            List<ListPermissionResVo> rootPermissions = listPermissionResVos.stream().filter(e -> ObjectUtils.isEmpty(e.getParentPermissionId())).collect(Collectors.toList());
            return getTree(rootPermissions, listPermissionResVos);
            // 对根结果集进行分页，再将结果集遍历成树
        }
        return null;
    }

    @Override
    public void insertPermission(AddPermissionReqVo addPermissionReqVo) {
        // 权限key不能重复
        UserPermissionDoExample example = new UserPermissionDoExample();
        example.createCriteria().andPermissionKeyEqualTo(addPermissionReqVo.getPermissionKey());
        if (countByExample(example) > 0) {
            throw new GlobalException(RES_DATA_EXIST, "权限key不能重复");
        }

        UserPermissionDo permissionDo = baseSupport.objectCopy(addPermissionReqVo, UserPermissionDo.class);
        permissionDo.setParentPermissionId(Long.parseLong(addPermissionReqVo.getParentPermissionId()));

        int result = insert(permissionDo);

        // 如果添加成功就自定与admin角色进行绑定
        if (result > 0) {
            // 获取角色为admin的角色id
            UserRoleDoExample userRoleDoExample = new UserRoleDoExample();
            userRoleDoExample.createCriteria().andRoleNameEqualTo("admin");
            UserRoleDo userRoleDo = userRoleBo.getRoleByCondition(userRoleDoExample);
            if (ObjectUtils.isEmpty(userRoleDo)) {
                return;
            }
            long roleId = userRoleDo.getId();

            // 获取新添加的权限的id
            example = new UserPermissionDoExample();
            example.createCriteria().andPermissionKeyEqualTo(addPermissionReqVo.getPermissionKey()).andPermissionNameEqualTo(addPermissionReqVo.getPermissionName());
            List<UserPermissionDo> list = selectByExample(example);
            if (CollectionUtils.isEmpty(list)) {
                return;
            }
            long permissionId = list.get(0).getId();
            // 绑定角色与权限的关系
            userRolePermissionBo.addRolePermission(roleId, permissionId);
        }
    }

    @Override
    public void updatePermission(UpdatePermissionReqVo updatePermissionReqVo) {
        // 权限key不能重复
        UserPermissionDoExample example = new UserPermissionDoExample();
        example.createCriteria().andPermissionKeyEqualTo(updatePermissionReqVo.getPermissionKey())
                .andIdNotEqualTo(updatePermissionReqVo.getId());
        if (countByExample(example) > 0) {
            throw new GlobalException(RES_DATA_EXIST, "权限key不能重复");
        }
        UserPermissionDo permissionDo = baseSupport.objectCopy(updatePermissionReqVo, UserPermissionDo.class);
        update(permissionDo);
    }

    @Override
    public void deletePermission(Long id) {
        UserPermissionDo userPermissionDo = selectByPrimaryKey(id);
        if (ObjectUtils.isEmpty(userPermissionDo)) {
            throw new GlobalException(RES_DATA_NOT_EXIST, "权限信息不存在");
        }
        // 删除权限的时候判定是否有子集，如果有就将子集以及子集的子集全部删除
        updateChildren(userPermissionDo);
    }

    private void updateChildren(UserPermissionDo userPermissionDo) {
        // 删除权限
        userPermissionDo.setIsDeleted("y");
        update(userPermissionDo);

        // 角色和权限解绑
        // 获取所有的绑定关系列表
        UserRolePermissionDoExample userRolePermissionDoExample = new UserRolePermissionDoExample();
        userRolePermissionDoExample.createCriteria().andPermissionIdEqualTo(userPermissionDo.getId());
        List<UserRolePermissionDo> userRolePermissionDos = userRolePermissionBo.getRolePermissionListByCondition(userRolePermissionDoExample);
        // 解绑
        if (!CollectionUtils.isEmpty(userRolePermissionDos)) {
            userRolePermissionBo.delRolePermission(userRolePermissionDos);
        }

        // 搜索子集
        UserPermissionDoExample example = new UserPermissionDoExample();
        example.createCriteria().andParentPermissionIdEqualTo(userPermissionDo.getId());
        List<UserPermissionDo> list = selectByExample(example);
        // 如果子集不为空就递归
        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(e -> updateChildren(e));
        }
    }


    private List<ListPermissionResVo> getTree(List<ListPermissionResVo> rootPermissions, List<ListPermissionResVo> listPermissionResVos) {
        // 将结果集遍历成树形结构
        List<ListPermissionResVo> list = new ArrayList<>();
        rootPermissions.forEach(e -> {
            list.add(setChildren(e, listPermissionResVos));
        });
        return list;
    }

    private ListPermissionResVo setChildren(ListPermissionResVo listPermissionResVo, List<ListPermissionResVo> listPermissionResVos) {
        // 设置子集
        for (ListPermissionResVo e : listPermissionResVos) {
            if (ObjectUtils.isEmpty(e.getParentPermissionId())) {
                continue;
            }
            if (e.getParentPermissionId().equals(listPermissionResVo.getId())) {
                listPermissionResVo.addChild(e);
                setChildren(e, listPermissionResVos);
            }
        }
        return listPermissionResVo;
    }
}