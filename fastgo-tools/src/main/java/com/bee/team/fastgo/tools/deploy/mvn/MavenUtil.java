package com.bee.team.fastgo.tools.deploy.mvn;

import com.spring.simple.development.support.properties.PropertyConfigurer;
import org.apache.maven.shared.invoker.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author luke
 * @desc maven api
 * @date 2020-07-25
 **/
public class MavenUtil {

    public Boolean cleanAndInstall(String projectPath) throws MavenInvocationException {
        InvocationRequest request = new DefaultInvocationRequest();
        request.setPomFile(new File(projectPath + "/" + "pom.xml"));
        List<String> cmds = new ArrayList<>();
        cmds.add("clean");
        cmds.add("install");
        cmds.add("-U");
        request.setGoals(cmds);
        request.setJavaHome(new File(PropertyConfigurer.getProperty("java.home")));
        Invoker invoker = new DefaultInvoker();
        invoker.setMavenHome(new File(PropertyConfigurer.getProperty("maven.home")));
        InvocationResult execute = invoker.execute(request);
        if (invoker.execute(request).getExitCode() != 0) {
            return false;
        }
        return true;
    }
}
