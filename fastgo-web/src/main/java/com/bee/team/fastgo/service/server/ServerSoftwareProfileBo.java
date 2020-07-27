package com.bee.team.fastgo.service.server;

import com.alibaba.lava.base.LavaBo;
import com.bee.team.fastgo.model.ServerSoftwareProfileDo;
import com.bee.team.fastgo.model.ServerSoftwareProfileDoExample;

public interface ServerSoftwareProfileBo extends LavaBo<ServerSoftwareProfileDo, ServerSoftwareProfileDoExample> {

    /**
     * 通过ip,软件名,版本查询是否存在该配置
      * @param ip
     * @param softwareName
     * @param version
     * @return {@link ServerSoftwareProfileDo}
     * @author jgz
     * @date 2020/7/27
     * @desc
     */
    ServerSoftwareProfileDo getServerSoftwareProfileByServerIpAndSoftwareNameAndVersion(String ip, String softwareName, String version);

    /**
     * 保存软件环境配置
      * @param serverSoftwareProfileDo
     * @return
     * @author jgz
     * @date 2020/7/27
     * @desc
     */
    void saveServerSoftwareProfile(ServerSoftwareProfileDo serverSoftwareProfileDo);
}