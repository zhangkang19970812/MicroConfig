package com.nju.tutorialtool.model;

import java.util.List;

public class ServiceInfoList {
    private List<ServiceInfo> serviceInfoList;

    public ServiceInfoList(List<ServiceInfo> serviceInfoList) {
        this.serviceInfoList = serviceInfoList;
    }

    public List<ServiceInfo> getServiceInfoList() {
        return serviceInfoList;
    }

    public void setServiceInfoList(List<ServiceInfo> serviceInfoList) {
        this.serviceInfoList = serviceInfoList;
    }
}
