package com.nju.tutorialtool.controller;

import com.nju.tutorialtool.model.MysqlInfo;
import com.nju.tutorialtool.service.CreateMysqlProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/mysql")
public class CreateMysqlProjectController {
    @Autowired
    private CreateMysqlProjectService createMysqlProjectService;

    @RequestMapping("/index")
    public ModelAndView create() {
        return new ModelAndView("");
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public boolean createProject(@RequestBody MysqlInfo mysqlInfo) throws Exception {
        createMysqlProjectService.createMysqlProject(mysqlInfo);
        return true;
    }
}
