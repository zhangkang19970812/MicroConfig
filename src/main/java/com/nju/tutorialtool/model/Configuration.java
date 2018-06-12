package com.nju.tutorialtool.model;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Configuration {
    @Id
    @GeneratedValue
    @JSONField(deserialize = false)
    private Long id;

    @OneToMany
    private List<ConfigurationItem> list;

    public Configuration() {
    }

    public Configuration(List<ConfigurationItem> list) {
        this.list = list;
    }

    public List<ConfigurationItem> getList() {
        return list;
    }

    public void setList(List<ConfigurationItem> list) {
        this.list = list;
    }

    public List<ConfigurationItem> getSameConfigurationItems(Configuration configuration) {
        List<ConfigurationItem> resList = new ArrayList<>();
        for (ConfigurationItem configurationItem : configuration.getList()) {
            if ("server.port".equals(configurationItem.getItemName()) || "spring.application.name".equals(configurationItem.getItemName())) {
                for (ConfigurationItem configurationItem1 : list) {
                    if (configurationItem.equals(configurationItem1)) {
                        resList.add(configurationItem);
                        break;
                    }
                }
            }
        }
        return resList;
    }
}
