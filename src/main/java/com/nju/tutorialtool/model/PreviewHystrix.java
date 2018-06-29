package com.nju.tutorialtool.model;

import java.util.List;
import java.util.Map;

/**
 * @Author YZ
 * @Date 28/06/2018
 */
public class PreviewHystrix {
    //包含service name，文件夹name
    private List<ServiceInfo> serviceInfoList;
    //指定的方法名列表
    private Map<String,List<String>> methodsMap;

    public List<ServiceInfo> getServiceInfoList() {
        return serviceInfoList;
    }

    public void setServiceInfoList(List<ServiceInfo> serviceInfoList) {
        this.serviceInfoList = serviceInfoList;
    }

    public Map<String, List<String>> getMethodsMap() {
        return methodsMap;
    }

    public void setMethodsMap(Map<String, List<String>> methodsMap) {
        this.methodsMap = methodsMap;
    }
}
