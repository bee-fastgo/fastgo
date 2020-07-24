package com.bee.team.fastgo.project.model;

import lombok.Data;

import java.util.List;

@Data
public class GitlabUser {

    private Integer _id;
    private String _username;
    private String _email;
    private String _name;
    private String _skype;
    private String _linkedin;
    private String _twitter;
    private String _provider;
    private String _state;
    private boolean _blocked;
    private List<GitlabUserIdentity> _identities;


}
