package com.nju.tutorialtool.vo;

public class ContainerVO {
    private String id;
    private String name;
    private String image;
    private String status;
    private String port;
    private String created;
    private short isTask;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public short getIsTask() {
        return isTask;
    }

    public void setIsTask(short isTask) {
        this.isTask = isTask;
    }
}
