package com.bee.team.fastgo.controller.user;//package com.bee.team.fastgo.controller.user;

import com.bee.team.fastgo.service.user.DynamicMenuBo;
import com.bee.team.fastgo.vo.user.AddMenuReqVo;
import com.bee.team.fastgo.vo.user.PageReqVo;
import com.bee.team.fastgo.vo.user.UpdateMenuReqVo;
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

import static com.spring.simple.development.support.exception.ResponseCode.RES_PARAM_IS_EMPTY;

/**
 * @author xqx
 * @date 2020/8/3 15:14
 * @desc 动态菜单管理
 **/
@Api(tags = "动态菜单管理")
@RestController
@RequestMapping("/menu")
public class DynamicMenuController {
    @Autowired
    private DynamicMenuBo dynamicMenuBo;

    @RequestMapping(value = "/listMenus", method = RequestMethod.POST)
    @ApiOperation(value = "菜单列表信息（不按条件查询）")
    @ValidHandler(key = "pageReqVo", value = PageReqVo.class, isReqBody = false)
    public ResBody listMenus(@RequestBody PageReqVo pageReqVo) {
        return new ResBody().buildSuccessResBody(dynamicMenuBo.listMenus(pageReqVo.getPageNum(), pageReqVo.getPageSize()));
    }

    @RequestMapping(value = "/insertMenu", method = RequestMethod.POST)
    @ApiOperation(value = "添加菜单信息")
    @ValidHandler(key = "addMenuReqVo", value = AddMenuReqVo.class, isReqBody = false)
    public ResBody insertMenu(@RequestBody AddMenuReqVo addMenuReqVo) {
        dynamicMenuBo.insertMenu(addMenuReqVo);
        return new ResBody().buildSuccessResBody();
    }

    @RequestMapping(value = "/updateMenu", method = RequestMethod.POST)
    @ApiOperation(value = "修改菜单信息")
    @ValidHandler(key = "updateMenuReqVo", value = UpdateMenuReqVo.class, isReqBody = false)
    public ResBody updateMenu(@RequestBody UpdateMenuReqVo updateMenuReqVo) {
        dynamicMenuBo.updateMenu(updateMenuReqVo);
        return new ResBody().buildSuccessResBody();
    }

    @RequestMapping(value = "/delMenu", method = RequestMethod.POST)
    @ApiOperation(value = "删除菜单信息")
    @ApiImplicitParam(name = "id", value = "菜单id", dataTypeClass = Long.class)
    public ResBody delMenu(@RequestBody Long id) {
        if (ObjectUtils.isEmpty(id)) {
            throw new GlobalException(RES_PARAM_IS_EMPTY, "菜单id不能为空");
        }
        dynamicMenuBo.delMenu(id);
        return new ResBody().buildSuccessResBody();
    }
}
