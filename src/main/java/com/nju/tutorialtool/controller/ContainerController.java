package com.nju.tutorialtool.controller;

import com.nju.tutorialtool.model.ServerInfo;
import com.nju.tutorialtool.service.ContainerService;
import com.nju.tutorialtool.vo.ContainerVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ContainerController {
    @Autowired
    private ContainerService containerService;


//    @PostMapping("/deployContainer")
//    public String deployContainer(String rep, String tag, String port, int ownerId, int serverId) {
//        ServerEntity server = serverService.getServerEntity(serverId).get();
//        if (server.getOwnerId() == ownerId) {
//            return containerService.deployContainer(rep, tag, port, serverId);
//        } else {
//            return "access_denied";
//        }
//    }

    @PostMapping("deployContainerByCompose")
    public String deployContainerByCompose(String composeFileUrl, ServerInfo serverInfo) {
        return containerService.deployContainerByCompose(composeFileUrl, serverInfo);
    }

    /**
     *
     * @param serverInfo
     * @return ContainerVO的List，前端获取成JSON
     */
    @PostMapping("getContainerList")
    public List<ContainerVO> getContainerList(ServerInfo serverInfo){
        return containerService.getContainerList(serverInfo);
    }
}
