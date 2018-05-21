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
     * Zuul: consumer:String; provider: List<String>
     */
    HashMap<String,String> services;
    List<Configuration> configurationList;
    boolean isEurekaServer;
    boolean isZuul;
    String zuulComsumer;
    List<String> zuulProviders;
    boolean isHystrix;
    boolean isRabbitMQ;
    String mqServiceName,mqSrc,mqDest;
    boolean isRibbon;

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
}