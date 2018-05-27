package com.nju.tutorialtool.controller;

import com.nju.tutorialtool.model.DeployServer;
import com.nju.tutorialtool.model.ServerInfo;
import com.nju.tutorialtool.model.ServiceDirMap;
import com.nju.tutorialtool.service.ContainerService;
import com.nju.tutorialtool.service.DeployServerService;
import com.nju.tutorialtool.service.ImageService;
import com.nju.tutorialtool.service.ServiceDirMapService;
import com.nju.tutorialtool.util.enums.BaseDirConstant;
import com.nju.tutorialtool.vo.ContainerVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping("/showAllContainerInfo")
    public List<ContainerVO> showContainerList() {
        buildDockerImage();
        containerService.deployContainerByCompose(BaseDirConstant.projectBaseDir, getServerInfo());
        return containerService.getContainerList(getServerInfo());
    }

    private void buildDockerImage() {
        List<ServiceDirMap> serviceDirMapList = serviceDirMapService.getAllServices();
        for (ServiceDirMap serviceDirMap : serviceDirMapList) {
            imageService.buildImageByDockerfile(BaseDirConstant.projectBaseDir + "/" + serviceDirMap.getDirName(), getServerInfo(), serviceDirMap.getServiceName());
        }
    }

    private ServerInfo getServerInfo() {
        DeployServer deployServer = deployServerService.getFirst();
        return new ServerInfo(deployServer.getIp(), deployServer.getUsername(), deployServer.getPassword(), "", "");
    }
}
