package com.bee.team.fastgo.service.server.impl;

import com.alibaba.lava.base.AbstractLavaBoImpl;
import com.bee.team.fastgo.common.SoftwareEnum;
import com.bee.team.fastgo.exception.sever.ScriptException;
import com.bee.team.fastgo.mapper.ServerScriptDoMapperExt;
import com.bee.team.fastgo.model.ServerScriptDo;
import com.bee.team.fastgo.model.ServerScriptDoExample;
import com.bee.team.fastgo.service.server.ServerScriptBo;
import com.bee.team.fastgo.service.server.ServerSourceBo;
import com.bee.team.fastgo.vo.server.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spring.simple.development.core.annotation.base.NoApiMethod;
import com.spring.simple.development.core.component.mvc.BaseSupport;
import com.spring.simple.development.core.component.mvc.page.ResPageDTO;
import com.spring.simple.development.support.exception.GlobalException;
import com.spring.simple.development.support.utils.RandomUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
public class ServerScriptBoImpl extends AbstractLavaBoImpl<ServerScriptDo, ServerScriptDoMapperExt, ServerScriptDoExample> implements ServerScriptBo {

    @Autowired
    private BaseSupport baseSupport;

    @Autowired
    private ServerSourceBo serverSourceBo;

    @Autowired
    @NoApiMethod
    public void setBaseMapper(ServerScriptDoMapperExt mapper) {
        setMapper(mapper);
    }

    @Override
    public void saveScript(ReqAddScriptVo reqAddScriptVo) {
        //名字是否重复
        if(scriptExistByScriptName(reqAddScriptVo.getScriptName())){
            throw new GlobalException(ScriptException.SCRIPT_ABNORMAL, "该脚本名已存在");
        }
        //是否支持该软件
        if(Stream.of(SoftwareEnum.values()).map(SoftwareEnum::name).map(String::toLowerCase).noneMatch(s -> s.equals(reqAddScriptVo.getSoftwareName()))) {
            throw new GlobalException(ScriptException.SCRIPT_ABNORMAL, "不支持的软件类型");
        }
        //是否支持该版本
        if(!serverSourceBo.listVersions(reqAddScriptVo.getSoftwareName()).contains(reqAddScriptVo.getVersion())){
            throw new GlobalException(ScriptException.SCRIPT_ABNORMAL, "不支持的软件版本");
        }
        //通过软件名,版本号,脚本类型判断脚本是否已存在
        if(scriptExistBySoftwareNameAndVersionAndType(reqAddScriptVo.getSoftwareName(),reqAddScriptVo.getVersion(),reqAddScriptVo.getType())){
            throw new GlobalException(ScriptException.SCRIPT_ABNORMAL, "软件所对应的脚本类型已存在");
        }

        //插入数据
        ServerScriptDo serverScriptDo = baseSupport.objectCopy(reqAddScriptVo, ServerScriptDo.class);
        serverScriptDo.setScriptKey(RandomUtil.randomStr(16));
        insert(serverScriptDo);
    }


    @Override
    public ResPageDTO<ResScriptVo> getScriptByPage(QueryScriptVo queryScriptVo) {
        ServerScriptDoExample serverScriptDoExample = new ServerScriptDoExample();
        if(StringUtils.isNotEmpty(queryScriptVo.getSoftwareName())) {
            //是否支持该软件类型
            if(Stream.of(SoftwareEnum.values()).map(SoftwareEnum::name).map(String::toLowerCase).noneMatch(s -> s.equals(queryScriptVo.getSoftwareName()))) {
                throw new GlobalException(ScriptException.SCRIPT_ABNORMAL, "不支持的软件类型");
            }
            serverScriptDoExample.createCriteria().andSoftwareNameEqualTo(queryScriptVo.getSoftwareName());
        }

        //查询数据
        int startNo = queryScriptVo.getStartPage();
        int rowCount = queryScriptVo.getPageSize();
        PageHelper.startPage(startNo, rowCount);
        List<ServerScriptDo> serverScriptDoList = selectByExample(serverScriptDoExample);
        return baseSupport.pageCopy(new PageInfo(serverScriptDoList), ResScriptVo.class);
    }

    @Override
    public ResScriptInfoVo getScriptInfoByScriptKey(String scriptKey) {
        //脚本是否存在
        if(!scriptExistByScriptKey(scriptKey)){
            throw new GlobalException(ScriptException.SCRIPT_ABNORMAL, "脚本不存在");
        }

        //查询数据
        ServerScriptDoExample serverScriptDoExample = new ServerScriptDoExample();
        serverScriptDoExample.createCriteria().andScriptKeyEqualTo(scriptKey);
        List<ServerScriptDo> serverScriptDoList = selectByExample(serverScriptDoExample);
        return baseSupport.objectCopy(serverScriptDoList.get(0), ResScriptInfoVo.class);
    }

