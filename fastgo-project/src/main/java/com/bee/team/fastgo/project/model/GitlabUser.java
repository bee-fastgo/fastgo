package com.bee.team.fastgo.project.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class GitlabUser {

    private Integer id;
    private String username;
    private String email;
    private String name;
    private String skype;
    private String linkedin;
    private String twitter;
    private String provider;
    private String state;
    private boolean blocked;


}
