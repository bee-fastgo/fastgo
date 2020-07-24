package com.bee.team.fastgo.project.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class GitlabCommit {

    public final static String URL = "/commits";

    private String id;
    private String title;
    private String message;

    @JsonProperty("short_id")
    private String shortId;

    @JsonProperty("author_name")
    private String authorName;

    @JsonProperty("author_email")
    private String authorEmail;

    @JsonProperty("created_at")
    private Date createdAt;

    @JsonProperty("committed_date")
    private Date committedDate;

    @JsonProperty("authored_date")
    private Date authoredDate;

    @JsonProperty("parent_ids")
    private List<String> parentIds;


}
