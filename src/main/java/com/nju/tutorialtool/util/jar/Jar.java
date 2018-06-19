package com.nju.tutorialtool.util.jar;

import com.nju.tutorialtool.util.exec.RunCommand;

public class Jar {
    /**
     * 调用cmd的maven打包命令
     * @param projectPath
     */
    public static void execCMD(String projectPath){
        RunCommand.runCommand(projectPath, "mvn clean package -Dmaven.test.skip=true", "mvn clean package -Dmaven.test.skip=true");
    }

//    public static void main(String[] args) {
//        execCMD("C:/Users/zk/Desktop/test/account_service");
//    }
}
