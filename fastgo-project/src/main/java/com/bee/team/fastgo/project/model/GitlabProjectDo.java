package com.bee.team.fastgo.project.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class GitlabProjectDo {
    private Integer id;
    private String name;

    @JsonProperty("name_with_namespace")
    private String nameWithNamespace;

    private String description;

    @JsonProperty("default_branch")
    private String defaultBranch;

    private GitlabUser owner;
    private boolean publicProject;
    private String path;

    @JsonProperty("visibility_level")
    private Integer visibilityLevel;

    @JsonProperty("path_with_namespace")
    private String pathWithNamespace;

    @JsonProperty("issues_enabled")
    private boolean issuesEnabled;

    @JsonProperty("merge_requests_enabled")
    private boolean mergeRequestsEnabled;

    @JsonProperty("snippets_enabled")
    private boolean snippetsEnabled;

    @JsonProperty("wall_enabled")
    private boolean wallEnabled;

    @JsonProperty("wiki_enabled")
    private boolean wikiEnabled;

    @JsonProperty("jobs_enabled")
    private boolean jobsEnabled;

    @JsonProperty("shared_runners_enabled")
    private boolean sharedRunnersEnabled;

    @JsonProperty("public_jobs")
    private boolean publicJobs;

    @JsonProperty("runners_token")
    private String runnersToken;

    @JsonProperty("created_at")
    private Date createdAt;

    @JsonProperty("ssh_url_to_repo")
    private String sshUrl;

    @JsonProperty("web_url")
    private String webUrl;

    @JsonProperty("http_url_to_repo")
    private String httpUrl;

    @JsonProperty("last_activity_at")
    private Date lastActivityAt;

    @JsonProperty("archived")
    private boolean archived;

    private GitlabNamespace namespace;

    @JsonProperty("permissions")
    private GitlabPermission permissions;

    @JsonProperty("avatar_url")
    private String avatarUrl;

    @JsonProperty("creator_id")
    private Integer creatorId;

    @JsonProperty("star_count")
    private Integer starCount;

    @JsonProperty("forks_count")
    private Integer forksCount;

    @JsonProperty("tag_list")
    private List<String> tagList;

    @JsonProperty("shared_with_groups")
    private List<GitlabProjectSharedGroup> sharedWithGroups;

}
