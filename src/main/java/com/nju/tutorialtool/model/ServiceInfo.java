package com.nju.tutorialtool.model;

/**
 * @author Shenmiu
 * @date 2018/05/28
 * <p>
 * 保存一个微服务的所有信息
 */
public class ServiceInfo {

    /**
     * 微服务名称
     */
    private String serviceName;

    /**
     * 保存微服务的根目录名称，不包含路径
     */
    private String folderName;

    /**
     * 微服务的配置
     */
    private Configuration config;

    /**
     * 微服务对应的mysql数据库的信息
     */
    private MysqlInfo mysqlInfo;

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
}
