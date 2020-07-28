package com.bee.team.fastgo.project.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class GitlabProjectHook {

    private Integer id;
    private String url;

    @JsonProperty("project_id")
    private Integer projectId;

    @JsonProperty("push_events")
    private boolean pushEvents;

    @JsonProperty("issues_events")
    private boolean issueEvents;

    @JsonProperty("merge_requests_events")
    private boolean mergeRequestsEvents;

    @JsonProperty("tag_push_events")
    private boolean tagPushEvents;

    @JsonProperty("created_at")
    private Date createdAt;

    @JsonProperty("enable_ssl_verification")
    private boolean sslVerificationEnabled;

}
