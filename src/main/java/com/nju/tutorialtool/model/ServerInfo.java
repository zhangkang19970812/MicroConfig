package com.nju.tutorialtool.model;

public class ServerInfo {
    private String ip;
    private String user;
    private String password;
    private String port;
    private String sourcePath;
    private String destinationPath;

    public ServerInfo(String ip, String user, String password, String port, String sourcePath, String destinationPath) {
        this.ip = ip;
        this.user = user;
        this.password = password;
        this.port = port;
        this.sourcePath = sourcePath;
        this.destinationPath = destinationPath;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
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

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getSourcePath() {
        return sourcePath;
    }

    public void setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
    }

    public String getDestinationPath() {
        return destinationPath;
    }

    public void setDestinationPath(String destinationPath) {
        this.destinationPath = destinationPath;
    }}
