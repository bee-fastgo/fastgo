package com.bee.team.fastgo.service.user.impl;//package com.bee.team.fastgo.service.user.impl;

import com.alibaba.lava.base.AbstractLavaBoImpl;
import com.bee.team.fastgo.mapper.DynamicMenuDoMapperExt;
import com.bee.team.fastgo.model.DynamicMenuDo;
import com.bee.team.fastgo.model.DynamicMenuDoExample;
import com.bee.team.fastgo.service.user.DynamicMenuBo;
import com.bee.team.fastgo.vo.user.AddMenuReqVo;
import com.bee.team.fastgo.vo.user.MenuListResVo;
import com.bee.team.fastgo.vo.user.UpdateMenuReqVo;
import com.bee.team.fastgo.vo.user.UserMenuResVo;
import com.spring.simple.development.core.annotation.base.NoApiMethod;
import com.spring.simple.development.core.component.mvc.BaseSupport;
import com.spring.simple.development.core.component.mvc.page.ResPageDTO;
import com.spring.simple.development.support.exception.GlobalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.spring.simple.development.support.exception.ResponseCode.RES_DATA_NOT_EXIST;

/**
 * @author xqx
 * @date 2020/7/17
 * @desc 动态菜单管理
 **/
@Service
public class DynamicMenuBoImpl extends AbstractLavaBoImpl<DynamicMenuDo, DynamicMenuDoMapperExt, DynamicMenuDoExample> implements DynamicMenuBo {
    @Autowired
    private BaseSupport baseSupport;

    @Autowired
    @NoApiMethod
    public void setBaseMapper(DynamicMenuDoMapperExt mapper) {
        setMapper(mapper);
    }

    @Override
    public ResPageDTO listMenus(Integer pageNum, Integer pageSize) {
        // 设置默认的分页参数
        ResPageDTO resPageDTO = new ResPageDTO();
        resPageDTO.setPageNum(pageNum);
        resPageDTO.setPageSize(pageSize);
        resPageDTO.setList(null);
        resPageDTO.setTotalCount(0);
        DynamicMenuDoExample example = new DynamicMenuDoExample();
        example.setOrderByClause("orderNumber asc");

        // 获取所有的菜单信息
        List<DynamicMenuDo> list = selectByExample(example);

        // 封装数据
        if (!CollectionUtils.isEmpty(list)) {
            List<MenuListResVo> listResVos = baseSupport.listCopy(list, MenuListResVo.class);

            // 获取所有的根菜单
            List<MenuListResVo> rootMenus = listResVos.stream().filter(e -> ObjectUtils.isEmpty(e.getParentMenuId())).collect(Collectors.toList());

            int skip = (pageNum - 1) * pageSize;
            // 对根结果集进行分页，再将结果集遍历成树
            resPageDTO.setList(getTree(rootMenus.stream().skip(skip).limit(pageSize).collect(Collectors.toList()), listResVos));
            resPageDTO.setTotalCount(rootMenus.size());
        }
        return resPageDTO;
    }

    @Override
    public List<UserMenuResVo> getUserBindMenus(List<Long> permissionIds) {
        if (CollectionUtils.isEmpty(permissionIds)) {
            return null;
        }
        DynamicMenuDoExample example = new DynamicMenuDoExample();
        example.createCriteria().andPermissionIdIn(permissionIds);
        example.setOrderByClause("orderNumber asc");
        List<DynamicMenuDo> list = selectByExample(example);
        List<UserMenuResVo> userMenuResVos = baseSupport.listCopy(list, UserMenuResVo.class);
        for (UserMenuResVo userMenuResVo : userMenuResVos) {
            userMenuResVo.setTitle(userMenuResVo.getMenuName());
            userMenuResVo.setPath(userMenuResVo.getMenuUrl());
            if (StringUtils.isEmpty(userMenuResVo.getMenuUrl()) || StringUtils.isEmpty(userMenuResVo.getMenuUrl().substring(1))) {
                userMenuResVo.setName("");
            } else if (userMenuResVo.getMenuUrl().substring(0, 1).equals("/")) {
                userMenuResVo.setName(userMenuResVo.getMenuUrl().substring(1).substring(0, 1).toUpperCase() + userMenuResVo.getMenuUrl().substring(2));
            } else {
                userMenuResVo.setName(userMenuResVo.getMenuUrl().substring(0, 1).toUpperCase() + userMenuResVo.getMenuUrl().substring(1));
            }
        }

        // 获取根
        List<UserMenuResVo> rootMenus = userMenuResVos.stream().filter(e -> ObjectUtils.isEmpty(e.getParentMenuId())).collect(Collectors.toList());

        // 设置路由
        List<UserMenuResVo> menuComponents = new ArrayList<>();
        rootMenus.forEach(e -> {
            e.setComponent("Layout");
            menuComponents.add(setComponent(e, userMenuResVos));
        });
        return menuComponents;
    }

