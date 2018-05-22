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
     * 参数DockerfileInfo包括项目路径和项目类型，类型包括service和mysql两种
     * @param dockerfileInfo
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public boolean createDockerfile(@RequestBody DockerfileInfo dockerfileInfo) throws Exception {
        createDockerfileService.createDockerfile(dockerfileInfo);
        return true;
    }
}
