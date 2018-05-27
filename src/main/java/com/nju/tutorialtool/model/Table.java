package com.nju.tutorialtool.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.Map;

@Entity
public class Table {
    @Id
    @GeneratedValue
    private Long id;

    private String tableName;

    @OneToMany
    private List<Column> columnList;
    private String primary_key;

    public Table(String tableName, List<Column> columnList, String primary_key) {
        this.tableName = tableName;
        this.columnList = columnList;
        this.primary_key = primary_key;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<Column> getColumnList() {
        return columnList;
    }

    public void setColumnList(List<Column> columnList) {
        this.columnList = columnList;
    }

    public String getPrimary_key() {
        return primary_key;
    }

    public void setPrimary_key(String primary_key) {
        this.primary_key = primary_key;
    }
}
