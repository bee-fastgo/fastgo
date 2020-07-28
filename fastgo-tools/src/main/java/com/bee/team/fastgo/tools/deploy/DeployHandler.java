package com.bee.team.fastgo.tools.deploy;

import com.bee.team.fastgo.tools.deploy.git.GitUtil;
import com.bee.team.fastgo.tools.deploy.mvn.MavenUtil;
import com.bee.team.fastgo.tools.deploy.scp.Scp;
import org.apache.maven.shared.invoker.MavenInvocationException;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;
import java.io.IOException;

/**
 * @author luke
 * @desc 自动部署
 * @date 2020-07-25
 **/
public class DeployHandler {
    /**
     * 部署项目
     *
     * @param deployDTO
     * @throws GitAPIException
     * @throws IOException
     * @throws MavenInvocationException
     */
    public static void deploy(DeployDTO deployDTO) throws GitAPIException, IOException, MavenInvocationException {

        //String projectName = "insurance-platform";
        //String gitUrl = "http://172.22.5.242/Insurance/insurance-platform.git";
        //String branch = "Formal2.0";
        GitUtil gitUtil = new GitUtil();
        // 1 拿到项目文件
        String projectPath = gitUtil.gitPull(deployDTO.getProjectName(), deployDTO.getGitUrl(), deployDTO.getBranchName());
        // 2 mvn 编译
        MavenUtil mavenUtil = new MavenUtil();
        Boolean aBoolean = mavenUtil.cleanAndInstall(projectPath);
        if (aBoolean == false) {
            throw new MavenInvocationException("编译失败");
        }
        // 3 推送
        String localPath = projectPath + "/target/" + deployDTO.getProjectName() + "-spring-simple.tar.gz";
        //Scp.uploadFile("172.22.5.243", 22,"root", "123456", new File(localPath), projectPath);
        Scp.uploadFile(deployDTO.getServiceIp(), deployDTO.getServicePort().intValue(), deployDTO.getServiceUserName(), deployDTO.getServiceUserPassword(), new File(localPath), projectPath);
        // 4 部署
        Scp.deploy(deployDTO.getProjectName(), deployDTO.getProjectPort(), deployDTO.getServiceIp(), deployDTO.getServiceUserName(), deployDTO.getServiceUserPassword(), projectPath);

    }
}
