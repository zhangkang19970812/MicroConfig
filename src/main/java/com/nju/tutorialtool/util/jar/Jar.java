package com.nju.tutorialtool.util.jar;

import com.nju.tutorialtool.util.exec.RunCommand;

import java.io.*;

public class Jar {
    /**
     * 调用cmd的maven打包命令
     * @param projectPath
     */
    public static void execCMD(String projectPath){
//        String osName = System.getProperty("os.name");
//        try {
//            String[] cmd = new String[3];
//            if (osName.contains("Windows")) {
//                cmd[0] = "cmd";
//                cmd[1] = "/C";
//                cmd[2] = "cd " + projectPath + " && mvn clean package -Dmaven.test.skip=true";
//            }
//            else if (osName.contains("Mac") || osName.contains("Linux")) {
//                cmd[0] = "/bin/bash";
//                cmd[1] = "/C";
//                cmd[2] = "cd " + projectPath + " && mvn clean package -Dmaven.test.skip=true";
//            }
//            Runtime runtime=Runtime.getRuntime();
////            runtime.exec("cd "+ projectPath +" && mvn clean package -Dmaven.test.skip=true");
//            Process ps = runtime.exec(cmd);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        RunCommand.runCommand(projectPath, "mvn clean package -Dmaven.test.skip=true", "mvn clean package -Dmaven.test.skip=true");
    }

    public static void main(String[] args) {
        Jar.execCMD("H:/programs/account_service");
    }
}
