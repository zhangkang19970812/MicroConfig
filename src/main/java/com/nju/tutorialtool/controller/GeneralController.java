package com.nju.tutorialtool.controller;

import com.nju.tutorialtool.model.Configuration;
import com.nju.tutorialtool.model.ConfigurationItem;
import com.nju.tutorialtool.model.General;
import com.nju.tutorialtool.service.ConfigurationService;
import com.nju.tutorialtool.service.HystrixService.AddHystrixService;
import com.nju.tutorialtool.service.RabbitmqService;
import com.nju.tutorialtool.service.RibbonService;
import com.nju.tutorialtool.service.ZuulService;
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
        HashMap<String,String> services=general.getServices();
        List<String> serviceURLs=new ArrayList<>();

        for (String url : services.values()) {
            serviceURLs.add(url);
        }

        /**
         * 配置文件
         */
        for(int i=0;i<serviceURLs.size();i++) {
            configurationService.editConfiguration(serviceURLs.get(i),getListFromMap(general.getConfigs().get(serviceURLs.get(i))));
        }

        if (general.isEurekaServer() == true) {

        }
        if (general.isZuul() == true) {
            zuulService.replaceUrl(general.getZuulComsumer(),general.getZuulProviders());
        }
        if (general.isHystrix() == true) {
            for(int i=0;i<serviceURLs.size();i++) {
                addHystrixService.add(serviceURLs.get(i));
            }
        }
        if (general.isRabbitMQ() == true) {
            for(int i=0;i<serviceURLs.size();i++) {
               addRabbitmq(serviceURLs.get(i));
            }

            addSender(general.getMqSrc());
            addReceiver(general.getMqDest());
        }
        if (general.isRibbon()== true) {
            for(int i=0;i<serviceURLs.size();i++) {
                ribbonService.addRibbon(serviceURLs.get(i));
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

    public List<ConfigurationItem> getListFromMap(HashMap<String,String> map){
        List<ConfigurationItem> configList=new ArrayList<>();
        Iterator<String> it = map.keySet().iterator();
        while(it.hasNext()) {
            String key = it.next();
            configList.add(new ConfigurationItem(key,map.get(key)));
        }
        return configList;
    }
}
