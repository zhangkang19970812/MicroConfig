package com.nju.tutorialtool.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author Shenmiu
 * @date 2018/05/21
 * <p>
 * 包含Spring Cloud一系列组件的信息:
 * baseDir
 * groupId
 * artifactId
 */
@Entity
public class SpringCloudInfo {
    @Id
    @GeneratedValue
    private Long id;

    /**
     * 目标文件夹
     */
//    private String baseDir;

    /**
     * GroupId
     */
    private String groupId;

    /**
     * ArtifactId
     */
    private String artifactId;

//    public String getBaseDir() {
//        return baseDir;
//    }
//
//    public void setBaseDir(String baseDir) {
//        this.baseDir = baseDir;
//    }

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
