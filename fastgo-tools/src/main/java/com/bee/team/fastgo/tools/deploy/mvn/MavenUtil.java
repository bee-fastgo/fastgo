package com.bee.team.fastgo.tools.deploy.mvn;

import com.bee.team.fastgo.tools.deploy.DeployHandler;
import com.bee.team.fastgo.tools.log.DeployJobFileAppender;
import com.spring.simple.development.support.properties.PropertyConfigurer;
import org.apache.maven.shared.invoker.*;

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
        request.setDebug(false);
        request.setJavaHome(new File(PropertyConfigurer.getProperty("java.home")));
        request.setInputStream(new FileInputStream(new File(DeployHandler.logPathThreadLocal.get())));
        Invoker invoker = new DefaultInvoker();
        invoker.setMavenHome(new File(PropertyConfigurer.getProperty("maven.home")));
        FileOutputStream fileOutputStream = new FileOutputStream(new File(DeployHandler.logPathThreadLocal.get()));
        PrintStream out = new PrintStream(fileOutputStream, true);
        InvokerLogger invokerLogger = new PrintStreamLogger(out, 3);
        invoker.setLogger(invokerLogger);
        if (invoker.execute(request).getExitCode() != 0) {
            return false;
        }
        return true;
    }
}
