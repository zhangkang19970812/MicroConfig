package com.nju.tutorialtool.model;

import java.util.List;

public class MysqlInfo {
    private String baseDir;
    private String projectName;
    private String database;
    private List<Table> tables;
    private String user;
    private String password;

    public MysqlInfo(String baseDir, String projectName, String database, List<Table> tables, String user, String password) {
        this.baseDir = baseDir;
        this.projectName = projectName;
        this.database = database;
        this.tables = tables;
        this.user = user;
        this.password = password;
    }

    public String getBaseDir() {
        return baseDir;
    }

    public void setBaseDir(String baseDir) {
        this.baseDir = baseDir;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public List<Table> getTables() {
        return tables;
    }

    public void setTables(List<Table> tables) {
        this.tables = tables;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
