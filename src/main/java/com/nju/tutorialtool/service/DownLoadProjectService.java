package com.nju.tutorialtool.service;

import com.nju.tutorialtool.model.ServerInfo;
import com.nju.tutorialtool.model.ServiceInfo;
import com.nju.tutorialtool.util.downloadFile.FTPDownload;
import com.nju.tutorialtool.util.enums.BaseDirConstant;
import com.nju.tutorialtool.util.enums.ServerMessage;
import com.nju.tutorialtool.util.ssh.RemoteExecuteCommand;
import org.apache.catalina.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;

@Service
public class DownLoadProjectService {
    @Autowired
    private ServiceDirMapService serviceDirMapService;

    public InputStream downloadProjects() {
        RemoteExecuteCommand remote = new RemoteExecuteCommand(ServerMessage.serverIp, ServerMessage.serverUser, ServerMessage.serverPassword);
//        System.out.println(remote.execute("cd " + BaseDirConstant.servicesDir + " && zip -r " + BaseDirConstant.zipName + " ./*"));
        return FTPDownload.download(BaseDirConstant.ftpDir + "/" + BaseDirConstant.zipName);

    }


//    public static void main(String[] args) throws Exception {
//        DownLoadProjectService downLoadProjectService = new DownLoadProjectService();
//        InputStream inputStream = downLoadProjectService.downloadProject(new ServerInfo("114.115.137.102", "root", "stk0123STK0123", "", ""), "account_service", "H:/programs/spark");
////        downLoadProjectService.test(new ServerInfo("114.115.137.102", "root", "stk0123STK0123", "80", ""), "account_service", "H:/programs/spark");
//        System.out.println(inputStream.available());
//    }
}
