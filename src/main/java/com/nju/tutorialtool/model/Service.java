package com.nju.tutorialtool.model;

public class Service {
    private String serviceName;
    private String image;
    private String port;
    private boolean isMysql;

    public Service(String serviceName, String image, String port, boolean isMysql) {
        this.serviceName = serviceName;
        this.image = image;
        this.port = port;
        this.isMysql = isMysql;
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
        return isMysql;
    }

    public void setMysql(boolean mysql) {
        isMysql = mysql;
    }
}
