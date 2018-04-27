package com.nju.tutorialtool.util;

import java.util.List;

public class DependencyConstant {
    public static String eurekaServer = "<dependency>\r\n" +
            "\t\t\t<groupId>org.springframework.cloud</groupId>\r\n" +
            "\t\t\t<artifactId>spring-cloud-eureka-server</artifactId>\r\n" +
            "\t\t</dependency>\r\n";
    public static String eurekaDiscovery = "<dependency>\r\n" +
            "\t\t\t<groupId>org.springframework.cloud</groupId>\r\n" +
            "\t\t\t<artifactId>spring-cloud-starter-eureka</artifactId>\r\n" +
            "\t\t</dependency>\r\n";
    public static String zuul = "<dependency>\r\n" +
            "\t\t\t<groupId>org.springframework.cloud</groupId>\r\n" +
            "\t\t\t<artifactId>spring-cloud-starter-zuul</artifactId>\r\n" +
            "\t\t</dependency>\r\n";
    public static String hystrix = "<dependency>\r\n" +
            "\t\t\t<groupId>org.springframework.cloud</groupId>\r\n" +
            "\t\t\t<artifactId>spring-cloud-starter-hystrix</artifactId>\r\n" +
            "\t\t</dependency>\r\n";
    public static String configServer = "<dependency>\r\n" +
            "\t\t\t<groupId>org.springframework.cloud</groupId>\r\n" +
            "\t\t\t<artifactId>spring-cloud-config-server</artifactId>\r\n" +
            "\t\t</dependency>\r\n";
    public static String configClient = "<dependency>\r\n" +
            "\t\t\t<groupId>org.springframework.cloud</groupId>\r\n" +
            "\t\t\t<artifactId>spring-cloud-starter-config</artifactId>\r\n" +
            "\t\t</dependency>\r\n";
    public static String actuator = "<dependency>\r\n" +
            "\t\t\t<groupId>org.springframework.boot</groupId>\r\n" +
            "\t\t\t<artifactId>spring-boot-starter-actuator</artifactId>\r\n" +
            "\t\t</dependency>\r\n";
    public static String rabbitMQ = "<dependency>\r\n" +
            "\t\t\t<groupId>org.springframework.boot</groupId>\r\n" +
            "\t\t\t<artifactId>spring-boot-starter-amqp</artifactId>\r\n" +
            "\t\t</dependency>\r\n";

    private static String getDependency(String name) {
        String res = "";
        switch (name){
            case "eurekaServer":
                res = "<dependency>\r\n" +
                        "\t\t\t<groupId>org.springframework.cloud</groupId>\r\n" +
                        "\t\t\t<artifactId>spring-cloud-eureka-server</artifactId>\r\n" +
                        "\t\t</dependency>\r\n";
                break;
            case "eurekaDiscovery":
                res = "<dependency>\r\n" +
                        "\t\t\t<groupId>org.springframework.cloud</groupId>\r\n" +
                        "\t\t\t<artifactId>spring-cloud-starter-eureka</artifactId>\r\n" +
                        "\t\t</dependency>\r\n";
                break;
            case "zuul":
                res = "<dependency>\r\n" +
                        "\t\t\t<groupId>org.springframework.cloud</groupId>\r\n" +
                        "\t\t\t<artifactId>spring-cloud-starter-zuul</artifactId>\r\n" +
                        "\t\t</dependency>\r\n";
                break;
            case "hystrix":
                res = "<dependency>\r\n" +
                        "\t\t\t<groupId>org.springframework.cloud</groupId>\r\n" +
                        "\t\t\t<artifactId>spring-cloud-starter-hystrix</artifactId>\r\n" +
                        "\t\t</dependency>\r\n";
                break;
            case "configServer":
                res = "<dependency>\r\n" +
                        "\t\t\t<groupId>org.springframework.cloud</groupId>\r\n" +
                        "\t\t\t<artifactId>spring-cloud-config-server</artifactId>\r\n" +
                        "\t\t</dependency>\r\n";
                break;
            case "configClient":
                res = "<dependency>\r\n" +
                        "\t\t\t<groupId>org.springframework.cloud</groupId>\r\n" +
                        "\t\t\t<artifactId>spring-cloud-starter-config</artifactId>\r\n" +
                        "\t\t</dependency>\r\n";
                break;
            case "rabbitMQ":
                res = "<dependency>\r\n" +
                        "\t\t\t<groupId>org.springframework.boot</groupId>\r\n" +
                        "\t\t\t<artifactId>spring-boot-starter-amqp</artifactId>\r\n" +
                        "\t\t</dependency>\r\n";
                break;
        }
        return res;
    }

    public static String getDependencies(List<String> list) {
        StringBuilder res = new StringBuilder();
        for (String name: list) {
            res.append(getDependency(name));
        }
        return res.toString();
    }
}
