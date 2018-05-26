package com.nju.tutorialtool.util.enums;

public class ShellScript {
    public static String DOCKER_INFO = "docker info";
    public static String CONTAINER_LS_A = "docker container ls -a";
    public static String DOCKER_RUN = "docker run";
    public static String CONTAINER_STOP = "docker container stop";
    public static String CONTAINER_RM = "docker container rm";

    public static String IMAGE_LS = "docker image ls";
    public static String IMAGE_BUILD = "docker build";

    public static String SWARM_MANAGER_JOIN = "docker swarm join-token manager";
    public static String SWARM_WORKER_JOIN = "docker swarm join-token worker";

    public static String COMPOSE_UP_BG = "docker-compose up -d";
}
