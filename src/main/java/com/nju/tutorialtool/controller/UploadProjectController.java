package com.nju.tutorialtool.controller;

import com.nju.tutorialtool.model.ProjectInfo;
import com.nju.tutorialtool.model.ServerInfo;
import com.nju.tutorialtool.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/upload")
public class UploadProjectController {
    @Autowired
    private UploadService uploadService;

    @RequestMapping("/index")
    public ModelAndView create() {
        return new ModelAndView("");
    }

    @RequestMapping(value = "/project", method = RequestMethod.POST)
    public boolean upload(@RequestBody ServerInfo serverInfo) {
        uploadService.upload(serverInfo);
        return true;
    }
}
