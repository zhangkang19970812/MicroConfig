package com.nju.tutorialtool.controller;

import com.nju.tutorialtool.model.ComposeInfo;
import com.nju.tutorialtool.service.CreateComposeYmlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/compose")
public class CreateComposeYmlController {
    @Autowired
    private CreateComposeYmlService createComposeYmlService;

    /**
     * 根据composeInfo创建docker-compose.yml文件
     * composeInfo包括一个service的list，
     * service是指compose文件中定义的一个服务，包括service的名称，image镜像，如果是eureka或者zuul项目则会包括port，否则port属性为null
     * isMysql是指该服务是否是mysql项目
     * @param composeInfo
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public boolean createComposeYml(@RequestBody ComposeInfo composeInfo) throws Exception {
        createComposeYmlService.createComposeYml(composeInfo);
        return true;
    }
}
