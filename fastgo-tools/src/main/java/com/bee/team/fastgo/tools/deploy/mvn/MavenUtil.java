package com.bee.team.fastgo.tools.deploy.mvn;

import ch.ethz.ssh2.StreamGobbler;
import com.bee.team.fastgo.tools.deploy.DeployHandler;
import com.bee.team.fastgo.tools.log.DeployJobFileAppender;
import com.spring.simple.development.support.properties.PropertyConfigurer;
import org.apache.maven.shared.invoker.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author luke
 * @desc maven api
 * @date 2020-07-25
 **/
public class MavenUtil {

    public Boolean cleanAndInstall(String projectPath) throws MavenInvocationException, IOException {
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
        InputStream inputStream = request.getInputStream(new FileInputStream(new File(DeployHandler.logPathThreadLocal.get())));
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        while (true) {
            String line = br.readLine();
            if (line == null) {
                break;
            }
            DeployJobFileAppender.appendLog(DeployHandler.logPathThreadLocal.get(),line);
        }
        if (invoker.execute(request).getExitCode() != 0) {
            return false;
        }
        return true;
    }
}
