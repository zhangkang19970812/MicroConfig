package com.nju.tutorialtool.controller;

import com.nju.tutorialtool.model.DownloadInfo;
import com.nju.tutorialtool.service.DownLoadProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DownloadController {
    @Autowired
    private DownLoadProjectService downLoadProjectService;

    @PostMapping("/download")
    public void downloadAllProejects(@RequestBody DownloadInfo downloadInfo) {
        downLoadProjectService.downloadAllProjects(downloadInfo.getServerInfo(), downloadInfo.getLocalPath());
    }
}
