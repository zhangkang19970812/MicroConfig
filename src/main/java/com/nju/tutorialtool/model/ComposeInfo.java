package com.nju.tutorialtool.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class ComposeInfo {
//    private String baseDir;
    @Id
    @GeneratedValue
    private Long id;

    @OneToMany
    private List<Service> serviceList;

    public ComposeInfo(){}

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
