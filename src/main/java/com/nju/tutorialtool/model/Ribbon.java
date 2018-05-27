package com.nju.tutorialtool.model;

import com.nju.tutorialtool.model.dto.RibbonDTO;
import com.nju.tutorialtool.util.enums.BaseDirConstant;

import java.util.List;
import java.util.Map;

public class Ribbon {
    private String consumerPath;
    private List<String> providerPath;

    public Ribbon(String consumerPath, List<String> providerPath) {
        this.consumerPath = consumerPath;
        this.providerPath = providerPath;
    }

    public Ribbon(RibbonDTO ribbonDTO, List<ServiceDirMap> services) {
//        consumerPath = services.get(ribbonDTO.getConsumer());
//        ribbonDTO.getProviders().forEach(provider -> providerPath.add(services.get(provider)));
        for (ServiceDirMap serviceDirMap : services) {
            if (serviceDirMap.getServiceName().equals(ribbonDTO.getConsumer())) {
                consumerPath = BaseDirConstant.projectBaseDir + "/" + serviceDirMap.getDirName();
                continue;
            }
            for (String provider: ribbonDTO.getProviders()) {
                if (serviceDirMap.getServiceName().equals(provider)) {
                    providerPath.add(BaseDirConstant.projectBaseDir + "/" + serviceDirMap.getDirName());
                    break;
                }
            }
        }
    }

    public String getConsumerPath() {
        return consumerPath;
    }

    public void setConsumerPath(String consumerPath) {
        this.consumerPath = consumerPath;
    }

    public List<String> getProviderPath() {
        return providerPath;
    }

    public void setProviderPath(List<String> providerPath) {
        this.providerPath = providerPath;
    }
}
