package com.bee.team.fastgo.tools.deploy;

import com.bee.team.fastgo.tools.deploy.git.GitUtil;
import com.bee.team.fastgo.tools.deploy.mvn.MavenUtil;
import com.bee.team.fastgo.tools.deploy.scp.Scp;
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

        String projectName = "insurance-platform";
        String gitUrl = "http://172.22.5.242/Insurance/insurance-platform.git";
        String branch = "Formal2.0";
        GitUtil gitUtil = new GitUtil();
        // 1 拿到项目文件
        String projectPath = gitUtil.gitPull(projectName, gitUrl, branch);
        // 2 mvn 编译
//        MavenUtil mavenUtil = new MavenUtil();
//        Boolean aBoolean = mavenUtil.cleanAndInstall(projectPath);
//        if (aBoolean == false) {
//            throw new MavenInvocationException("编译失败");
//        }
        // 3 推送到指定环境并部署
        Scp.doSCPAndInvoke(projectName, "8000", "172.22.5.243", "root", "123456", projectPath + "/target/" + projectName + "-spring-simple.zip", projectPath);

    }
}
