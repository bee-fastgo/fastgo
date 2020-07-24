package com.bee.team.fastgo.project.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GitlabProjectSharedGroup {

    @JsonProperty("group_id")
    private int groupId;

    @JsonProperty("group_name")
    private String groupName;

    @JsonProperty("group_access_level")
    private int groupAccessLevel;


}
