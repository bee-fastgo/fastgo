package com.bee.team.fastgo.service.api.server.impl;

import com.bee.team.fastgo.model.ServerSourceDo;
import com.bee.team.fastgo.service.api.server.SourceApi;
import com.bee.team.fastgo.service.api.server.dto.res.ResSourceListDTO;
import com.bee.team.fastgo.service.api.server.dto.res.SourceDTO;
import com.bee.team.fastgo.service.server.ServerBo;
import com.bee.team.fastgo.service.server.ServerSourceBo;
import com.bee.team.fastgo.vo.server.ServerVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author jgz
 * @date 2020/7/28
 * @desc 软件源接口实现
 **/
@Service
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

            // 获取所有软件源的信息
            List<ServerSourceDo> sourcesList = serverSourceBo.getSourcesList();
            List<SourceDTO> collect = sourcesList.stream().map(serverSourceDo -> {
                SourceDTO sourceDTO = new SourceDTO();
                sourceDTO.setSoftwareName(serverSourceDo.getSoftwareName());
                sourceDTO.setVersion(serverSourceDo.getSourceVersion());
                return sourceDTO;
            }).collect(Collectors.toList());
            resSourceListDTO.setSourceDTOList(collect);

            return resSourceListDTO;
        }).collect(Collectors.toList());
    }
}
