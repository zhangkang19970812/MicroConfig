package com.nju.tutorialtool.service;

import com.nju.tutorialtool.model.ServerInfo;
import com.nju.tutorialtool.util.enums.BaseDirConstant;
import com.nju.tutorialtool.util.uploadfile.ServerLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class UploadService {
    @Autowired
    private UserService userService;

    public void upload(ServerInfo serverInfo) {
        String id = userService.addUser();
        ServerLogin.downLoadFile(serverInfo.getIp(), serverInfo.getUser(), serverInfo.getPassword(), serverInfo.getPort(), "", "", serverInfo.getSourcePath(), BaseDirConstant.projectBaseDir + File.separator + "user" + id);
    }
}
