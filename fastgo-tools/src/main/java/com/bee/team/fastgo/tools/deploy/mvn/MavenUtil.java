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
    @Value("${java.home}")
    private String javaHome;
    @Value("${maven.home}")
    private String mavenHome;

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