    @Override
    public void updateScript(ReqUpdateScriptVo reqUpdateScriptVo) {
        //脚本是否存在
        if(!scriptExistByScriptKey(reqUpdateScriptVo.getScriptKey())){
            throw new GlobalException(ScriptException.SCRIPT_ABNORMAL, "脚本不存在");
        }

        //修改数据
        ServerScriptDo serverScriptDo = new ServerScriptDo();
        serverScriptDo.setScript(reqUpdateScriptVo.getScript());
        ServerScriptDoExample serverScriptDoExample = new ServerScriptDoExample();
        serverScriptDoExample.createCriteria().andScriptKeyEqualTo(reqUpdateScriptVo.getScriptKey());
        updateByExample(serverScriptDo,serverScriptDoExample);
    }

    @Override
    public void deleteScriptByScriptKey(String scriptKey) {
        ServerScriptDoExample serverScriptDoExample = new ServerScriptDoExample();
        serverScriptDoExample.createCriteria().andScriptKeyEqualTo(scriptKey);
        deleteByExample(serverScriptDoExample);
    }

    @Override
    public ServerScriptDo getScriptBySoftwareNameAndVersionAndType(String softwareName, String version, String type) {
        //是否支持该软件
        if(Stream.of(SoftwareEnum.values()).map(SoftwareEnum::name).map(String::toLowerCase).noneMatch(s -> s.equals(softwareName))) {
            throw new GlobalException(ScriptException.SCRIPT_ABNORMAL, "不支持的软件类型");
        }
        //脚本是否存在
        if(!scriptExistBySoftwareNameAndVersionAndType(softwareName,version,type)){
            throw new GlobalException(ScriptException.SCRIPT_ABNORMAL, "脚本不存在");
        }
        ServerScriptDoExample serverScriptDoExample = new ServerScriptDoExample();
        serverScriptDoExample.createCriteria().andSoftwareNameEqualTo(softwareName).andVersionEqualTo(version).andTypeEqualTo(type);
        List<ServerScriptDo> serverScriptDoList = selectByExample(serverScriptDoExample);
        return serverScriptDoList.get(0);
    }


    /**
     * 通过脚本key查询脚本是否存在
     * @param scriptKey 脚本key
     * @return {@link boolean 存在返回true不存在返回false}
     * @author jgz
     * @date 12:38 2020/7/24
     * @Description
     */
    private boolean scriptExistByScriptKey(String scriptKey){
        ServerScriptDoExample serverScriptDoExample = new ServerScriptDoExample();
        serverScriptDoExample.createCriteria().andScriptKeyEqualTo(scriptKey);
        return CollectionUtils.isNotEmpty(selectByExample(serverScriptDoExample));
//        return countByExample(serverScriptDoExample) > 0;
    }

    /**
     * 通过脚本名查询脚本是否存在
     * @param scriptName 脚本名
     * @return {@link boolean 存在返回true不存在返回false}
     * @author jgz
     * @date 12:23 2020/7/24
     * @Description
     */
    private boolean scriptExistByScriptName(String scriptName){
        ServerScriptDoExample serverScriptDoExample = new ServerScriptDoExample();
        serverScriptDoExample.createCriteria().andScriptNameEqualTo(scriptName);
        return CollectionUtils.isNotEmpty(selectByExample(serverScriptDoExample));
//        return countByExample(serverScriptDoExample) > 0;
    }

    /**
     * 通过软件名,版本,脚本类型查询脚本是否存在
     * @param softwareName 软件名
     * @param version 版本
     * @param type 类型
     * @return {@link boolean 存在返回true不存在返回false}
     * @author jgz
     * @date 12:28 2020/7/24
     * @Description
     */
    private boolean scriptExistBySoftwareNameAndVersionAndType(String softwareName,String version,String type){
        ServerScriptDoExample serverScriptDoExample = new ServerScriptDoExample();
        serverScriptDoExample.createCriteria().andSoftwareNameEqualTo(softwareName).andVersionEqualTo(version).andTypeEqualTo(type);
        return CollectionUtils.isNotEmpty(selectByExample(serverScriptDoExample));
//        return countByExample(serverScriptDoExample) > 0;
    }


}