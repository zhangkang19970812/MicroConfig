package com.nju.tutorialtool.util.exec;

public class RunCommand {
    public static boolean isLinux() {
        String osName = System.getProperty("os.name");
        return (!osName.contains("Windows"));
    }

    public static void runCommand(String path, String windowsCommand, String linuxCommand) {
        try {
            String[] cmd = new String[3];
            if (!isLinux()) {
                cmd[0] = "cmd";
                cmd[1] = "/C";
                cmd[2] = "cd " + path + " && " + windowsCommand;
            }
            else {
                cmd[0] = "/bin/bash";
                cmd[1] = "/C";
                cmd[2] = "cd " + path + " && " + linuxCommand;
            }
            Runtime runtime=Runtime.getRuntime();
//            runtime.exec("cd "+ projectPath +" && mvn clean package -Dmaven.test.skip=true");
            Process ps = runtime.exec(cmd);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
