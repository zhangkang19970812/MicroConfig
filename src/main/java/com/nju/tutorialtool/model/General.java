package com.nju.tutorialtool.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.nju.tutorialtool.model.dto.RibbonDTO;

import java.util.HashMap;
import java.util.List;

/**
 * @Author YZ
 * @Date 2018/5/20
 */
public class General {

    /**
     * Map：服务名称，地址
     */
    private HashMap<String, String> services;

    /**
     * 服务的配置
     */
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
    private RibbonDTO ribbonDTO;


    /**
     * 是否含有Zuul
     */
    private boolean isZuul;

    /**
     * 数据库创建、配置信息
     */
    private MysqlInfo mysqlInfo;

    public ServerInfo getServerInfo() {
        return serverInfo;
    }

    public void setServerInfo(ServerInfo serverInfo) {
        this.serverInfo = serverInfo;
    }

    /**
     * 项目部署路径及详细信息
     */
    private ServerInfo serverInfo;

    public MysqlInfo getMysqlInfo() {
        return mysqlInfo;
    }

    public void setMysqlInfo(MysqlInfo mysqlInfo) {
        this.mysqlInfo = mysqlInfo;
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

    public HashMap<String, String> getServices() {

        return services;
    }

    public void setServices(HashMap<String, String> services) {
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
}