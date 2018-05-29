package com.nju.tutorialtool.model;

public class DownloadInfo {
    private ServerInfo serverInfo;
    private String localPath;

    public DownloadInfo(ServerInfo serverInfo, String localPath) {
        this.serverInfo = serverInfo;
        this.localPath = localPath;
    }

    public ServerInfo getServerInfo() {
        return serverInfo;
    }

    public void setServerInfo(ServerInfo serverInfo) {
        this.serverInfo = serverInfo;
    }

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }
}
