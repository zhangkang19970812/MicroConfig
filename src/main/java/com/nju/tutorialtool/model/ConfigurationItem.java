package com.nju.tutorialtool.model;

public class ConfigurationItem {
    private String itemName;
    private String value;

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
