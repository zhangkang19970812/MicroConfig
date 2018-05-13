package com.nju.tutorialtool.model;

import java.util.HashMap;
import java.util.Map;

public class Table {
    private String tableName;
    private Map<String, String> columns = new HashMap<>();
    private String primary_key;

    public Table(String tableName, Map<String, String> columns, String primary_key) {
        this.tableName = tableName;
        this.columns = columns;
        this.primary_key = primary_key;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Map<String, String> getColumns() {
        return columns;
    }

    public void setColumns(Map<String, String> columns) {
        this.columns = columns;
    }

    public String getPrimary_key() {
        return primary_key;
    }

    public void setPrimary_key(String primary_key) {
        this.primary_key = primary_key;
    }
}
