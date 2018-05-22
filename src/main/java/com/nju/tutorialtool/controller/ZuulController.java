package com.nju.tutorialtool.controller;

import com.nju.tutorialtool.model.SpringCloudInfo;
import com.nju.tutorialtool.service.ZuulService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/zuul")
public class ZuulController {

    @Autowired
    private ZuulService zuulService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public boolean createZuulProject(@RequestBody SpringCloudInfo springCloudInfo) {
        zuulService.createZuulProject(springCloudInfo);
        return true;
    }
}
