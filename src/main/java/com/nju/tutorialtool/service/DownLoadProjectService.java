package com.nju.tutorialtool.service;

import com.nju.tutorialtool.model.ServerInfo;
import com.nju.tutorialtool.model.ServiceInfo;
import com.nju.tutorialtool.util.downloadFile.DownLoadFile;
import com.nju.tutorialtool.util.enums.BaseDirConstant;
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
        DownLoadFile.downloadFile("http://" + serverInfo.getIp() + ":" + serverInfo.getPort() + BaseDirConstant.projectBaseDir + "/" + folderName + ".zip",
                localPath + "/" + folderName + ".zip");

    }
}
