package com.bee.team.fastgo.tools.deploy;

import com.bee.team.fastgo.tools.deploy.git.GitUtil;
import com.bee.team.fastgo.tools.deploy.mvn.MavenUtil;
import com.bee.team.fastgo.tools.deploy.scp.Scp;
import org.apache.maven.shared.invoker.MavenInvocationException;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.*;
import java.util.UUID;

/**
 * @author luke
 * @desc 自动部署
 * @date 2020-07-25
 **/
public class DeployHandler {
    /**
     * 部署simple项目
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

    public static void deployVue(DeployDTO deployDTO) throws GitAPIException, IOException, InterruptedException {
        GitUtil gitUtil = new GitUtil();
        // 1 拿到项目文件
        String projectPath = gitUtil.gitPull(deployDTO.getProjectName(), deployDTO.getGitUrl(), deployDTO.getBranchName());
        // 2 npm 编译
        invokeDeployVue(projectPath);
        // 3 打包
        invokeBuildVue(projectPath);
        String localPath = projectPath + "/dist/";
        if (isWindows()) {
            localPath = System.getProperties().getProperty("user.dir").substring(0, 2) + projectPath + "/dist/";
            ;
        }
        File file = new File(projectPath + "/dist.tar.gz");
        if (file.exists()) {
            file.delete();
        }
        GzipUtil.compress(localPath, projectPath + "/dist.tar.gz");
        // 4 推送
        Scp.uploadFile(deployDTO.getServiceIp(), deployDTO.getServicePort().intValue(), deployDTO.getServiceUserName(), deployDTO.getServiceUserPassword(), file, projectPath);
        // 5 部署
        Scp.vueDeploy(deployDTO.getProjectName(), deployDTO.getProjectPort(), deployDTO.getServicePort(), deployDTO.getServiceIp(), deployDTO.getServiceUserName(), deployDTO.getServiceUserPassword(), projectPath, deployDTO.getSimpleServiceUrl());

    }

    /**
     * vue 项目编译
     *
     * @param projectPath
     * @throws IOException
     * @throws InterruptedException
     */
    public static void invokeDeployVue(String projectPath) throws IOException, InterruptedException {
        String scriptFileName;
        String cmd;
        if (isWindows()) {
            cmd = "cd " + System.getProperties().getProperty("user.dir").substring(0, 2) + projectPath + "\n npm install -registry=https://registry.npm.taobao.org";
            scriptFileName = System.getProperties().getProperty("user.dir").substring(0, 2) + projectPath + "/" + "vueBuild.bat";
        } else {
            cmd = "cd " + projectPath + "\n npm install -registry=https://registry.npm.taobao.org";
            scriptFileName = projectPath + "/" + "vueBuild.sh";
        }
        System.out.println("执行项目编译命令：" + cmd);
        File scriptFile = new File(scriptFileName);
        if (scriptFile.exists()) {
            scriptFile.delete();
        }
        ScriptUtil.markScriptFile(scriptFileName, cmd);
        Process process;
        if (isWindows()) {
            process = Runtime.getRuntime().exec(scriptFileName);
        } else {
            process = Runtime.getRuntime().exec("bash " + scriptFileName);
        }
        InputStream in = process.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            System.out.println(line + "\n");
        }
        in.close();
        System.out.println("项目编译执行结果：" + process.waitFor());
    }

    /**
     * vue 项目打包
     *
     * @param projectPath
     * @throws IOException
     * @throws InterruptedException
     */
    public static void invokeBuildVue(String projectPath) throws IOException, InterruptedException {
        String scriptFileName;
        String cmd;
        if (isWindows()) {
            cmd = "cd " + System.getProperties().getProperty("user.dir").substring(0, 2) + projectPath + "\n  npm run build";
            scriptFileName = System.getProperties().getProperty("user.dir").substring(0, 2) + projectPath + "/" + "vueBuild.bat";
        } else {
            cmd = "cd " + projectPath + "\n  npm run build";
            scriptFileName = projectPath + "/" + "vueBuild.sh";
        }
        System.out.println("执行项目打包命令：" + cmd);
        File scriptFile = new File(scriptFileName);
        if (scriptFile.exists()) {
            scriptFile.delete();
        }
        ScriptUtil.markScriptFile(scriptFileName, cmd);
        Process process = Runtime.getRuntime().exec(scriptFileName);
        InputStream in = process.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            System.out.println(line + "\n");
        }
        in.close();
        System.out.println("项目打包执行结果：" + process.waitFor());
    }

    public static boolean isWindows() {
        return System.getProperties().getProperty("os.name").toUpperCase().indexOf("WINDOWS") != -1;
    }

    public static void main(String[] args) throws IOException, GitAPIException, InterruptedException {
        DeployDTO deployDTO = new DeployDTO();
        String projectName = "h5-template";
        String gitUrl = "http://172.22.5.242/web/h5-template.git";
        String branch = "master";
        deployDTO.setProjectName(projectName);
        deployDTO.setGitUrl(gitUrl);
        deployDTO.setBranchName(branch);
        deployDTO.setSimpleServiceUrl("http://172.22.5.248:9999/");
        deployDTO.setServiceIp("172.22.5.243");
        deployDTO.setServicePort(22);
        deployDTO.setServiceUserName("root");
        deployDTO.setServiceUserPassword("123456");
        deployDTO.setProjectPort("10001");
        DeployHandler.deployVue(deployDTO);
    }


}
