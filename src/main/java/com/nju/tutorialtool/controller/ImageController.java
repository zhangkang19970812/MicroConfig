package com.nju.tutorialtool.controller;

import com.nju.tutorialtool.model.ServerInfo;
import com.nju.tutorialtool.service.ImageService;
import com.nju.tutorialtool.vo.ImageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/image")
public class ImageController {

    @Autowired
    private ImageService imageService;

    /**
     * 得到某服务器上镜像列表
     * @param serverInfo
     * @return ImageVO的List
     */
    @PostMapping("getImageList")
    public List<ImageVO> getImageList(@RequestBody ServerInfo serverInfo) {
        return imageService.getImageList(serverInfo);
    }

    /**
     * 从Dockerfile路径构建镜像
     * @param dockerfileUrl Dockerfile所在路径
     * @param serverInfo
     * @param imageName
     * @return
     */
    @PostMapping("buildImageByDockerfile")
    public String buildImageByDockerfile(String dockerfileUrl, ServerInfo serverInfo, String imageName) {
        return imageService.buildImageByDockerfile(dockerfileUrl, serverInfo, imageName);
    }
}
