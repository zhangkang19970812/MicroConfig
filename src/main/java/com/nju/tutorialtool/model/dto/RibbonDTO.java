package com.nju.tutorialtool.model.dto;

import java.util.List;

/**
 * @author Shenmiu
 * @date 2018/05/26
 */
public class RibbonDTO {

    /**
     * ribbon consumer的服务名称
     */
    private String consumer;

    /**
     * 所有ribbon providers的服务名称
     */
    private List<String> providers;

    public String getConsumer() {
        return consumer;
    }

    public void setConsumer(String consumer) {
        this.consumer = consumer;
    }

    public List<String> getProviders() {
        return providers;
    }

    public void setProviders(List<String> providers) {
        this.providers = providers;
    }
}
