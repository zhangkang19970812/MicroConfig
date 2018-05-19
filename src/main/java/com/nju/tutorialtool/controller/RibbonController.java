package com.nju.tutorialtool.controller;

import com.nju.tutorialtool.service.RibbonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/ribbon")
public class RibbonController {
    @Autowired
    private RibbonService ribbonService;

    /**
     * 添加Ribbon注解
     * @param projectPath
     * @return
     * @throws IOException
     */
    @RequestMapping("/addRibbon")
    public boolean addRibbon(String projectPath) throws IOException {
        ribbonService.addRibbon(projectPath);
        return true;
    }
}
