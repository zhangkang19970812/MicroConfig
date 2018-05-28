package com.nju.tutorialtool.model.dto;

import javax.persistence.*;
import java.util.List;

/**
 * @author Shenmiu
 * @date 2018/05/26
 */
@Entity
public class RibbonDTO {
    @Id
    @GeneratedValue
    private Long id;
    /**
     * ribbon consumer的服务名称
     */
    private String consumer;

    /**
     * 所有ribbon providers的服务名称
     */
    @ElementCollection
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
