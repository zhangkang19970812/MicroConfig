package com.nju.tutorialtool.controller;

import com.nju.tutorialtool.model.ConfigurationItem;
import com.nju.tutorialtool.model.General;
import com.nju.tutorialtool.service.*;
import com.nju.tutorialtool.service.HystrixService.AddHystrixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * @Author YZ
 * @Date 2018/5/18
 */
@RestController
@RequestMapping("/addGeneral")
public class GeneralController {

    @Autowired
    private EurekaService eurekaService;
    @Autowired
    private AddHystrixService addHystrixService;
    @Autowired
    private RabbitmqService rabbitmqService;
    @Autowired
    private RibbonService ribbonService;
    @Autowired
    private ZuulService zuulService;
    @Autowired
    private ConfigurationService configurationService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public void addGeneral(@RequestBody General general) throws IOException {
        HashMap<String, String> services = general.getServices();

        List<String> serviceRootPaths = new ArrayList<>(services.values());

        // Eureka Server
        eurekaService.createEurekaServer();

        // Eureka Client
        eurekaService.addEurekaClient(serviceRootPaths);

        /**
         * 配置文件
         */
        for (int i = 0; i < serviceRootPaths.size(); i++) {
            configurationService.editConfiguration(serviceRootPaths.get(i), getListFromMap(general.getConfigs().get(serviceRootPaths.get(i))));
        }

        if (general.isEurekaServer()) {

        }
        if (general.isZuul()) {
            zuulService.replaceUrl(general.getZuulComsumer(), general.getZuulProviders());
        }
        if (general.isHystrix()) {
            for (int i = 0; i < serviceRootPaths.size(); i++) {
                addHystrixService.add(serviceRootPaths.get(i));
            }
        }
        if (general.isRabbitMQ()) {
            addRabbitmq(services.get(general.getMqServiceName()));
            addSender(general.getMqSrc());
            addReceiver(general.getMqDest());
        }
        if (general.isRibbon()) {
            for (int i = 0; i < serviceRootPaths.size(); i++) {
                ribbonService.addRibbon(serviceRootPaths.get(i));
            }
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

    public List<ConfigurationItem> getListFromMap(HashMap<String, String> map) {
        List<ConfigurationItem> configList = new ArrayList<>();
        Iterator<String> it = map.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next();
            configList.add(new ConfigurationItem(key, map.get(key)));
        }
        return configList;
    }
}
