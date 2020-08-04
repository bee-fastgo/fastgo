package com.bee.team.fastgo.service.user;//package com.bee.team.fastgo.service.user;

import com.alibaba.lava.base.LavaBo;
import com.bee.team.fastgo.model.DynamicMenuDo;
import com.bee.team.fastgo.model.DynamicMenuDoExample;
import com.bee.team.fastgo.vo.user.AddMenuReqVo;
import com.bee.team.fastgo.vo.user.MenuListResVo;
import com.bee.team.fastgo.vo.user.UpdateMenuReqVo;
import com.spring.simple.development.core.component.mvc.page.ResPageDTO;

import java.util.List;

/**
 * @author xqx
 * @date 2020/7/17
 * @desc 动态菜单管理
 **/
public interface DynamicMenuBo extends LavaBo<DynamicMenuDo, DynamicMenuDoExample> {
    /**
     * 获取菜单列表
     *
     * @param pageNum
     * @param pageSize
     * @return {@link ResPageDTO}
     * @author xqx
     * @date 2020/8/3
     * @desc 获取菜单列表
     */
    ResPageDTO listMenus(Integer pageNum, Integer pageSize);

    /**
     * 根据权限id获取用户绑定的菜单列表
     *
     * @param permissionIds
     * @return {@link List< MenuListResVo>}
     * @author xqx
     * @date 2020/8/4
     * @desc 根据权限id获取用户绑定的菜单列表
     */
    List<MenuListResVo> getUserBindMenus(List<Long> permissionIds);

    /**
     * 添加菜单
     *
     * @param addMenuReqVo
     * @return
     * @author xqx
     * @date 2020/8/3
     * @desc 添加菜单
     */
    void insertMenu(AddMenuReqVo addMenuReqVo);

    /**
     * 修改菜单
     *
     * @param updateMenuReqVo
     * @return
     * @author xqx
     * @date 2020/8/3
     * @desc 修改菜单
     */
    void updateMenu(UpdateMenuReqVo updateMenuReqVo);

    /**
     * 删除菜单
     *
     * @param id
     * @return
     * @author xqx
     * @date 2020/8/3
     * @desc 删除菜单
     */
    void delMenu(Long id);
}