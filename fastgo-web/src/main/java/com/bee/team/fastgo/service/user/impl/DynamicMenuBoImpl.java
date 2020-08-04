package com.bee.team.fastgo.service.user.impl;//package com.bee.team.fastgo.service.user.impl;

import com.alibaba.lava.base.AbstractLavaBoImpl;
import com.bee.team.fastgo.mapper.DynamicMenuDoMapperExt;
import com.bee.team.fastgo.model.DynamicMenuDo;
import com.bee.team.fastgo.model.DynamicMenuDoExample;
import com.bee.team.fastgo.service.user.DynamicMenuBo;
import com.bee.team.fastgo.vo.user.AddMenuReqVo;
import com.bee.team.fastgo.vo.user.MenuListResVo;
import com.bee.team.fastgo.vo.user.UpdateMenuReqVo;
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

        // 获取所有的菜单信息
        List<DynamicMenuDo> list = selectByExample(new DynamicMenuDoExample());

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
    public List<MenuListResVo> getUserBindMenus(List<Long> permissionIds) {
        if (CollectionUtils.isEmpty(permissionIds)) {
            return null;
        }
        DynamicMenuDoExample example = new DynamicMenuDoExample();
        example.createCriteria().andPermissionIdIn(permissionIds);
        example.setOrderByClause("orderNumber desc");
        List<DynamicMenuDo> list = selectByExample(example);
        return baseSupport.listCopy(list, MenuListResVo.class);
    }

    @Override
    public void insertMenu(AddMenuReqVo addMenuReqVo) {
        insert(baseSupport.objectCopy(addMenuReqVo, DynamicMenuDo.class));
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
        dynamicMenuDo.setIsDeleted("y");
        update(dynamicMenuDo);
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