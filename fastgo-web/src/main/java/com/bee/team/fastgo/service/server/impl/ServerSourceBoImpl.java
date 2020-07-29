package com.bee.team.fastgo.service.server.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.lava.base.AbstractLavaBoImpl;
import com.bee.team.fastgo.mapper.ServerSourceDoMapperExt;
import com.bee.team.fastgo.model.ServerSourceDo;
import com.bee.team.fastgo.model.ServerSourceDoExample;
import com.bee.team.fastgo.service.server.ServerSourceBo;
import com.bee.team.fastgo.vo.server.ResUpdateResourceVo;
import com.spring.simple.development.core.annotation.base.NoApiMethod;
import com.spring.simple.development.support.exception.GlobalException;
import com.spring.simple.development.support.utils.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

import static com.spring.simple.development.support.exception.ResponseCode.RES_DATA_EXIST;
import static com.spring.simple.development.support.exception.ResponseCode.RES_PARAM_IS_EMPTY;

@Service
public class ServerSourceBoImpl extends AbstractLavaBoImpl<ServerSourceDo, ServerSourceDoMapperExt, ServerSourceDoExample> implements ServerSourceBo {

    @Autowired
    @NoApiMethod
    public void setBaseMapper(ServerSourceDoMapperExt mapper) {
        setMapper(mapper);
    }

    @Override
    public void insertSource(ServerSourceDo serverSourceDo) {
        // 软件和版本号组合唯一的
        if (!ObjectUtils.isEmpty(getSourceByNameAndVersion(serverSourceDo.getSourceName(), serverSourceDo.getSourceVersion()))) {
            throw new GlobalException(RES_DATA_EXIST, serverSourceDo.getSourceName() + serverSourceDo.getSourceVersion() + "已存在");
        }

        String sourceCode = RandomUtils.getRandomStr(16);
        serverSourceDo.setSourceCode(sourceCode);
        insert(serverSourceDo);
    }

    @Override
    public ServerSourceDo getSourceBySourceCode(String sourceCode) {
        if (StringUtils.isEmpty(sourceCode)) {
            throw new GlobalException(RES_PARAM_IS_EMPTY, "资源标识不能为空");
        }
        ServerSourceDoExample example = new ServerSourceDoExample();
        example.createCriteria().andSourceCodeEqualTo(sourceCode).andIsDeletedEqualTo("n");
        List<ServerSourceDo> list = selectByExample(example);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }

        return list.get(0);
    }

    @Override
    public ServerSourceDo getSourceByNameAndVersion(String sourceName, String sourceVersion) {
        if (StringUtils.isEmpty(sourceName) || StringUtils.isEmpty(sourceVersion)) {
            throw new GlobalException(RES_PARAM_IS_EMPTY, "请求参数不能为空");
        }
        ServerSourceDoExample example = new ServerSourceDoExample();
        example.createCriteria().andIsDeletedEqualTo("n")
                .andSourceNameEqualTo(sourceName)
                .andSourceVersionEqualTo(sourceVersion);
        List<ServerSourceDo> list = selectByExample(example);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<ServerSourceDo> getSourcesList() {
        ServerSourceDoExample example = new ServerSourceDoExample();
        return selectByExample(example);
    }

    @Override
    public void updateSource(ResUpdateResourceVo resUpdateResourceVo) {

    }

    @Override
    public void deleteSource(String sourceCode) {
        if (StringUtils.isEmpty(sourceCode)) {
            throw new GlobalException(RES_PARAM_IS_EMPTY, "请求参数不能为空");
        }
        ServerSourceDoExample example = new ServerSourceDoExample();
        ServerSourceDo serverSourceDo = getSourceBySourceCode(sourceCode);
        serverSourceDo.setIsDeleted("y");
        update(serverSourceDo);
    }
}