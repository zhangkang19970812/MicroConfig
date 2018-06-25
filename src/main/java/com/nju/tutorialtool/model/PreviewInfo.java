package com.nju.tutorialtool.model;

import java.util.List;

public class PreviewInfo {
    private String serviceName;
    private List<PreviewFileInfo> fileInfoList;

    public PreviewInfo(String serviceName, List<PreviewFileInfo> fileInfoList) {
        this.serviceName = serviceName;
        this.fileInfoList = fileInfoList;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public List<PreviewFileInfo> getFileInfoList() {
        return fileInfoList;
    }

    public void setFileInfoList(List<PreviewFileInfo> fileInfoList) {
        this.fileInfoList = fileInfoList;
    }
}
