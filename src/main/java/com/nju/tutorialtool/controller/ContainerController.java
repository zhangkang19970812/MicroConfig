package com.nju.tutorialtool.controller;

import com.nju.tutorialtool.model.DeployServer;
import com.nju.tutorialtool.model.ServerInfo;
import com.nju.tutorialtool.model.ServiceInfo;
import com.nju.tutorialtool.service.*;
import com.nju.tutorialtool.util.enums.BaseDirConstant;
import com.nju.tutorialtool.vo.ContainerVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.List;

@RestController
@RequestMapping("/container")
public class ContainerController {
    @Autowired
    private ImageService imageService;
    @Autowired
    private ContainerService containerService;
    @Autowired
    private ServiceDirMapService serviceDirMapService;
    @Autowired
    private DeployServerService deployServerService;
    @Autowired
    private UploadService uploadService;

    @RequestMapping("/showAllContainerInfo")
    public List<ContainerVO> showContainerList() {
        return containerService.getContainerList(getServerInfo());
    }

    @PostMapping("/deploy")
    public void uploadAndBuildImageAndDeploy() {
        uploadAllServices();
        uploadComposeFile();
        buildDockerImage();
        deployContainers();
    }

    private void deployContainers() {
        containerService.deployContainerByCompose(BaseDirConstant.projectBaseDir, getServerInfo());
    }

    private void buildDockerImage() {
        List<ServiceInfo> serviceInfoList = serviceDirMapService.getAllServices();
        for (ServiceInfo serviceInfo : serviceInfoList) {
            imageService.buildImageByDockerfile(getServicePath(serviceInfo), getServerInfo(), serviceInfo.getServiceName());
        }
    }

    private void uploadAllServices() {
        List<ServiceInfo> serviceInfoList = serviceDirMapService.getAllServices();
        for (ServiceInfo serviceInfo : serviceInfoList) {
            uploadService.upload(new ServerInfo(getServerInfo().getIp(), getServerInfo().getUser(), getServerInfo().getPassword(), "", getServicePath(serviceInfo)));
        }
    }

    private void uploadComposeFile() {
        uploadService.upload(new ServerInfo(getServerInfo().getIp(), getServerInfo().getUser(), getServerInfo().getPassword(), "", BaseDirConstant.projectBaseDir + File.separator + "docker-compose.yml"));
    }

    private ServerInfo getServerInfo() {
        DeployServer deployServer = deployServerService.getFirst();
        return new ServerInfo(deployServer.getIp(), deployServer.getUsername(), deployServer.getPassword(), "", "");
    }

    private String getServicePath(ServiceInfo serviceInfo) {
        return BaseDirConstant.projectBaseDir + File.separator + serviceInfo.getFolderName();
    }
}
