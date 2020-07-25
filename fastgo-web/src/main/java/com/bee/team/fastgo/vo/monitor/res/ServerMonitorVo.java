package com.bee.team.fastgo.vo.monitor.res;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @desc 监控服务器各项指标状态响应类Vo
 * @auth hjs
 * @date 2020-07-25
 **/
@Data
@ApiModel(value = "ServerMonitorVo", description = "监控服务器各项指标状态响应类Vo")
public class ServerMonitorVo implements Serializable {

    /**
     * cpu
     */
    private List<ServerCpuLogVo> serverCpuLogVoList;

    /**
     * 负载均衡
     */
    private List<ServerLoadLogVo> serverLoadLogVoList;

    /**
     * 内存
     */
    private List<ServerMemoryLogVo> serverMemoryLogVoList;

    /**
     * 网络吞吐量
     */
    private List<ServerNetIOLogVo> serverNetIOLogVoList;
}
