package com.bee.team.fastgo.service.server.impl;

import com.alibaba.lava.base.AbstractLavaBoImpl;
import com.bee.team.fastgo.common.SoftwareEnum;
import com.bee.team.fastgo.mapper.ServerScriptDoMapperExt;
import com.bee.team.fastgo.model.ServerScriptDo;
import com.bee.team.fastgo.model.ServerScriptDoExample;
import com.bee.team.fastgo.service.server.ServerScriptBo;
import com.bee.team.fastgo.vo.server.QueryScriptVo;
import com.bee.team.fastgo.vo.server.ReqAddScriptVo;
import com.bee.team.fastgo.vo.server.ResScriptVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spring.simple.development.core.annotation.base.NoApiMethod;
import com.spring.simple.development.core.component.mvc.BaseSupport;
import com.spring.simple.development.core.component.mvc.page.ResPageDTO;
import com.spring.simple.development.support.exception.GlobalException;
import com.spring.simple.development.support.exception.ResponseCode;
import com.spring.simple.development.support.utils.RandomUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
public class ServerScriptBoImpl extends AbstractLavaBoImpl<ServerScriptDo, ServerScriptDoMapperExt, ServerScriptDoExample> implements ServerScriptBo {

    @Autowired
    private BaseSupport baseSupport;

    @Autowired
    @NoApiMethod
    public void setBaseMapper(ServerScriptDoMapperExt mapper) {
        setMapper(mapper);
    }



    @Override
    public void saveScript(ReqAddScriptVo reqAddScriptVo) {
        for (SoftwareEnum value : SoftwareEnum.values()) {
            //如果字典中存在该软件则进行脚本的保存
            if (value.name().toLowerCase().equals(reqAddScriptVo.getSoftwareName())){
                ServerScriptDo serverScriptDo = baseSupport.objectCopy(reqAddScriptVo, ServerScriptDo.class);
                serverScriptDo.setScriptKey(RandomUtil.randomStr(16));
                insert(serverScriptDo);
                return;
            }
        }
        throw new GlobalException(ResponseCode.RES_PARAM_IS_EMPTY, "不支持的软件类型");
    }


    @Override
    public ResPageDTO<ResScriptVo> getScriptByPage(QueryScriptVo queryScriptVo) {
        ServerScriptDoExample serverScriptDoExample = new ServerScriptDoExample();
        if(StringUtils.isNotEmpty(queryScriptVo.getSoftwareName())) {
            if(Stream.of(SoftwareEnum.values()).map(SoftwareEnum::name).map(String::toLowerCase).anyMatch(s -> s.equals(queryScriptVo.getSoftwareName()))){
                serverScriptDoExample.createCriteria().andSoftwareNameEqualTo(queryScriptVo.getSoftwareName());
            } else {
                throw new GlobalException(ResponseCode.RES_PARAM_IS_EMPTY, "不支持的软件类型");
            }
        }

        int startNo = queryScriptVo.getStartPage();
        int rowCount = queryScriptVo.getPageSize();
        PageHelper.startPage(startNo, rowCount);
        List<ServerScriptDo> serverScriptDoList = selectByExample(serverScriptDoExample);

        return baseSupport.pageCopy(new PageInfo(serverScriptDoList),ResScriptVo.class);
    }
}