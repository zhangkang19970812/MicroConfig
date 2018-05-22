package com.nju.tutorialtool.util.jar;

import java.io.*;

public class Jar {
    /**
     * 调用cmd的maven打包命令
     * @param dir
     */
    public static void execCMD(File dir){
        Runtime runtime=Runtime.getRuntime();
        try {
            runtime.exec("mvn clean package -Dmaven.test.skip=true", null, dir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
