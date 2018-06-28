package com.nju.tutorialtool.controller;

import com.nju.tutorialtool.service.HystrixService.AddHystrixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Author YZ
 * @Date 2018/5/13
 */
@RestController
@RequestMapping("/addHystrix")
public class AddHystrixController {
    @Autowired
    private AddHystrixService addHystrixService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public boolean addHystrix(@RequestBody String url, Map<String,List<String>> map){
        addHystrixService.add(url,map);
        return true;
    }

    @RequestMapping(value = "/getMethods", method = RequestMethod.GET)
    public Map<String,List<String>> getMethodNames(@RequestBody String url){
        return addHystrixService.getMethodNames(url);
    }
}
