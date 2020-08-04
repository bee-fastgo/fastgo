package com.bee.team.fastgo.service.user.impl;//package com.bee.team.fastgo.service.user.impl;

import com.alibaba.lava.base.AbstractLavaBoImpl;
import com.bee.team.fastgo.mapper.UserRoleDoMapperExt;
import com.bee.team.fastgo.model.UserRoleDo;
import com.bee.team.fastgo.model.UserRoleDoExample;
import com.bee.team.fastgo.service.user.UserRoleBo;
import com.bee.team.fastgo.vo.user.AddRoleReqVo;
import com.bee.team.fastgo.vo.user.ListRoleResVo;
import com.bee.team.fastgo.vo.user.UpdateRoleReqVo;
import com.spring.simple.development.core.annotation.base.NoApiMethod;
import com.spring.simple.development.core.component.mvc.BaseSupport;
import com.spring.simple.development.core.component.mvc.page.ResPageDTO;
import com.spring.simple.development.support.exception.GlobalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

import static com.spring.simple.development.support.exception.ResponseCode.RES_DATA_EXIST;
import static com.spring.simple.development.support.exception.ResponseCode.RES_DATA_NOT_EXIST;

/**
 * @author xqx
 * @date 2020/7/17
 * @desc 角色管理
 **/
@Service
public class UserRoleBoImpl extends AbstractLavaBoImpl<UserRoleDo, UserRoleDoMapperExt, UserRoleDoExample> implements UserRoleBo {
    @Autowired
    private BaseSupport baseSupport;

    @Autowired
    @NoApiMethod
    public void setBaseMapper(UserRoleDoMapperExt mapper) {
        setMapper(mapper);
    }

    @Override
    public ResPageDTO ListRoles(Integer pageNum, Integer pageSize) {
        // 设置默认的分页参数
        ResPageDTO resPageDTO = new ResPageDTO();
        resPageDTO.setPageNum(pageNum);
        resPageDTO.setPageSize(pageSize);
        resPageDTO.setList(null);
        resPageDTO.setTotalCount(0);

        // 获取所有的角色信息
        List<UserRoleDo> list = selectByExample(new UserRoleDoExample());

        // 封装数据
        if (!CollectionUtils.isEmpty(list)) {
            List<ListRoleResVo> listRoleResVos = baseSupport.listCopy(list, ListRoleResVo.class);
            int skip = (pageNum - 1) * pageSize;
            resPageDTO.setList(listRoleResVos.stream().skip(skip).limit(pageSize).collect(Collectors.toList()));
            resPageDTO.setTotalCount(listRoleResVos.size());
        }
        return resPageDTO;
    }

    @Override
    public UserRoleDo getRoleById(Long id) {
        return selectByPrimaryKey(id);
    }

    @Override
    public void insertRole(AddRoleReqVo addRoleReqVo) {
        // 角色名不能重复
        UserRoleDoExample example = new UserRoleDoExample();
        example.createCriteria().andRoleNameEqualTo(addRoleReqVo.getRoleName());

        if (countByExample(example) > 0) {
            throw new GlobalException(RES_DATA_EXIST, "角色名已存在");
        }
        UserRoleDo userRoleDo = baseSupport.objectCopy(addRoleReqVo, UserRoleDo.class);

        insert(userRoleDo);
    }

    @Override
    public void updateRole(UpdateRoleReqVo updateRoleReqVo) {
        // 角色名不能重复
        UserRoleDoExample example = new UserRoleDoExample();
        example.createCriteria().andRoleNameEqualTo(updateRoleReqVo.getRoleName())
                .andIdNotEqualTo(updateRoleReqVo.getId());
        if (countByExample(example) > 0) {
            throw new GlobalException(RES_DATA_EXIST, "角色名已存在");
        }

        // 根据id获取角色信息
        UserRoleDo userRoleDo = baseSupport.objectCopy(updateRoleReqVo, UserRoleDo.class);
        update(userRoleDo);
    }

    @Override
    public void deleteRole(Long id) {
        UserRoleDo userRoleDo = selectByPrimaryKey(id);
        if (ObjectUtils.isEmpty(userRoleDo)) {
            throw new GlobalException(RES_DATA_NOT_EXIST, "角色信息不存在");
        }
        userRoleDo.setIsDeleted("y");
        update(userRoleDo);
    }
}