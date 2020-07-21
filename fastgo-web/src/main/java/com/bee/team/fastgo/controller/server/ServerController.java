package com.bee.team.fastgo.controller.server;

import com.bee.team.fastgo.service.server.ServerBo;
import com.spring.simple.development.core.component.mvc.res.ResBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: luke
 * @time: 2020/7/21 0021 8:51
 */
@RestController("/server")
public class ServerController {

    @Autowired
    private ServerBo serverBo;

}
