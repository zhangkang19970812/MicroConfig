package com.nju.tutorialtool.model;

/**
 * @author Shenmiu
 * @date 2018/05/26
 *
 * 部署微服务的服务器的信息
 */
public class DeployServer {

    /**
     * 部署服务器的用户名
     */
    private String username;

    /**
     * 部署服务器的密码
     */
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
