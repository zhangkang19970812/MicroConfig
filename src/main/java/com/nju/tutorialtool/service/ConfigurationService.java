package com.nju.tutorialtool.service;

import com.nju.tutorialtool.model.ConfigurationItem;
import com.nju.tutorialtool.util.io.IO;
import org.springframework.stereotype.Service;

import java.io.File;
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
        File file = IO.getFile(projectPath + "/src/main/resources", "application");
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
}
