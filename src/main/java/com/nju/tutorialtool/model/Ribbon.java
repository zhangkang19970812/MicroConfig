package com.nju.tutorialtool.model;

import com.nju.tutorialtool.model.dto.RibbonDTO;
import com.nju.tutorialtool.util.enums.BaseDirConstant;

import java.util.List;

public class Ribbon {
    private String consumerPath;
    private List<String> providerPath;

    public Ribbon(String consumerPath, List<String> providerPath) {
        this.consumerPath = consumerPath;
        this.providerPath = providerPath;
    }

<<<<<<< HEAD
    public Ribbon(RibbonDTO ribbonDTO, List<ServiceInfo> services) {
//        consumerPath = services.get(ribbonDTO.getConsumer());
//        ribbonDTO.getProviders().forEach(provider -> providerPath.add(services.get(provider)));
        for (ServiceInfo serviceInfo : services) {
            if (serviceInfo.getServiceName().equals(ribbonDTO.getConsumer())) {
                consumerPath = BaseDirConstant.projectBaseDir + "/" + serviceInfo.getFolderName();
                continue;
            }
            for (String provider: ribbonDTO.getProviders()) {
                if (serviceInfo.getServiceName().equals(provider)) {
                    providerPath.add(BaseDirConstant.projectBaseDir + "/" + serviceInfo.getFolderName());
                    break;
                }
            }
        }
    }

=======
>>>>>>> origin/master
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
