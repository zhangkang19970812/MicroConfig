package com.nju.tutorialtool.controller;

import com.nju.tutorialtool.service.CreateProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/createProject")
public class CreateProjectController {
    @Autowired
    private CreateProjectService createProjectService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public boolean createProject(@RequestBody ProjectInfo projectInfo) throws Exception {
        createProjectService.createProject(projectInfo);
        return true;
    }
}
