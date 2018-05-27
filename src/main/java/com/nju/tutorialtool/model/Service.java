package com.nju.tutorialtool.model;

public class Service {
    private String serviceName;
    private String image;
    private String port;
    private boolean mysql;

    public Service(String serviceName, String image, String port, boolean mysql) {
        this.serviceName = serviceName;
        this.image = image;
        this.port = port;
        this.mysql = mysql;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public boolean isMysql() {
        return mysql;
    }

    public void setMysql(boolean mysql) {
        this.mysql = mysql;
    }
}
