package com.bee.team.fastgo.service.user.impl;//package com.bee.team.fastgo.service.user.impl;

import com.alibaba.lava.base.AbstractLavaBoImpl;
import com.bee.team.fastgo.mapper.UserPermissionDoMapperExt;
import com.bee.team.fastgo.model.UserPermissionDo;
import com.bee.team.fastgo.model.UserPermissionDoExample;
import com.bee.team.fastgo.service.user.UserPermissionBo;
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
    public void insertPermission(AddPermissionReqVo addPermissionReqVo) {
        // 权限key不能重复
        UserPermissionDoExample example = new UserPermissionDoExample();
        example.createCriteria().andPermissionKeyEqualTo(addPermissionReqVo.getPermissionKey());
        if (countByExample(example) > 0) {
            throw new GlobalException(RES_DATA_EXIST, "权限key不能重复");
        }

        UserPermissionDo permissionDo = baseSupport.objectCopy(addPermissionReqVo, UserPermissionDo.class);

        insert(permissionDo);
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
        userPermissionDo.setIsDeleted("y");
        update(userPermissionDo);
    }

    private List<ListPermissionResVo> getTree(List<ListPermissionResVo> rootPermissions, List<ListPermissionResVo> listPermissionResVos) {
        // 将结果集遍历成树形结构
        List<ListPermissionResVo> list = new ArrayList<>();
        rootPermissions.forEach(e -> {
            list.add(getChildren(e, listPermissionResVos));
        });
        return list;
    }

    private ListPermissionResVo getChildren(ListPermissionResVo listPermissionResVo, List<ListPermissionResVo> listPermissionResVos) {
        // 设置子集
        for (ListPermissionResVo e : listPermissionResVos) {
            if (ObjectUtils.isEmpty(e.getParentPermissionId())) {
                continue;
            }
            if (e.getParentPermissionId().equals(listPermissionResVo.getId())) {
                listPermissionResVo.addChild(e);
                getChildren(e, listPermissionResVos);
            }
        }
        return listPermissionResVo;
    }
}