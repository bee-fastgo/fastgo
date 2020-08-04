package com.bee.team.fastgo.service.server;

import com.alibaba.lava.base.LavaBo;
import com.bee.team.fastgo.model.AlertInfoDo;
import com.bee.team.fastgo.model.AlertInfoDoExample;
import com.bee.team.fastgo.vo.server.QueryAlertInfoVo;
import com.bee.team.fastgo.vo.server.ResAlertInfoVo;
import com.spring.simple.development.core.component.mvc.page.ResPageDTO;

public interface AlertInfoBo extends LavaBo<AlertInfoDo, AlertInfoDoExample> {

    /**
     * 获取告警分页
      * @param queryAlertInfoVo
     * @return {@link ResPageDTO<ResAlertInfoVo>}
     * @author jgz
     * @date 2020/8/4
     * @desc
     */
    ResPageDTO<ResAlertInfoVo> getAlertInfoByPage(QueryAlertInfoVo queryAlertInfoVo);

    /**
     * 插入告警消息
      * @param alertInfoDo
     * @return
     * @author jgz
     * @date 2020/8/4
     * @desc
     */
    void insertAlertInfo(AlertInfoDo alertInfoDo);
}