package com.nju.tutorialtool.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Configuration {
    @Id
    @GeneratedValue
    private Long id;
    private String projectPath;

    @OneToMany
    private List<ConfigurationItem> list;

    public Configuration(){}

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
