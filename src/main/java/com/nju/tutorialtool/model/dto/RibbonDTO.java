package com.nju.tutorialtool.model.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.nju.tutorialtool.model.RibbonRule;

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
    @JSONField(deserialize = false)
    private Long id;
    /**
     * ribbon consumer的服务名称
     */
    private String consumer;

    /**
     * 所有ribbon providers的服务名称和各自对应的负载均衡规则
     */
    @OneToMany
    private List<RibbonRule> providers;

    public RibbonDTO() {
    }

    public String getConsumer() {
        return consumer;
    }

    public void setConsumer(String consumer) {
        this.consumer = consumer;
    }

    public List<RibbonRule> getProviders() {
        return providers;
    }

    public void setProviders(List<RibbonRule> providers) {
        this.providers = providers;
    }
}
