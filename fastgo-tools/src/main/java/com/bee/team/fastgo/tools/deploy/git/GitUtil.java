package com.bee.team.fastgo.tools.deploy.git;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
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
    private String gitUser = "root";
    @Value("${gitlab.password}")
    private String gitPassword = "restart@2020";

    @Value("${fastgo.project.path}")
    private String projectPath = "/data/fastgo/deploy/";

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
            gitPull(file);
        } else {
            fromCloneRepository(projectUrl, projectPath + projectName + "/" + branchName, branchName);
        }
        return projectPath + projectName + "/" + branchName;
    }

    public void gitPull(File repoDir) throws GitAPIException, IOException {
        File RepoGitDir = new File(repoDir.getAbsolutePath() + "/.git");
        Repository repo = null;
        try {
            CredentialsProvider credential = createCredential(gitUser, gitPassword);
            repo = new FileRepository(RepoGitDir.getAbsolutePath());
            Git git = new Git(repo);
            PullCommand pullCmd = git.pull();
            pullCmd.setCredentialsProvider(credential);
            pullCmd.call();
        } finally {
            if (repo != null) {
                repo.close();
            }
        }
    }
}
