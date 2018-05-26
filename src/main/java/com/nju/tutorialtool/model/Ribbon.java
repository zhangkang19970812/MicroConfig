package com.nju.tutorialtool.model;

import com.nju.tutorialtool.model.dto.RibbonDTO;

import java.util.List;
import java.util.Map;

public class Ribbon {
    private String consumerPath;
    private List<String> providerPath;

    public Ribbon(String consumerPath, List<String> providerPath) {
        this.consumerPath = consumerPath;
        this.providerPath = providerPath;
    }

    public Ribbon(RibbonDTO ribbonDTO, Map<String, String> services) {
        consumerPath = services.get(ribbonDTO.getConsumer());
        ribbonDTO.getProviders().forEach(provider -> providerPath.add(services.get(provider)));
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
