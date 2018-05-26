package com.nju.tutorialtool.util.ssh;

public class SSHInfo {
    private String host;
    private String user;
    private String pwd;
    private int port;

    /**
     *
     * @param host
     * @param user
     * @param pwd
     */
    public SSHInfo(String host, String user, String pwd) {
        this.host = host;
        this.user = user;
        this.pwd = pwd;
        this.port = 22; //default
    }

    /**
     *
     * @param host
     * @param user
     * @param pwd
     * @param port
     */
    public SSHInfo(String host, String user, String pwd, int port) {
        this.host = host;
        this.user = user;
        this.pwd = pwd;
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
