package com.bee.team.fastgo.hander.event;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import ch.ethz.ssh2.SCPOutputStream;
import com.bee.team.fastgo.hander.InitServer;
import com.bee.team.fastgo.hander.JobHandler;
import com.bee.team.fastgo.job.core.biz.model.HandleCallbackParam;
import com.bee.team.fastgo.job.core.util.IpUtil;
import com.bee.team.fastgo.tools.deploy.scp.Scp;
import com.spring.simple.development.support.exception.GlobalException;
import com.spring.simple.development.support.utils.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;

import static com.spring.simple.development.support.exception.GlobalResponseCode.SERVICE_FAILED;

/**
 * @Author luke
 * @Description 任务日志push事件
 * @Date 19:11 2020/7/3 0003
 **/
@EnableAsync
@Component
public class SeverListener {

    @Value("${server.port}")
    private Integer port;

    @Async
    @EventListener
    public void jobLogPushEventHandle(JobLogPushEvent jobLogPushEvent) {
        JobHandler.push((HandleCallbackParam) jobLogPushEvent.getSource());
    }


    @Async
    @EventListener
    public void initServerHandle(InitServerEvent initServerEvent){
        InitServer initServer = (InitServer) initServerEvent.getSource();
        Connection conn = null;
        try {
            conn = new Connection(initServer.getIp(), initServer.getPort());
            System.out.println(DateUtils.getCurrentTime() + "开始远程传输文件");
            conn.connect();

            boolean isAuthenticated = conn.authenticateWithPassword(initServer.getUser(), initServer.getPassword());
            if (!isAuthenticated) {
                throw new IOException("Authentication failed.文件scp到数据服务器时发生异常");
            }
            File file = new File("/data/fastgo/sources/init.tar.gz");
            if(file.exists()){
                uploadFile(initServer.getIp(), initServer.getPort(),initServer.getUser(),initServer.getPassword(),file,"/root/data");
                Scp.invokeCmd(conn.openSession(),"cd /root/data && tar -zxvf init.tar.gz && bash /root/data/init/install_jdk.sh http://" + IpUtil.getIp() + ":" + port);
            }
        }
        catch (IOException e) {
            throw new GlobalException(SERVICE_FAILED);
        } finally {
            if (conn != null) {
                conn.close();
            }

        }
    }

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
            Scp.invokeCmd(conn.openSession(), cmd);

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



}
