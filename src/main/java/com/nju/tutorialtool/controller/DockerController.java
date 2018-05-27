package com.nju.tutorialtool.controller;

import com.nju.tutorialtool.model.ServerInfo;
import com.nju.tutorialtool.service.ContainerService;
import com.nju.tutorialtool.service.ImageService;
import com.nju.tutorialtool.vo.ContainerVO;
import com.nju.tutorialtool.vo.ImageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Shenmiu
 * @date 2018/05/27
 * <p>
 * 1. 容器管理
 * 2. 镜像管理
 */
@RestController
@RequestMapping("/manage")
public class DockerController {

    @Autowired
    private ImageService imageService;

    @Autowired
    private ContainerService containerService;

    /**
     * 得到某服务器上镜像列表
     *
     * @param serverInfo
     * @return ImageVO的List
     */
    @GetMapping("image")
    public List<ImageVO> getImageList(@RequestBody ServerInfo serverInfo) {
        return imageService.getImageList(serverInfo);
    }

    /**
     * 从Dockerfile路径构建镜像
     *
     * @param dockerfileUrl Dockerfile所在路径
     * @param serverInfo
     * @param imageName
     * @return
     */
    @PostMapping("buildImageByDockerfile")
    public String buildImageByDockerfile(String dockerfileUrl, ServerInfo serverInfo, String imageName) {
        return imageService.buildImageByDockerfile(dockerfileUrl, serverInfo, imageName);
    }

    @PostMapping("deployContainerByCompose")
    public String deployContainerByCompose(String composeFileUrl, ServerInfo serverInfo) {
        return containerService.deployContainerByCompose(composeFileUrl, serverInfo);
    }

    /**
     * @param serverInfo
     * @return ContainerVO的List，前端获取成JSON
     */
    @GetMapping("container")
    public List<ContainerVO> getContainerList(ServerInfo serverInfo) {
        return containerService.getContainerList(serverInfo);
    }

}
