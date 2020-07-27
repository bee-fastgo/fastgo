package com.bee.team.fastgo.tools.deploy.git;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.beans.factory.annotation.Value;

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

    @Value("${gitlab.username}")
    private String gitUser;
    @Value("${gitlab.password}")
    private String gitPassword;

    @Value("${fastgo.project.path}")
    private String projectPath;

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

        CredentialsProvider credential = createCredential(gitUser, gitPassword);
        Git git = Git.cloneRepository()
                .setCredentialsProvider(credential)
                .setURI(repoUrl)
                .setBranch(branchName)
                .setDirectory(new File(cloneDir)).call();
        return git;
    }

    public String gitPull(String projectName, String projectUrl, String branchName) throws GitAPIException, IOException {

        File file = new File(projectPath + projectName + "/" + branchName);
        if (file.exists()) {
            getRepositoryFromDir(projectPath + projectName + "/" + branchName);
        } else {
            fromCloneRepository(projectUrl, projectPath + projectName + "/" + branchName, branchName);
        }
        return projectPath + projectName + "/" + branchName;
    }

    public Repository getRepositoryFromDir(String dir) throws IOException {
        return new FileRepositoryBuilder()
                .setGitDir(Paths.get(dir, ".git").toFile())
                .build();
    }
}
