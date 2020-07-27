package com.bee.team.fastgo.project.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author hs
 * @date 2020/7/27 17:57
 * @desc 项目分支名称
 **/
@Data
public class GitlabBranch {

    public final static String URL = "/repository/branches";

    @JsonProperty("name")
    private String name;

    @JsonProperty("commit")
    private GitlabBranchCommit commit;

    @JsonProperty("protected")
    private boolean branchProtected;


}
