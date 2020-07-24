package com.bee.team.fastgo.project.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GitlabProjectAccessLevel {

    @JsonProperty("access_level")
    private int accessLevel;

    @JsonProperty("notification_level")
    private int notificationLevel;


}