    private UserMenuResVo setComponent(UserMenuResVo rootMenu, List<UserMenuResVo> userMenuResVos) {
        for (UserMenuResVo userMenuResVo : userMenuResVos) {
            if (ObjectUtils.isEmpty(userMenuResVo.getParentMenuId())) {
                continue;
            }
            if (userMenuResVo.getParentMenuId().equals(rootMenu.getId())) {
                if (rootMenu.getComponent().equals("Layout")) {
                    userMenuResVo.setComponent(rootMenu.getPath() + "/" + userMenuResVo.getPath());
                } else {
                    userMenuResVo.setComponent(rootMenu.getComponent() + "/" + userMenuResVo.getPath());
                }
                rootMenu.addChildren(userMenuResVo);
                setComponent(userMenuResVo, userMenuResVos);
            }
        }
        return rootMenu;
    }

    @Override
    public void insertMenu(AddMenuReqVo addMenuReqVo) {
        DynamicMenuDo dynamicMenuDo = baseSupport.objectCopy(addMenuReqVo, DynamicMenuDo.class);
        if (!StringUtils.isEmpty(dynamicMenuDo.getParentMenuId())) {
            dynamicMenuDo.setParentMenuId(Long.parseLong(addMenuReqVo.getParentMenuId()));
        }
        insert(dynamicMenuDo);
    }

    @Override
    public void updateMenu(UpdateMenuReqVo updateMenuReqVo) {
        update(baseSupport.objectCopy(updateMenuReqVo, DynamicMenuDo.class));
    }

    @Override
    public void delMenu(Long id) {
        DynamicMenuDo dynamicMenuDo = selectByPrimaryKey(id);
        if (ObjectUtils.isEmpty(dynamicMenuDo)) {
            throw new GlobalException(RES_DATA_NOT_EXIST, "菜单信息不存在");
        }
        // 删除子集
        updateChildren(dynamicMenuDo);
    }

    private void updateChildren(DynamicMenuDo dynamicMenuDo) {
        // 删除当前菜单
        dynamicMenuDo.setIsDeleted("y");
        update(dynamicMenuDo);

        // 搜索子集
        DynamicMenuDoExample example = new DynamicMenuDoExample();
        example.createCriteria().andParentMenuIdEqualTo(dynamicMenuDo.getId());
        List<DynamicMenuDo> list = selectByExample(example);
        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(e -> updateChildren(e));
        }
    }

    private List<MenuListResVo> getTree(List<MenuListResVo> rootMenus, List<MenuListResVo> listResVos) {
        // 将结果集遍历成树形结构
        List<MenuListResVo> list = new ArrayList<>();
        rootMenus.forEach(e -> {
            list.add(getChildren(e, listResVos));
        });
        return list;
    }

    private MenuListResVo getChildren(MenuListResVo rootMenu, List<MenuListResVo> listResVos) {
        // 设置子集
        for (MenuListResVo e : listResVos) {
            if (ObjectUtils.isEmpty(e.getParentMenuId())) {
                continue;
            }
            if (e.getParentMenuId().equals(rootMenu.getId())) {
                rootMenu.addChildren(e);
                getChildren(e, listResVos);
            }
        }
        return rootMenu;
    }

}