package com.nju.tutorialtool.model;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class ServiceInfo {
    @Id
    @GeneratedValue
    @JSONField(deserialize = false, serialize = false)
    private Long id;

    private String serviceName;

    private String folderName;

    /**
     * 微服务的配置
     */
    @OneToOne
    private Configuration config;

    /**
     * 微服务对应的mysql数据库的信息
     */
    @OneToOne
    @JSONField(serialize = false)
    private MysqlInfo mysqlInfo;

    public Configuration getConfig() {
        return config;
    }

    public void setConfig(Configuration config) {
        this.config = config;
    }

    public MysqlInfo getMysqlInfo() {
        return mysqlInfo;
    }

    public void setMysqlInfo(MysqlInfo mysqlInfo) {
        this.mysqlInfo = mysqlInfo;
    }

    public ServiceInfo() {
    }

    public ServiceInfo(String serviceName, String folderName) {
        this.serviceName = serviceName;
        this.folderName = folderName;
    }

    public ServiceInfo(String serviceName, String folderName, Configuration config) {
        this.serviceName = serviceName;
        this.folderName = folderName;
        this.config = config;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }
}
