package com.nju.tutorialtool.controller;

import com.nju.tutorialtool.model.ProjectInfo;
import com.nju.tutorialtool.service.CreateProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/createProject")
public class CreateProjectController {
    @Autowired
    private CreateProjectService createProjectService;

    @RequestMapping("/index")
    public ModelAndView create() {
        return new ModelAndView("index");
    }

    /**
     * @param projectInfo 不包含baseDir了
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public boolean createProject(@RequestBody ProjectInfo projectInfo) {
        createProjectService.createProject(projectInfo);
        return true;
    }
}
