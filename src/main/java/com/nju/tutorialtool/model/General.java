package com.nju.tutorialtool.model;

import java.util.HashMap;
import java.util.List;

/**
 * @Author YZ
 * @Date 2018/5/20
 */
public class General {
    /**
     * Map：服务名称，地址
     * 修改为Configuration //Map：服务名称，配置Map<配置名称，配置值>
     * 组件选择boolean
     * RabbitMQ: src,dest
     * Ribbon: consumer:String; provider: List<String>
     */
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
     * 是否含有Zuul
     */
    private boolean isZuul;

    /**
     * zuul Consumer, 服务名称
     */
    private String zuulComsumer;

    /**
     * zuul Providers, 所有提供服务的名称列表
     */
    private List<String> zuulProviders;

    /**
     * 是否含有hystrix
     */
    private boolean isHystrix;

    /**
     * 是否含有rabbitmq
     */
    private boolean isRabbitMQ;

    /**
     * mq的服务名称
     */
    private String mqServiceName;

    /**
     * mq的发送方
     */
    private String mqSrc;

    /**
     * mq的接收方
     */
    private String mqDest;

    /**
     * 是否含有ribbon
     */
    private boolean isRibbon;

    public boolean isRibbon() {
        return isRibbon;
    }

    public void setRibbon(boolean ribbon) {
        isRibbon = ribbon;
    }

    public String getMqDest() {

        return mqDest;
    }

    public void setMqDest(String mqDest) {
        this.mqDest = mqDest;
    }

    public String getMqSrc() {

        return mqSrc;
    }

    public String getMqServiceName() {
        return mqServiceName;
    }

    public void setMqServiceName(String mqServiceName) {
        this.mqServiceName = mqServiceName;
    }

    public void setMqSrc(String mqSrc) {
        this.mqSrc = mqSrc;
    }

    public boolean isRabbitMQ() {

        return isRabbitMQ;
    }

    public void setRabbitMQ(boolean rabbitMQ) {
        isRabbitMQ = rabbitMQ;
    }

    public boolean isHystrix() {

        return isHystrix;
    }

    public void setHystrix(boolean hystrix) {
        isHystrix = hystrix;
    }

    public List<String> getZuulProviders() {

        return zuulProviders;
    }

    public void setZuulProviders(List<String> zuulProviders) {
        this.zuulProviders = zuulProviders;
    }

    public boolean isZuul() {

        return isZuul;
    }

    public void setZuul(boolean zuul) {
        isZuul = zuul;
    }

    public String getZuulComsumer() {

        return zuulComsumer;
    }

    public void setZuulComsumer(String zuulComsumer) {
        this.zuulComsumer = zuulComsumer;
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
}