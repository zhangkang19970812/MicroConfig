package com.nju.tutorialtool.util.jar;

import java.io.*;

public class Jar {
    /**
     * 调用cmd的maven打包命令
     * @param projectPath
     */
    public static void execCMD(String projectPath){
        Runtime runtime=Runtime.getRuntime();
        try {
            runtime.exec("cd "+ projectPath +" && mvn clean package -Dmaven.test.skip=true");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
