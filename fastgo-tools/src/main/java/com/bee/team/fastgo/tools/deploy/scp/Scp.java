package com.bee.team.fastgo.tools.deploy.scp;

import ch.ethz.ssh2.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author luke
 * @desc 远程传输
 * @date 2020-07-27
 **/
public class Scp {
    /**
     * 远程copy并部署
     *
     * @param projectName
     * @param port
     * @param dataServerIp
     * @param user
     * @param password
     * @param localPath
     * @param remotePath
     * @throws IOException
     */
    public static void doSCPAndInvoke(String projectName, String port, String dataServerIp, String user, String password, String localPath, String remotePath) throws IOException {
        //文件scp到数据服务器
        Connection conn = null;
        try {
            conn = new Connection(dataServerIp);
            System.out.println("开始scp文件");
            conn.connect();

            boolean isAuthenticated = conn.authenticateWithPassword(user, password);
            if (isAuthenticated == false) {
                throw new IOException("Authentication failed.文件scp到数据服务器时发生异常");
            }
            //执行删除命令
            SCPClient client = new SCPClient(conn);
            //本地文件scp到远程目录
            client.put(localPath, remotePath);

            String step1 = "rm -rf " + remotePath + "/*";
            String step2 = "mkdir -p " + remotePath;
            String step3 = "unzip  " + remotePath + "/" + projectName + "-spring-simple.zip";
            String step4 = "echo " + getDockerFile(projectName) + " >> " + remotePath + "/Dockerfile";
            String step5 = "docker build -t " + projectName + " .";
            String step6 = "docker stop " + projectName + "Docker";
            String step7 = "docker rm " + projectName + "Docker";
            String step8 = "docker   run --name=" + projectName + "Docker -d -p " + port + ":" + port + " " + projectName;
            conn.openSession().execCommand(step1 + "\n" + step2 + "\n" + step3 + "\n" + step4 + "\n" + step5 + "\n" + step6 + "\n" + step7 + "\n" + step8);
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    public static String getDockerFile(String projectName) {
        String dockerFile = "'FROM openjdk:8-jre-slim\n" +
                "MAINTAINER fastgo\n" +
                "\n" +
                "ENV PARAMS=\"\"\n" +
                "\n" +
                "ENV TZ=PRC\n" +
                "RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone\n" +
                "\n" +
                "ADD " + projectName + " /" + projectName + "\n" +
                "\n" +
                "ENTRYPOINT [\"sh\",\"-c\",\"java -jar $JAVA_OPTS /" + projectName + "/" + projectName + ".jar $PARAMS\"]'";
        return dockerFile;
    }
}
