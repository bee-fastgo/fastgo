package com.bee.team.fastgo.project.utils;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.File;
import java.io.IOException;

/**
 * @author hs
 * @date 2020/7/25 15:53
 * @desc git 管理工具
 **/
public class GitUtil {

    /**
     * @param userName
     * @param password
     * @return {@link CredentialsProvider}
     * @author hs
     * @date 2020/7/25
     * @desc 生成gitlab用户凭证
     */
    public static CredentialsProvider createCredential(String userName, String password) {
        return new UsernamePasswordCredentialsProvider(userName, password);
    }

    /**
     * @param repoUrl
     * @param cloneDir
     * @param provider
     * @return {@link Git}
     * @author hs
     * @date 2020/7/25
     * @desc 克隆gitlab项目到本地
     */

    public static Git fromCloneRepository(String repoUrl, String cloneDir, CredentialsProvider provider) throws GitAPIException {
        Git git = Git.cloneRepository()
                .setCredentialsProvider(provider)
                .setURI(repoUrl)
                .setDirectory(new File(cloneDir)).call();
        return git;
    }

    /**
     * @param git
     * @param message
     * @param provider
     * @return
     * @author hs
     * @date 2020/7/25
     * @desc git提交代码
     */
    public static void commit(Git git, String message, CredentialsProvider provider) throws GitAPIException {
        git.add().addFilepattern(".").call();
        git.commit()
                .setMessage(message)
                .call();
    }

    /**
     * @param git
     * @param provider
     * @return
     * @author hs
     * @date 2020/7/25
     * @desc push代码到对应的git分支
     */

    public static void push(Git git, CredentialsProvider provider) throws GitAPIException, IOException {
        push(git, null, provider);
    }

    public static void push(Git git, String branch, CredentialsProvider provider) throws GitAPIException, IOException {
        if (branch == null) {
            branch = git.getRepository().getBranch();
        }
        git.push()
                .setCredentialsProvider(provider)
                .setRemote("origin").setRefSpecs(new RefSpec(branch)).call();
    }

}
