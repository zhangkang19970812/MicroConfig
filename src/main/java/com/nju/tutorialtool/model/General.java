package com.nju.tutorialtool.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.nju.tutorialtool.model.dto.RibbonDTO;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;

/**
 * @Author YZ
 * @Date 2018/5/20
 */
@Entity
public class General {

    @Id
    @GeneratedValue
    private Long id;

    /**
     * 要部署的服务器信息
     */
    private DeployServer deployServer;

    /**
     * Map：服务名称，文件夹名称
     */
    @OneToMany
    private List<ServiceDirMap> services;

    /**
     * 服务的配置
     */
    @OneToMany
    private List<Configuration> configurationList;

    /**
     * 是否含有EurekaServer
     */
    private boolean isEurekaServer;

    /**
     * 包含创建eureka server的信息
     */
    private SpringCloudInfo eurekaServerInfo;

    /**
     * 是否含有hystrix
     */
    private boolean isHystrix;

    /**
     * 是否含有ribbon
     */
    private boolean isRibbon;

    /**
     * RibbonDTO
     */
    @JSONField(name = "ribbon")
    @OneToOne
    private RibbonDTO ribbonDTO;

    /**
     * 是否含有Zuul
     */
    private boolean isZuul;

    private SpringCloudInfo zuulInfo;

    /**
     * 数据库创建、配置信息
     */
    @OneToMany
    private List<MysqlInfo> mysqlInfoList;

    public ServerInfo getServerInfo() {
        return serverInfo;
    }

    public void setServerInfo(ServerInfo serverInfo) {
        this.serverInfo = serverInfo;
    }

    /**
     * 项目部署路径及详细信息
     */
    @OneToOne
    private ServerInfo serverInfo;

    /**
     * 创建docker-compose.yml所需信息
     */
    @OneToOne
    private ComposeInfo composeInfo;

    public DeployServer getDeployServer() {
        return deployServer;
    }

    public void setDeployServer(DeployServer deployServer) {
        this.deployServer = deployServer;
    }

    public ComposeInfo getComposeInfo() {
        return composeInfo;
    }

    public void setComposeInfo(ComposeInfo composeInfo) {
        this.composeInfo = composeInfo;
    }

    public List<MysqlInfo> getMysqlInfoList() {
        return mysqlInfoList;
    }

    public void setMysqlInfoList(List<MysqlInfo> mysqlInfoList) {
        this.mysqlInfoList = mysqlInfoList;
    }

    public boolean isRibbon() {
        return isRibbon;
    }

    public void setRibbon(boolean ribbon) {
        isRibbon = ribbon;
    }

    public boolean isHystrix() {

        return isHystrix;
    }

    public void setHystrix(boolean hystrix) {
        isHystrix = hystrix;
    }

    public boolean isZuul() {

        return isZuul;
    }

    public void setZuul(boolean zuul) {
        isZuul = zuul;
    }

    public boolean isEurekaServer() {

        return isEurekaServer;
    }

    public void setEurekaServer(boolean eurekaServer) {
        isEurekaServer = eurekaServer;
    }

    public List<Configuration> getConfigurationList() {
        return configurationList;
    }

    public void setConfigurationList(List<Configuration> configurationList) {
        this.configurationList = configurationList;
    }

    public List<ServiceDirMap> getServices() {
        return services;
    }

    public void setServices(List<ServiceDirMap> services) {
        this.services = services;
    }

    public SpringCloudInfo getEurekaServerInfo() {
        return eurekaServerInfo;
    }

    public void setEurekaServerInfo(SpringCloudInfo eurekaServerInfo) {
        this.eurekaServerInfo = eurekaServerInfo;
    }

    public RibbonDTO getRibbonDTO() {
        return ribbonDTO;
    }

    public void setRibbonDTO(RibbonDTO ribbonDTO) {
        this.ribbonDTO = ribbonDTO;
    }

    public SpringCloudInfo getZuulInfo() {
        return zuulInfo;
    }

    public void setZuulInfo(SpringCloudInfo zuulInfo) {
        this.zuulInfo = zuulInfo;
    }
}