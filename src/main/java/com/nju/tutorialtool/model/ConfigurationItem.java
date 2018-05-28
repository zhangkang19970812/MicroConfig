package com.nju.tutorialtool.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ConfigurationItem {

    @Id
    @GeneratedValue
    private Long id;

    private String itemName;
    private String value;

    public ConfigurationItem(){}

    public ConfigurationItem(String itemName, String value) {
        this.itemName = itemName;
        this.value = value;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return itemName + "=" + value;
    }
}
