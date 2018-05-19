package com.nju.tutorialtool.model;

import java.util.List;

public class Configuration {
    private String projectPath;
    private List<ConfigurationItem> list;

    public Configuration(String projectPath, List<ConfigurationItem> list) {
        this.projectPath = projectPath;
        this.list = list;
    }

    public String getProjectPath() {
        return projectPath;
    }

    public void setProjectPath(String projectPath) {
        this.projectPath = projectPath;
    }

    public List<ConfigurationItem> getList() {
        return list;
    }

    public void setList(List<ConfigurationItem> list) {
        this.list = list;
    }
}
