package com.bee.team.fastgo.service.api.server.impl;

import com.bee.team.fastgo.service.api.dto.server.req.ReqCreateSoftwareDTO;
import com.bee.team.fastgo.service.api.dto.server.res.ResCreateSoftwareDTO;
import com.bee.team.fastgo.service.api.server.SoftwareProfileApi;
import org.springframework.stereotype.Service;

/**
 * @author jgz
 * @date 2020/7/25
 * @desc 软件环境api实现
 **/
@Service
public class SoftwareProfileApiImpl implements SoftwareProfileApi {



    @Override
    public ResCreateSoftwareDTO createSoftwareEnvironment(ReqCreateSoftwareDTO reqCreateSoftwareDTO) {

        // TODO 1.查询是否存在该配置?


        // TODO 2.判断是否支持该软件?

        // TODO 3.该服务器是否处于托管中?

        // TODO 4.为该服务器安装软件,并拿到脚本执行的key

        // TODO 5.将该软件的信息保存到数据库

        // TODO 6.返回配置

        return null;
    }
}
