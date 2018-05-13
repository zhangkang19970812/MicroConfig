package com.nju.tutorialtool.service;

import com.nju.tutorialtool.model.ServerInfo;
import com.nju.tutorialtool.util.uploadfile.ServerLogin;
import org.springframework.stereotype.Service;

@Service
public class UploadService {
    public void upload(ServerInfo serverInfo) {
        ServerLogin.downLoadFile(serverInfo.getIp(), serverInfo.getUser(), serverInfo.getPassword(), serverInfo.getPort(), "", "", serverInfo.getSourcePath(), serverInfo.getDestinationPath());
    }
}
