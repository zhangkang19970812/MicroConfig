package com.nju.tutorialtool.controller;

import com.nju.tutorialtool.model.Configuration;
import com.nju.tutorialtool.service.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/configuration")
public class ConfigurationController {

    @Autowired
    private ConfigurationService configurationService;

    /**
     * 修改某项目的配置文件
     * @param configuration 包含一个项目路径，一个配置项列表
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public boolean editConfiguration(@RequestBody Configuration configuration) {
        configurationService.editConfiguration(configuration.getProjectPath(), configuration.getList());
        return true;
    }
}
