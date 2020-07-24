package com.bee.team.fastgo.mapper;

import com.bee.team.fastgo.model.ProfileSoftwareRelationDo;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

@MapperScan
public interface ProfileSoftwareRelationDoMapperExt extends com.alibaba.lava.base.LavaMapper<com.bee.team.fastgo.model.ProfileSoftwareRelationDo, com.bee.team.fastgo.model.ProfileSoftwareRelationDoExample> {

    void batchInsertProfileSoftware(@Param("psDos") List<ProfileSoftwareRelationDo> psDos);

}