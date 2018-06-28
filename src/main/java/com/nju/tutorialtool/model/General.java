package com.nju.tutorialtool.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.nju.tutorialtool.model.dto.RibbonDTO;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

/**
 * @Author YZ
 * @Date 2018/5/20
 */
public class General {

    private Long id;

    /**
     * Map：服务名称，文件夹名称
     */
    private List<ServiceInfo> services;

    /**
     * 是否含有EurekaServer
     */
    private boolean isEurekaServer;

    /**
     * 包含创建eureka server的信息
     */

    private SpringCloudInfo eurekaServerInfo;

    /**
     * 是否含有ribbon
     */
    private boolean isRibbon;

    /**
     * RibbonDTO
     */
//    @JSONField(name = "ribbon")

    private List<RibbonDTO> ribbonDTOList;

    /**
     * 是否含有hystrix
     */
    private boolean isHystrix;

    /**
     * 是否含有Zuul
     */
    private boolean isZuul;

    /**
     * 创建zuul的信息
     */

    private SpringCloudInfo zuulInfo;

    /**
     * 创建docker-compose.yml所需信息
     */

    private ComposeInfo composeInfo;

    /**
     * 指定方法添加熔断，key=某个service，value=该service中需要添加熔断的方法名称
     */

    private Map<String, List<String>> methods;

    public Map<String, List<String>> getMethods() {
        return methods;
    }

    public void setMethods(Map<String, List<String>> methods) {
        this.methods = methods;
    }

    public General() {
    }

    public ComposeInfo getComposeInfo() {
        return composeInfo;
    }

    public void setComposeInfo(ComposeInfo composeInfo) {
        this.composeInfo = composeInfo;
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

    public List<ServiceInfo> getServices() {
        return services;
    }

    public void setServices(List<ServiceInfo> services) {
        this.services = services;
    }

    public SpringCloudInfo getEurekaServerInfo() {
        return eurekaServerInfo;
    }

    public void setEurekaServerInfo(SpringCloudInfo eurekaServerInfo) {
        this.eurekaServerInfo = eurekaServerInfo;
    }

    public List<RibbonDTO> getRibbonDTOList() {
        return ribbonDTOList;
    }

    public void setRibbonDTOList(List<RibbonDTO> ribbonDTOList) {
        this.ribbonDTOList = ribbonDTOList;
    }

    public SpringCloudInfo getZuulInfo() {
        return zuulInfo;
    }

    public void setZuulInfo(SpringCloudInfo zuulInfo) {
        this.zuulInfo = zuulInfo;
    }
}