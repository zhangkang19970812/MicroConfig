package com.nju.tutorialtool.controller;

import com.nju.tutorialtool.service.GenerateJarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jar")
public class JarController {

    @Autowired
    private GenerateJarService generateJarService;

    @RequestMapping(value = "/generate", method = RequestMethod.POST)
    public boolean generateJar(String projectPath) {
        generateJarService.generateJar(projectPath);
        return true;
    }
}
