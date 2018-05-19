package com.nju.tutorialtool.controller;

import com.nju.tutorialtool.service.HystrixService.AddHystrixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author YZ
 * @Date 2018/5/18
 */
@RestController
@RequestMapping("/addGeneral")
public class GeneralController {
    @Autowired
    private AddHystrixService addHystrixService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public void addGeneral(@RequestBody String url, boolean isEurekaServer,boolean isZuul,boolean isHystrix,boolean isRabbitMQ,boolean isRibbon){
        if(isEurekaServer==true){

        }
        if(isZuul==true){

        }
        if(isHystrix==true){
            addHystrixService.add(url);
        }
        if(isRabbitMQ==true){

        }
        if(isRibbon==true){

        }
    }
}
