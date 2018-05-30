package com.nju.tutorialtool.service;

import com.nju.tutorialtool.model.ServerInfo;
import com.nju.tutorialtool.model.ServiceInfo;
import com.nju.tutorialtool.util.downloadFile.DownLoadFile;
import com.nju.tutorialtool.util.enums.BaseDirConstant;
import com.nju.tutorialtool.util.exec.RunCommand;
import com.nju.tutorialtool.util.ssh.RemoteExecuteCommand;
import com.nju.tutorialtool.util.ssh.SSHHelper;
import com.nju.tutorialtool.util.ssh.SSHInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DownLoadProjectService {
    @Autowired
    private ServiceDirMapService serviceDirMapService;

    public void downloadAllProjects(ServerInfo serverInfo, String localPath) {
        for (ServiceInfo serviceInfo : serviceDirMapService.getAllServices()) {
            downloadProject(serverInfo, serviceInfo.getFolderName(), localPath);
        }
    }

    public void downloadProject(ServerInfo serverInfo, String folderName, String localPath) {
        RemoteExecuteCommand remote = new RemoteExecuteCommand(serverInfo.getIp(), serverInfo.getUser(), serverInfo.getPassword());
//        if (remote.login()) {
        System.out.println(remote.execute("cd " + "/usr/services" + "/" + folderName + " && zip -r " + folderName + ".zip ./*"));
        remote.getFile("/usr/services" + "/" + folderName + "/" + folderName + ".zip", localPath);
//        }

//        RunCommand.runCommand(localPath, "scp " + serverInfo.getUser() + "@" + serverInfo.getIp() + ":" + "/usr/services" + "/" + folderName + "/" + folderName + ".zip ./",
//                "scp " + serverInfo.getUser() + "@" + serverInfo.getIp() + ":" + "/usr/services" + "/" + folderName + "/" + folderName + ".zip ./");
//        DownLoadFile.downloadFile("http://" + serverInfo.getIp() + ":" + serverInfo.getPort() + "/usr/services" + "/" + folderName + "/" + folderName + ".zip",
//                localPath + "/" + folderName + ".zip");
        RunCommand.runCommand(localPath, "expand " + folderName + ".zip " + folderName, "unzip " + folderName + ".zip");
    }

    public static void main(String[] args) {
        DownLoadProjectService downLoadProjectService = new DownLoadProjectService();
        downLoadProjectService.downloadProject(new ServerInfo("114.115.137.102", "root", "stk0123STK0123", "", ""), "account_service", "H:/programs/spark");
    }
}
