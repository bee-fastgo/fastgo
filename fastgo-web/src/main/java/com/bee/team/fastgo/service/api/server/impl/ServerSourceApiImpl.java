package com.bee.team.fastgo.service.api.server.impl;

import com.bee.team.fastgo.model.ServerSourceDo;
import com.bee.team.fastgo.service.api.server.ServerSourceApi;
import com.bee.team.fastgo.service.server.ServerSourceBo;
import com.spring.simple.development.support.exception.GlobalException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.spring.simple.development.support.exception.ResponseCode.RES_PARAM_IS_EMPTY;

/**
 * @author xqx
 * @date 2020/7/28 14:44
 * @desc 软件资源
 **/
@Service
public class ServerSourceApiImpl implements ServerSourceApi {
    @Autowired
    private ServerSourceBo serverSourceBo;

    @Override
    public ServerSourceDo getSourceByNameAndVersion(String sourceName, String sourceVersion) {
        if (StringUtils.isEmpty(sourceName) || StringUtils.isEmpty(sourceVersion)) {
            throw new GlobalException(RES_PARAM_IS_EMPTY, "请求参数不能为空");
        }
        return serverSourceBo.getSourceByNameAndVersion(sourceName, sourceVersion);
    }

    @Override
    public List<ServerSourceDo> getSourcesList() {
        return serverSourceBo.getSourcesList();
    }
}
