package com.nju.tutorialtool.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class RibbonRule {
    @Id
    @GeneratedValue
    private Long id;

    //提供者服务名称
    private String provider;
    //该提供者负载均衡规则
    private String loadbalancerRule;

    public RibbonRule(String provider, String loadbalancerRule) {
        this.provider = provider;
        this.loadbalancerRule = loadbalancerRule;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getLoadbalancerRule() {
        return loadbalancerRule;
    }

    public void setLoadbalancerRule(String loadbalancerRule) {
        this.loadbalancerRule = loadbalancerRule;
    }
}
