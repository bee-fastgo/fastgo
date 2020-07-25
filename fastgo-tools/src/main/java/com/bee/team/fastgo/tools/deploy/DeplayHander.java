package com.bee.team.fastgo.tools.deploy;

import com.bee.team.fastgo.tools.deploy.git.GitUtil;
import com.bee.team.fastgo.tools.deploy.mvn.MavenUtil;
import org.apache.maven.shared.invoker.MavenInvocationException;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.IOException;

/**
 * @author luke
 * @desc 自动部署
 * @date 2020-07-25
 **/
public class DeplayHander {

    public static void main(String[] args) throws GitAPIException, IOException, MavenInvocationException {
        GitUtil gitUtil = new GitUtil();
        // 1 拿到项目文件
        String projectPath = gitUtil.gitPull("fastgo", "http://172.22.5.242/bee-FastGo/fastgo.git", "dev");
        // 2 mvn 编译
        MavenUtil mavenUtil = new MavenUtil();
        mavenUtil.cleanAndInstall(projectPath);
        // 推送到指定环境
    }
}
