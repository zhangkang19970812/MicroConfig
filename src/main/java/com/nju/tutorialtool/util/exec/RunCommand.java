package com.nju.tutorialtool.util.exec;

import java.io.*;

public class RunCommand {
    public static boolean isLinux() {
        String osName = System.getProperty("os.name");
        return (!osName.contains("Windows"));
    }

//    public static void main(String[] args) {
//        runCommand("F:\\account_service", "mvn clean package -Dmaven.test.skip=true", "mvn clean package -Dmaven.test.skip=true");
//    }

    public static void runCommand(String path, String windowsCommand, String linuxCommand) {
        try {
            String[] cmd = new String[3];
            if (!isLinux()) {
                cmd[0] = "cmd";
                cmd[1] = "/c";
                cmd[2] = path.substring(0, path.indexOf(":") + 1) + " && cd " + path + " && " + windowsCommand;
            }
            else {
                cmd[0] = "/bin/bash";
                cmd[1] = "/C";
                cmd[2] = "cd " + path + " && " + linuxCommand;
            }
            Runtime runtime=Runtime.getRuntime();
//            runtime.exec("cd "+ projectPath +" && mvn clean package -Dmaven.test.skip=true");
            Process process = runtime.exec(cmd);
            // 打印程序输出
            readProcessOutput(process);

            // 等待程序执行结束并输出状态
            int exitCode = process.waitFor();

//            if (exitCode == SUCCESS) {
//                System.out.println(SUCCESS_MESSAGE);
//            } else {
//                System.err.println(ERROR_MESSAGE + exitCode);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 打印进程输出
     *
     * @param process 进程
     */
    private static void readProcessOutput(final Process process) {
        // 将进程的正常输出在 System.out 中打印，进程的错误输出在 System.err 中打印
        read(process.getInputStream(), System.out);
        read(process.getErrorStream(), System.err);
    }

    // 读取输入流
    private static void read(InputStream inputStream, PrintStream out) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                out.println(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
