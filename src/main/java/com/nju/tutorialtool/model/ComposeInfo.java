package com.nju.tutorialtool.model;

import java.util.List;

public class ComposeInfo {
//    private String baseDir;
    private List<Service> serviceList;

    public ComposeInfo(List<Service> serviceList) {
//        this.baseDir = baseDir;
        this.serviceList = serviceList;
    }

//    public String getBaseDir() {
//        return baseDir;
//    }
//
//    public void setBaseDir(String baseDir) {
//        this.baseDir = baseDir;
//    }

    public List<Service> getServiceList() {
        return serviceList;
    }

    public void setServiceList(List<Service> serviceList) {
        this.serviceList = serviceList;
    }
}
