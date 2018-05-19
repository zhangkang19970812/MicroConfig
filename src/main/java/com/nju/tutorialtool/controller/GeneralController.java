package com.nju.tutorialtool.controller;

import com.nju.tutorialtool.service.HystrixService.AddHystrixService;
import com.nju.tutorialtool.service.RabbitmqService;
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

    @Autowired
    private RabbitmqService rabbitmqService;


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public void addGeneral(@RequestBody String url, boolean isEurekaServer, boolean isZuul, boolean isHystrix, boolean isRabbitMQ, boolean isRibbon, String src, String dest, String mqPath) {
        if (isEurekaServer == true) {

        }
        if (isZuul == true) {

        }
        if (isHystrix == true) {
            addHystrixService.add(url);
        }
        if (isRabbitMQ == true) {
            addRabbitmq(mqPath);
            addSender(src);
            addReceiver(dest);
        }
        if (isRibbon == true) {

        }
    }


    public void addRabbitmq(String location) {
        String path = rabbitmqService.find(location);
        String direct = path.substring(location.length() + 15);
        rabbitmqService.addQueue(path, direct);
    }

    public void addSender(String location) {
        String path = rabbitmqService.find(location);
        String direct = path.substring(location.length() + 15);
        rabbitmqService.addSender(path, direct);
    }

    public void addReceiver(String location) {
        String path = rabbitmqService.find(location);
        String direct = path.substring(location.length() + 15);
        rabbitmqService.addRecevier(path, direct);
    }
}
