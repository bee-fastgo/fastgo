package com.bee.team.fastgo.tools.deploy.git;

import com.spring.simple.development.core.annotation.base.Value;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * @author luke
 * @desc git api
 * @auth luke
 * @date 2020-07-25
 **/
public class GitUtils {

    @Value("${gitlab.url}")
    private String gitlabUrl;
    @Value("${gitlab.username}")
    private static String gitUser;
    @Value("${gitlab.password}")
    private static String gitPassword;

    @Value("${fastgo.project.path}")
    private static String projectPath;

    /**
     * @param userName
     * @param password
     * @return {@link CredentialsProvider}
     * @author luke
     * @date 2020-07-25 16:02
     * @desc 获取凭证
     **/
    public static CredentialsProvider createCredential(String userName, String password) {
        return new UsernamePasswordCredentialsProvider(userName, password);
    }

    public static Git fromCloneRepository(String repoUrl, String cloneDir, String branchName) throws GitAPIException {

        CredentialsProvider credential = createCredential(gitUser, gitPassword);
        Git git = Git.cloneRepository()
                .setCredentialsProvider(credential)
                .setURI(repoUrl)
                .setBranch(branchName)
                .setDirectory(new File(cloneDir)).call();
        return git;
    }

    public static String gitPull(String projectName, String projectUrl, String branchName) throws GitAPIException {

        fromCloneRepository(projectUrl, projectPath + projectName + "/" + branchName, branchName);
        return projectPath + projectName + "/" + branchName;
    }

    public static Repository getRepositoryFromDir(String dir) throws IOException {
        return new FileRepositoryBuilder()
                .setGitDir(Paths.get(dir, ".git").toFile())
                .build();
    }
}
