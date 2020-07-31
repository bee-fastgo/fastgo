package com.bee.team.fastgo.service.server;

import com.alibaba.lava.base.LavaBo;
import com.bee.team.fastgo.model.ServerSoftwareProfileDo;
import com.bee.team.fastgo.model.ServerSoftwareProfileDoExample;
import com.bee.team.fastgo.vo.server.QuerySoftwareEnvironmentVo;
import com.bee.team.fastgo.vo.server.ReqAddEnvironmentVo;
import com.bee.team.fastgo.vo.server.ResSoftwareEnvironmentVo;
import com.spring.simple.development.core.component.mvc.page.ResPageDTO;

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

    /**
     * 通过code查询配置
      * @param softwareCode
     * @return {@link ServerSoftwareProfileDo}
     * @author jgz
     * @date 2020/7/27
     * @desc
     */
    ServerSoftwareProfileDo getServerSoftwareProfileBySoftwareCode(String softwareCode);

    /**
     * 创建环境
      * @param reqAddEnvironmentVo
     * @return
     * @author jgz
     * @date 2020/7/27
     * @desc
     */
    void createEnvironment(ReqAddEnvironmentVo reqAddEnvironmentVo);

    /**
     * 通过环境code移除软件环境
      * @param softwareCode
     * @return
     * @author jgz
     * @date 2020/7/28
     * @desc
     */
    void deleteServerSoftwareProfileBySoftwareCode(String softwareCode);

    /**
     * 查询已有环境的分页
      * @param querySoftwareEnvironmentVo
     * @return {@link ResPageDTO<ResSoftwareEnvironmentVo>}
     * @author jgz
     * @date 2020/7/28
     * @desc
     */
    ResPageDTO<ResSoftwareEnvironmentVo> getSoftwareEnvironmentByPage(QuerySoftwareEnvironmentVo querySoftwareEnvironmentVo);

    /**
     * 通过环境code修改环境配置
      * @param softwareCode
     * @param softwareConfig
     * @return
     * @author jgz
     * @date 2020/7/28
     * @desc
     */
    void updateSoftwareConfigBySoftwareCode(String softwareCode, String softwareConfig);

    /**
     * 通过软件名+版本+ip+port+dbName获取对应的mysql配置
      * @param ip
     * @param port
     * @param dataSourceName
     * @author jgz
     * @date 2020/7/31
     * @desc
     */
    ServerSoftwareProfileDo getMysqlProfileBySoftwareNameAndVersionIpAndPortAndDataSourceName(String softwareName,String version,String ip, String port, String dataSourceName);
}