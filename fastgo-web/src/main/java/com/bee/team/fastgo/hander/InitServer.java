package com.bee.team.fastgo.hander;

/**
 * @author jgz
 * @date 2020/7/29
 * @desc 初始化服务事件源
 **/
public class InitServer {

    private String ip;

    private Integer port;

    private String user;

    private String password;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
