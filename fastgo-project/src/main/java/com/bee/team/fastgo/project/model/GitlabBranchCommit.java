package com.bee.team.fastgo.project.model;

import lombok.Data;

/**
 * @author hs
 * @date 2020/7/27 17:58
 * @desc 项目分支详细信息
 **/

@Data
public class GitlabBranchCommit {

    public static String URL = "/users";

    private String id;
    private String tree;
    private String message;
    private GitlabUser author;
    private GitlabUser committer;

}
