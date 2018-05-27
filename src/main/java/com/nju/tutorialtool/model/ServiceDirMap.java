package com.nju.tutorialtool.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ServiceDirMap {
    @Id
    @GeneratedValue
    private Long id;

    private String serviceName;

    private String dirName;

    public ServiceDirMap(String serviceName, String dirName) {
        this.serviceName = serviceName;
        this.dirName = dirName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getDirName() {
        return dirName;
    }

    public void setDirName(String dirName) {
        this.dirName = dirName;
    }
}
