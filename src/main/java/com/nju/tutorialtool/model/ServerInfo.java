package com.nju.tutorialtool.model;

import com.sun.javafx.beans.IDProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ServerInfo {
    @Id
    @GeneratedValue
    private Long id;

    private String ip;
    private String user;
    private String password;
    private String port;
    private String sourcePath;
//    private String destinationPath;


    public ServerInfo() {
    }

    public ServerInfo(String ip, String user, String password, String port, String sourcePath) {
        this.ip = ip;
        this.user = user;
        this.password = password;
        this.port = port;
        this.sourcePath = sourcePath;
//        this.destinationPath = destinationPath;
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

//    public String getDestinationPath() {
//        return destinationPath;
//    }
//
//    public void setDestinationPath(String destinationPath) {
//        this.destinationPath = destinationPath;
//    }
}
