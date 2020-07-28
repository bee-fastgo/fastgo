package com.bee.team.fastgo.tools.deploy.scp;

import ch.ethz.ssh2.*;
import com.spring.simple.development.support.exception.GlobalException;
import com.spring.simple.development.support.utils.DateUtils;

import java.io.*;

import static com.spring.simple.development.support.exception.GlobalResponseCode.SERVICE_FAILED;

/**
 * @author luke
 * @desc 远程传输
 * @date 2020-07-27
 **/
public class Scp {


    /**
     * 上传文件
     *
     * @param dataServerIp
     * @param port
     * @param user
     * @param password
     * @param localFile
     * @param remoteTargetDirectory
     */
    public static void uploadFile(String dataServerIp, int port, String user, String password, File localFile, String remoteTargetDirectory) {
        Connection conn = null;
        try {
            conn = new Connection(dataServerIp, port);
            System.out.println(DateUtils.getCurrentTime() + "开始远程传输文件");
            conn.connect();

            boolean isAuthenticated = conn.authenticateWithPassword(user, password);
            if (isAuthenticated == false) {
                throw new IOException("Authentication failed.文件scp到数据服务器时发生异常");
            }
            String cmd = "rm -rf " + remoteTargetDirectory + "/* &&" + " mkdir -p " + remoteTargetDirectory;
            invokeCmd(conn.openSession(), cmd);

            SCPClient scpClient = new SCPClient(conn);
            SCPOutputStream os = scpClient.put(localFile.getName(), localFile.length(), remoteTargetDirectory, null);
            byte[] b = new byte[4096];
            FileInputStream fis = new FileInputStream(localFile);
            int i;
            while ((i = fis.read(b)) != -1) {
                os.write(b, 0, i);
            }
            os.flush();
            fis.close();
            os.close();
            System.out.println(DateUtils.getCurrentTime() + "远程传输文件完成");
        } catch (IOException e) {
            throw new GlobalException(SERVICE_FAILED);
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    /**
     * 远程copy并部署
     *
     * @param projectName
     * @param port
     * @param dataServerIp
     * @param user
     * @param password
     * @param remotePath
     * @throws IOException
     */
    public static void deploy(String projectName, String port, String dataServerIp, String user, String password, String remotePath) throws IOException {
        Connection conn = null;
        try {
            conn = new Connection(dataServerIp);
            System.out.println(DateUtils.getCurrentTime() + "开始部署");
            conn.connect();

            boolean isAuthenticated = conn.authenticateWithPassword(user, password);
            if (isAuthenticated == false) {
                throw new IOException("Authentication failed.文件scp到数据服务器时发生异常");
            }
            // 解压
            String step3 = "cd  " + remotePath + "\n" + "tar -zxvf  " + remotePath + "/" + projectName + "-spring-simple.tar.gz";
            invokeCmd(conn.openSession(), step3);

            // 构建Docker
            String step4 = "echo " + getDockerFile(projectName) + " >> " + remotePath + "/Dockerfile";
            invokeCmd(conn.openSession(), step4);
            // 部署
            String step5 = "docker build -t " + projectName + " .";
            String step6 = "docker stop " + projectName + "Docker";
            String step7 = "docker rm " + projectName + "Docker";
            String step8 = "docker   run --name=" + projectName + "Docker -d -p " + port + ":" + port + " " + projectName;
            invokeCmd(conn.openSession(), step5 + "\n" + step6 + "\n" + step7 + "\n" + step8);
            System.out.println(DateUtils.getCurrentTime() + "部署完成");
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

    public static void invokeCmd(Session session, String cmd) throws IOException {
        System.out.println(DateUtils.getCurrentTime() + "执行命令：" + cmd);

        session.execCommand(cmd);
        //显示执行命令后的信息
        System.out.println("Here is some information about the remote host:");
        InputStream stdout = new StreamGobbler(session.getStdout());
        BufferedReader br = new BufferedReader(new InputStreamReader(stdout));
        while (true) {
            String line = br.readLine();
            if (line == null) {
                break;
            }
            System.out.println(line);
        }
        System.out.println("ExitCode: " + session.getExitStatus());
        //关闭远程连接
        session.close();
        System.out.println(DateUtils.getCurrentTime() + "执行命令：" + cmd + "完成");

    }
}
