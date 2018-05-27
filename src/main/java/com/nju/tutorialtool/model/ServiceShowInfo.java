package com.nju.tutorialtool.model;

public class ServiceShowInfo {
    private String serviceName;
    private String port;
    private String serverIp;

    public ServiceShowInfo(String serviceName, String port, String serverIp) {
        this.serviceName = serviceName;
        this.port = port;
        this.serverIp = serverIp;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }
}
