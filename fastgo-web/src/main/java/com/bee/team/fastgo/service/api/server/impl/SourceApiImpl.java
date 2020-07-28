package com.bee.team.fastgo.service.api.server.impl;

import com.bee.team.fastgo.service.api.server.SourceApi;
import com.bee.team.fastgo.service.api.server.dto.res.ResSourceListDTO;
import com.bee.team.fastgo.service.server.ServerBo;
import com.bee.team.fastgo.service.server.ServerSourceBo;
import com.bee.team.fastgo.vo.server.ServerVo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author jgz
 * @date 2020/7/28
 * @desc 软件源接口实现
 **/
public class SourceApiImpl implements SourceApi {

    @Autowired
    private ServerBo serverBo;

    @Autowired
    private ServerSourceBo serverSourceBo;

    @Override
    public List<ResSourceListDTO> getSourceList() {
        List<ServerVo> serverVos = serverBo.queryListServer();
        return serverVos.stream().map(serverVo -> {
            ResSourceListDTO resSourceListDTO = new ResSourceListDTO();
            resSourceListDTO.setIp(serverVo.getServerIp());
            //TODO 获取所有软件源的信息
//            resSourceListDTO.setSourceDTOList();
            return resSourceListDTO;
        }).collect(Collectors.toList());

    }
}
