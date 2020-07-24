package com.bee.team.fastgo.controller.server;

import com.bee.team.fastgo.hander.SimpleExecutorCmd;
import com.bee.team.fastgo.job.core.glue.GlueTypeEnum;
import com.bee.team.fastgo.service.server.ServerBo;
import com.bee.team.fastgo.vo.server.AddServerVo;
import com.bee.team.fastgo.vo.server.ModifyServerVo;
import com.bee.team.fastgo.vo.server.QueryServerVo;
import com.bee.team.fastgo.vo.server.ServerVo;
import com.spring.simple.development.core.annotation.base.ValidHandler;
import com.spring.simple.development.core.component.mvc.page.ResPageDTO;
import com.spring.simple.development.core.component.mvc.res.ResBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @description:
 * @author: luke
 * @time: 2020/7/21 0021 8:51
 */
@Api(tags = "服务器相关")
@RestController("/server")
public class ServerController {

    @Autowired
    private ServerBo serverBo;

    /**
     * @return com.spring.simple.development.core.component.mvc.res.ResBody
     * @Author luke
     * @Description 添加server
     * @Date 9:26 2020/7/21 0021
     * @Param [addServerVo]
     **/
    @RequestMapping(value = "/addServer", method = RequestMethod.POST)
    @ValidHandler(key = "addServerVo", value = AddServerVo.class)
    @ApiOperation(value = "添加服务器")
    @ApiImplicitParam(name = "addServerVo", value = "添加服务器Vo对象", dataTypeClass = AddServerVo.class)
    public ResBody<Void> addServer(AddServerVo addServerVo) {
        serverBo.addServerDo(addServerVo);
        return new ResBody().buildSuccessResBody();
    }

    /**
     * @return com.spring.simple.development.core.component.mvc.res.ResBody
     * @Author luke
     * @Description 修改server
     * @Date 9:26 2020/7/21 0021
     * @Param [modifyServerVo]
     **/
    @RequestMapping(value = "/modifyServer", method = RequestMethod.POST)
    @ValidHandler(key = "modifyServer", value = ModifyServerVo.class)
    @ApiOperation(value = "修改服务器")
    @ApiImplicitParam(name = "modifyServerVo", value = "修改服务器Vo对象", dataTypeClass = ModifyServerVo.class)
    public ResBody<Void> addServer(ModifyServerVo modifyServerVo) {
        serverBo.modifyServerDo(modifyServerVo);
        return new ResBody().buildSuccessResBody();
    }

    @RequestMapping(value = "/getServerList", method = RequestMethod.POST)
    @ValidHandler(key = "queryServerVo", value = QueryServerVo.class)
    @ApiOperation(value = "查询服务器(分页)")
    @ApiImplicitParam(name = "queryServerVo", value = "服务器查询对象", dataTypeClass = QueryServerVo.class)
    public ResBody<ServerVo> addServer(QueryServerVo queryServerVo) {
        ResPageDTO resPageDTO = serverBo.queryPageServer(queryServerVo);
        return new ResBody().buildSuccessResBody(resPageDTO);
    }


    @GetMapping(value = "/exec")
    @ApiOperation(value = "执行测试脚本")
    public ResBody<Void> exec(){
        SimpleExecutorCmd.executorCmd(GlueTypeEnum.GLUE_SHELL, "#!/bin/bash\n" +
                "\n" +
                "software=redis\n" +
                "version=5.0.8\n" +
                "targetPath=/data/fastgo/software\n" +
                "\n" +
                "# 安装wget\n" +
                "if [[ -z $(rpm -qa | grep wget) ]];then\n" +
                "\tyum -y install wget\n" +
                "fi\n" +
                "\n" +
                "# 停止redis\n" +
                "for pid in $(ps -A | grep $software | awk '{print $1}')\n" +
                "do\n" +
                "\tkill -9 $pid\n" +
                "done\n" +
                "\n" +
                "\n" +
                "# 下载redis\n" +
                "wget $(pwd) \t\n" +
                "\n" +
                "# 如果基本文件夹不存在,则创建\n" +
                "if [[ ! -d \"$targetPath\" ]];then\n" +
                "\tmkdir -p $targetPath\n" +
                "fi\n" +
                "\n" +
                "# 解压文件\n" +
                "tar -zxf $(pwd)/$software-$version.tar.gz -C $targetPath\n" +
                "\n" +
                "# 启动\n" +
                "$targetPath/$software-$version/redis-server $targetPath/$software-$version/redis.conf\n" +
                "\n" +
                "# 清理资源\n" +
                "if [[ 0 -eq $? ]];then\n" +
                "\techo \"redis install and run success...\"\n" +
                "\techo \"clear package...\"\n" +
                "\trm -rf $(pwd)/$software-$version.tar.gz\n" +
                "fi", null, -1, "172.22.5.71");
        return new ResBody().buildSuccessResBody();
    }
}
