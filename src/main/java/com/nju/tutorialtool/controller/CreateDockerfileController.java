package com.nju.tutorialtool.controller;

import com.nju.tutorialtool.model.DockerfileInfo;
import com.nju.tutorialtool.service.CreateDockerfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dockerfile")
public class CreateDockerfileController {
    @Autowired
    private CreateDockerfileService createDockerfileService;

    /**
     * 为某服务创建Dockerfile
     * @param projectPath
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public boolean createDockerfile(String projectPath) throws Exception {
        createDockerfileService.createDockerfile(projectPath, "service");
        return true;
    }
}
