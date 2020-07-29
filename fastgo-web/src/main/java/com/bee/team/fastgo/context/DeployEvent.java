package com.bee.team.fastgo.context;

import com.bee.team.fastgo.service.api.server.dto.req.SimpleDeployDTO;
import com.bee.team.fastgo.service.api.server.dto.req.VueDeployDTO;
import com.bee.team.fastgo.tools.deploy.DeployDTO;
import io.swagger.models.auth.In;
import org.springframework.context.ApplicationEvent;

/**
 * @author hs
 * @date 2020/7/29 11:24
 * @desc 项目部署事件vo
 **/
public class DeployEvent extends ApplicationEvent {

    private SimpleDeployDTO deployDTO;

    private VueDeployDTO vueDeployDTO;

    //1-前台部署 2-后台部署
    private Integer type;

    //项目id
    private  Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public VueDeployDTO getVueDeployDTO() {
        return vueDeployDTO;
    }

    public void setVueDeployDTO(VueDeployDTO vueDeployDTO) {
        this.vueDeployDTO = vueDeployDTO;
    }

    public SimpleDeployDTO getDeployDTO() {
        return deployDTO;
    }

    public void setDeployDTO(SimpleDeployDTO deployDTO) {
        this.deployDTO = deployDTO;
    }

    public DeployEvent(Object source, SimpleDeployDTO deployDTO, VueDeployDTO vueDeployDTO, Integer type, Integer id) {
        super(source);
        this.deployDTO = deployDTO;
        this.vueDeployDTO = vueDeployDTO;
        this.type = type;
        this.id = id;
    }
}
