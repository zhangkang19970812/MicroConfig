package com.nju.tutorialtool.model;

/**
 * projectPath: 要创建Dockerfile的项目地址
 * projectType: 要创建Dockerfile的项目类型，分为service和mysql两种
 */
public class DockerfileInfo {
    private String projectPath;
    private String projectType;

    public DockerfileInfo(String projectPath, String projectType) {
        this.projectPath = projectPath;
        this.projectType = projectType;
    }

    public String getProjectPath() {
        return projectPath;
    }

    public void setProjectPath(String projectPath) {
        this.projectPath = projectPath;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }
}
