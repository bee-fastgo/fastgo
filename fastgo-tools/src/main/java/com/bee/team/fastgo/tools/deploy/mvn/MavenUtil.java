package com.bee.team.fastgo.tools.deploy.mvn;

import org.apache.maven.shared.invoker.*;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author luke
 * @desc maven api
 * @date 2020-07-25
 **/
public class MavenUtil {
    @Value("java.home")
    private String javaHome = "C:\\Program Files\\Java\\jdk1.8.0_202";
    @Value("maven.home")
    private String mavenHome = "D:\\softwarePath\\apache-maven-3.6.3";

    public Boolean cleanAndInstall(String projectPath) throws MavenInvocationException {
        InvocationRequest request = new DefaultInvocationRequest();
        request.setPomFile(new File(projectPath + "/" + "pom.xml"));
        List<String> cmds = new ArrayList<>();
        cmds.add("clean");
        cmds.add("install");
        cmds.add("-U");
        request.setGoals(cmds);
        request.setJavaHome(new File(javaHome));
        Invoker invoker = new DefaultInvoker();
        invoker.setMavenHome(new File(mavenHome));
        if (invoker.execute(request).getExitCode() != 0) {
            return false;
        }
        return true;
    }
}
