package com.nju.tutorialtool.controller;

import com.nju.tutorialtool.service.RabbitmqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rabbitmq")
public class RabbitmqController {

    @Autowired
    RabbitmqService rabbitmqService;

    @RequestMapping("/addRabbitmq")
    public void addRabbitmq(@RequestBody String location) {
        String path = rabbitmqService.find(location);
        String direct = path.substring(location.length() + 15);
        rabbitmqService.addQueue(path, direct);
    }

    @RequestMapping("/addSender")
    public void addSender(@RequestBody String location) {
        String path = rabbitmqService.find(location);
        String direct = path.substring(location.length() + 15);
        rabbitmqService.addSender(path, direct);
    }

    @RequestMapping("/addReceiver")
    public void addReceiver(@RequestBody String location) {
        String path = rabbitmqService.find(location);
        String direct = path.substring(location.length() + 15);
        rabbitmqService.addRecevier(path, direct);
    }
}
