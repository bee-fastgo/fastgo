package com.bee.team.fastgo.tools.deploy.git;

import com.bee.team.fastgo.tools.deploy.DeployHandler;
import com.bee.team.fastgo.tools.log.DeployJobFileAppender;
import com.spring.simple.development.support.properties.PropertyConfigurer;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullCommand;
import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * @author luke
 * @desc git api
 * @auth luke
 * @date 2020-07-25
 **/
public class GitUtil {

    /**
     * @param userName
     * @param password
     * @return {@link CredentialsProvider}
     * @author luke
     * @date 2020-07-25 16:02
     * @desc 获取凭证
     **/
    public CredentialsProvider createCredential(String userName, String password) {
        return new UsernamePasswordCredentialsProvider(userName, password);
    }

    public Git fromCloneRepository(String repoUrl, String cloneDir, String branchName) throws GitAPIException {

        CredentialsProvider credential = createCredential(PropertyConfigurer.getProperty("gitlab.username"), PropertyConfigurer.getProperty("gitlab.password"));
        Git git = Git.cloneRepository()
                .setCredentialsProvider(credential)
                .setURI(repoUrl)
                .setBranch(branchName)
                .setDirectory(new File(cloneDir)).call();
        return git;
    }

    public String gitPull(String projectName, String projectUrl, String branchName) throws GitAPIException, IOException {

        File file = new File(PropertyConfigurer.getProperty("fastgo.project.path") + projectName + "/" + branchName);
        if (file.exists()) {
            gitPull(file);
        } else {
            fromCloneRepository(projectUrl, PropertyConfigurer.getProperty("fastgo.project.path") + projectName + "/" + branchName, branchName);
        }
        return PropertyConfigurer.getProperty("fastgo.project.path") + projectName + "/" + branchName;
    }

    public void gitPull(File repoDir) throws GitAPIException, IOException {
        File RepoGitDir = new File(repoDir.getAbsolutePath() + "/.git");
        Repository repo = null;
        try {
            CredentialsProvider credential = createCredential(PropertyConfigurer.getProperty("gitlab.username"), PropertyConfigurer.getProperty("gitlab.password"));
            repo = new FileRepository(RepoGitDir.getAbsolutePath());
            Git git = new Git(repo);
            PullCommand pullCmd = git.pull();
            pullCmd.setCredentialsProvider(credential);
            PullResult call = pullCmd.call();
            DeployJobFileAppender.appendLog(DeployHandler.logPathThreadLocal.get(), call.toString()+"\n");
        } finally {
            if (repo != null) {
                repo.close();
            }
        }
    }
}
