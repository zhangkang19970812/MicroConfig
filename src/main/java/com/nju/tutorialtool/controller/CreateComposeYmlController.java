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
     * 创建docker-compose.yml文件
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public boolean createComposeYml() throws Exception {
        createComposeYmlService.createComposeYml();
        return true;
    }
}
