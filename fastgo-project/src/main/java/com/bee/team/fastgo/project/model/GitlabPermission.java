package com.bee.team.fastgo.project.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GitlabPermission {

    @JsonProperty("project_access")
    private GitlabProjectAccessLevel projectAccess;

    @JsonProperty("group_access")
    private GitlabProjectAccessLevel groupAccess;


}
