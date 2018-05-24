package com.nju.tutorialtool.service;

import com.nju.tutorialtool.controller.ConfigurationController;
import com.nju.tutorialtool.model.Configuration;
import com.nju.tutorialtool.model.ConfigurationItem;
import com.nju.tutorialtool.util.io.IO;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ConfigurationService {
    /**
     * 修改某项目的配置文件
     * @param projectPath
     * @param list
     */
    public void editConfiguration(String projectPath, List<ConfigurationItem> list) {
        File file = IO.getFile(projectPath + "/src/main/resources", "application.properties");
        String[] str = IO.readFromFile(file).split("\n");
        for (ConfigurationItem configurationItem : list) {
            int ret = 0;
            for (String s : str) {
                if (configurationItem.getItemName().equals(s.split("=")[0])) {
                    IO.replaceFileStr(file, s, configurationItem.toString());
                    ret = 1;
                    break;
                }
            }
            if (ret == 0) {
                IO.insertToEnd(file, configurationItem.toString());
            }
        }
    }

//    public static void main(String[] args) {
//        ConfigurationItem configurationItem1 = new ConfigurationItem("server.port", "5000");
//        ConfigurationItem configurationItem2 = new ConfigurationItem("spring.application.name", "account");
//        ConfigurationItem configurationItem3 = new ConfigurationItem("eureka.client.allow-redirects", "false");
//        List<ConfigurationItem> list = new ArrayList<>();
//        list.add(configurationItem1);
//        list.add(configurationItem2);
//        list.add(configurationItem3);
//        ConfigurationService configurationService = new ConfigurationService();
//        configurationService.editConfiguration("H:/programs/account_service", list);
//    }
}
