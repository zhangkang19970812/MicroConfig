package com.nju.tutorialtool.controller;

import com.nju.tutorialtool.model.Configuration;
import com.nju.tutorialtool.model.ConfigurationItem;
import com.nju.tutorialtool.service.ConfigurationService;
import com.nju.tutorialtool.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/configuration")
public class ConfigurationController {

    @Autowired
    private ConfigurationService configurationService;

    @RequestMapping(value = "/validate", method = RequestMethod.POST)
    public ResultVO validate(@RequestBody Configuration configuration) {
        List<Configuration> list = configurationService.getAllServicesConfigurations();
        List<ConfigurationItem> configurationItemList = new ArrayList<>();
        for (Configuration configuration1 : list) {
            configurationItemList.addAll(configuration.getSameConfigurationItems(configuration1));
        }
        if (configurationItemList.size() == 0) {
            return new ResultVO("Correctly configured", 1);
        }
        else {
            StringBuffer stringBuffer = new StringBuffer();
            int pcount = 0, ncount = 0;
            for (ConfigurationItem configurationItem : configurationItemList) {
                if (configurationItem.getItemName().equals("server.port") && pcount == 0) {
                    stringBuffer.append("server.port ");
                    pcount = 1;
                }
                if (configurationItem.getItemName().equals("spring.application.name") && ncount == 0) {
                    stringBuffer.append("spring.application.name ");
                    ncount = 1;
                }
            }
            return new ResultVO("ConfigurationItem: " + stringBuffer.toString() + "have the same property values as the other services", 0);
        }
    }
}
