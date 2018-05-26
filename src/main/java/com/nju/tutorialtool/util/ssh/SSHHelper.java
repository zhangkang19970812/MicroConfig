package com.nju.tutorialtool.util.ssh;

/**
 * Created by marioquer on 2018/4/21.
 */

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.io.*;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class SSHHelper {

    private static int SESSION_TIMEOUT = 30000;
    private static int CHANNEL_TIMEOUT = 3000;
    private String pathPrefix;
    private SSHInfo sshInfo;
    private Session session;
    private JSch jSch;

    public SSHHelper(SSHInfo sshInfo) {
        pathPrefix = Class.class.getClass().getResource("/").getPath();
        this.sshInfo = sshInfo;
        jSch = new JSch();
    }

    public int connect() {
        long start = System.currentTimeMillis();
        try {
            session = jSch.getSession(sshInfo.getUser(), sshInfo.getHost(), sshInfo.getPort());
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.setPassword(sshInfo.getPwd());
            session.connect(SESSION_TIMEOUT);
        } catch (JSchException e) {
            System.out.println(e.getMessage());
            if (e.getMessage().startsWith("java.net.UnknownHostException"))
                return -2; // wrong setting
            else
                return -1; // wrong psw
        }
        System.out.println((System.currentTimeMillis() - start) + "ms");
        return 0;
    }

    public void close() {
        if (session != null && session.isConnected()) {
            session.disconnect();
        }
    }

    /**
     * 远程 执行命令并返回结果调用过程 是同步的（执行完才会返回）
     *
     * @param command 命令
     * @return
     */
    public String exec(String command) {
        long start = System.currentTimeMillis();
        String result = "";
        ChannelExec openChannel = null;
        try {
            if (session == null || !session.isConnected()) {
                this.connect();
            }
            openChannel = (ChannelExec) session.openChannel("exec");
            openChannel.setCommand(command);
            InputStream in = openChannel.getInputStream();
            openChannel.connect(CHANNEL_TIMEOUT);
            int exitStatus = openChannel.getExitStatus(); // -1,0
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String buf = null;
            while ((buf = reader.readLine()) != null) {
                result += new String(buf.getBytes("gbk"), "UTF-8") + "\r\n";
            }
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (JSchException | IOException | InterruptedException e) {
            result += e.getMessage();
        } finally {
            if (openChannel != null && !openChannel.isClosed()) {
                openChannel.disconnect();
            }
        }
        System.out.println((System.currentTimeMillis() - start) + "ms");
        return result;
    }

    /**
     * 从文件中执行shell脚本
     *
     * @param url 文件url
     * @return
     */
    public synchronized String execFromFile(String url) {
        FileReader fileReader = null;
        String command = "";
        try {
            fileReader = new FileReader(pathPrefix + url);
            Scanner scanner = new Scanner(fileReader);
            while (scanner.hasNext()) {
                command = command + scanner.nextLine() + "\n";
            }
            scanner.close();
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return exec(command);
    }

    public SSHInfo getSshInfo() {
        return sshInfo;
    }

    public Session getSession() {
        return session;
    }

    public void setSshInfo(SSHInfo sshInfo) {
        this.sshInfo = sshInfo;
    }

    public static void main(String args[]) {
        SSHInfo sshInfo = new SSHInfo("139.196.87.209", "root", "Qq6655355", 22);
        SSHInfo sshInfo1 = new SSHInfo("138.68.54.186", "root", "Qq6655355", 22); // do
        SSHHelper sshHelper = new SSHHelper(sshInfo);
        SSHHelper sshHelper1 = new SSHHelper(sshInfo1);
        sshHelper.connect();
        sshHelper1.connect();
        String output1 = sshHelper1.exec("docker info");
        System.out.println(output1);
        String[] outputlist = output1.split("\\n|\\r\\n|\\r");
        for (String i : outputlist) {
            System.out.println(i + "1");
        }
//        String output2 = sshHelper.exec("sudo apt-get update");
//        System.out.println(output2);
        sshHelper.close();
        sshHelper1.close();
    }
}
