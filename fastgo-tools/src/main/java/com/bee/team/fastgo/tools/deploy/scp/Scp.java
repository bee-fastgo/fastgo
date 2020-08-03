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
            localFile.delete();
        } catch (IOException e) {
            throw new GlobalException(SERVICE_FAILED);
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    /**
     * 部署
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
            // 构建docker
            String step5 = "cd  " + remotePath + "\n" + "docker build -t " + projectName + " .";
            invokeCmd(conn.openSession(), step5);
            // 部署
            String step6 = "cd  " + remotePath + "\n" + "docker stop " + projectName + "Docker";
            String step7 = "cd  " + remotePath + "\n" + "docker rm " + projectName + "Docker";
            String step8 = "cd  " + remotePath + "\n" + "docker   run --name=" + projectName + "Docker -d -p " + port + ":" + port + " " + projectName;
            invokeCmd(conn.openSession(), step6);
            invokeCmd(conn.openSession(), step7);
            invokeCmd(conn.openSession(), step8);
            System.out.println(DateUtils.getCurrentTime() + "部署完成");
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    /**
     * vue部署
     *
     * @param projectName
     * @param port
     * @param dataServerIp
     * @param user
     * @param password
     * @param remotePath
     * @throws IOException
     */
    public static void vueDeploy(String projectName, String projectPort, int port, String dataServerIp, String user, String password, String remotePath, String serviceUrl) throws IOException {
        Connection conn = null;
        try {
            conn = new Connection(dataServerIp, port);
            System.out.println(DateUtils.getCurrentTime() + "开始部署");
            conn.connect();

            boolean isAuthenticated = conn.authenticateWithPassword(user, password);
            if (isAuthenticated == false) {
                throw new IOException("Authentication failed.文件scp到数据服务器时发生异常");
            }
            // 解压
            String step1 = "cd  " + remotePath + "\n" + "tar -zxvf  dist.tar.gz";
            invokeCmd(conn.openSession(), step1);
            // 构建Nginx.conf
            String step2 = "cd  " + remotePath + "\n" + "echo " + getNginxConfig(dataServerIp, projectPort, remotePath, serviceUrl) + " >> " + remotePath + "/default.conf";
            invokeCmd(conn.openSession(), step2);
            // 构建Docker
            String step3 = "cd  " + remotePath + "\n" + "echo " + getVueDockerFile(projectPort) + " >> " + remotePath + "/Dockerfile";
            invokeCmd(conn.openSession(), step3);
            // 构建docker
            String step5 = "docker build -t " + projectName + " .";
            invokeCmd(conn.openSession(), step5);

            // 部署
            String step6 = "docker stop " + projectName + "Docker";
            String step7 = "docker rm " + projectName + "Docker";
            //-v /data/nginx/conf/nginx.conf:/etc/nginx/nginx.conf
            //-v /data/nginx/log:/var/log/nginx
            //-v /data/nginx/html:/usr/share/nginx/html
            String step8 = "docker   run --name=" + projectName + "Docker -d -p " + projectPort + ":" + projectPort + " -v " + remotePath + "/default.conf:/etc/nginx/nginx.conf -v /data/nginx/log:/var/log/nginx -v " + remotePath + "/dist:/usr/share/nginx/html " + projectName;
            invokeCmd(conn.openSession(), step6 + "\n" + step7 + "\n" + step8);
            System.out.println(DateUtils.getCurrentTime() + "部署完成");
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    public static String getVueDockerFile(String projectPort) {
        String dockerFile = "'# Base Image设置基础镜像\n" +
                "FROM nginx\n" +
                "\n" +
                "MAINTAINER fastgo\n" +
                "\n" +
                "# 将文件中的内容复制到 /usr/share/nginx/html/ 这个目录下面\n"+
                "COPY ./dist  /usr/share/nginx/html/\n" +
                "COPY default.conf /etc/nginx/conf.d/default.conf\n" +
                "EXPOSE " + projectPort + "\n'";
        return dockerFile;
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

    public static String getNginxConfig(String nginxIp, String nginxPort, String projectPath, String serviceUrl) {
        String dockerFile = "'\n" +
                "user  root;\n" +
                "worker_processes  1;\n" +
                "events {\n" +
                "    worker_connections  1024;\n" +
                "}\n" +
                "http {\n" +
                "    include       mime.types;\n" +
                "    default_type  application/octet-stream;\n" +
                "    sendfile        on;\n" +
                "    keepalive_timeout  65;\n" +
                "    root /usr/share/nginx/html/;\n" +
                "    server {\n" +
                "        listen       " + nginxPort + ";\n" +
                "        server_name  " + nginxIp + ";\n" +
                "        location / {\n" +
                "            index  index.html index.htm;\n" +
                "            try_files $uri $uri/ /index.html;\n" +
                "            proxy_set_header   Host             $host;\n" +
                "            proxy_set_header   X-Real-IP        $remote_addr;\n" +
                "            proxy_set_header   X-Forwarded-For  $proxy_add_x_forwarded_for;\n" +
                "            proxy_cookie_path / /; # 关键配置\n" +
                "        }\n" +
                "        error_page   500 502 503 504  /50x.html;\n" +
                "        location = /50x.html {\n" +
                "            root   html;\n" +
                "        }\n" +
                "        location /api {\n" +
                "            proxy_pass " + serviceUrl + ";\n" +
                "        }\n" +
                "    }\n" +
                "}\n'";
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
