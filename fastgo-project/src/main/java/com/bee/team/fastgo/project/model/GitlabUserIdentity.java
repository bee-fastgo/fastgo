package com.bee.team.fastgo.project.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GitlabUserIdentity {

    @JsonProperty("provider")
    private String _provider;

    @JsonProperty("extern_uid")
    private String _externUid;


}
