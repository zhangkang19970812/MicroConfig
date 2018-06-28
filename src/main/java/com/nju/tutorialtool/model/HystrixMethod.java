package com.nju.tutorialtool.model;

import java.util.List;

/**
 * @Author YZ
 * @Date 28/06/2018
 */
public class HystrixMethod {
    //服务名称
    private String serviceName;
    //Controller文件的名称
    private String controllerName;
    //Controller文件中的所有方法名
    private List<String> methodNames;

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getControllerName() {
        return controllerName;
    }

    public void setControllerName(String controllerName) {
        this.controllerName = controllerName;
    }

    public List<String> getMethodNames() {
        return methodNames;
    }

    public void setMethodNames(List<String> methodNames) {
        this.methodNames = methodNames;
    }
}
