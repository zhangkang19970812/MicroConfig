package com.nju.tutorialtool.model;

/**
 * @author Shenmiu
 * @date 2018/05/21
 * <p>
 * 包含EurekaServer的信息:
 * baseDir
 * groupId
 * artifactId
 */
public class EurekaServerInfo {

    /**
     * 目标文件夹
     */
    private String baseDir;

    /**
     * GroupId
     */
    private String groupId;

    /**
     * ArtifactId
     */
    private String artifactId;

    public String getBaseDir() {
        return baseDir;
    }

    public void setBaseDir(String baseDir) {
        this.baseDir = baseDir;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }
}
