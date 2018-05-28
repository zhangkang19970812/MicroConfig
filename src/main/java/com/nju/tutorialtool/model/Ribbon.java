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
