package com.nju.tutorialtool.service;

import com.nju.tutorialtool.util.downloadFile.FTPDownload;
import com.nju.tutorialtool.util.enums.BaseDirConstant;
import com.nju.tutorialtool.util.enums.ServerMessage;
import com.nju.tutorialtool.util.exec.RunCommand;
import com.nju.tutorialtool.util.ssh.RemoteExecuteCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class DownLoadProjectService {
    @Autowired
    private UserService userService;

    public InputStream downloadProjects() {
        RemoteExecuteCommand remote = new RemoteExecuteCommand(ServerMessage.serverIp, ServerMessage.serverUser, ServerMessage.serverPassword);
//        System.out.println(remote.execute("cd " + BaseDirConstant.servicesDir + " && zip -r " + BaseDirConstant.zipName + " ./*"));
        return FTPDownload.download(BaseDirConstant.ftpDir + "/" + BaseDirConstant.zipName);
    }

    public InputStream getDownload() throws IOException {
        RunCommand.runCommand(userService.getUserFolder(), "", "rm -rf " + BaseDirConstant.zipName + " && zip -r " + BaseDirConstant.zipName + " ./*");
        return new FileInputStream(new File(userService.getUserFolder() + File.separator + BaseDirConstant.zipName));
    }


//    public static void main(String[] args) throws Exception {
//        File file = new File("C:/Users/zk/Desktop/1.txt");
//        InputStream inputStream = new FileInputStream(file);
//        RunCommand.runCommand("C:\\Users\\zk\\Desktop", "del/f/s/q 1.txt", "");
//        System.out.println();
//    }
}
