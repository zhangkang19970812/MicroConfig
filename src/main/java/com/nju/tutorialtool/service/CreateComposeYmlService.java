package com.nju.tutorialtool.service;

import com.nju.tutorialtool.model.ComposeInfo;
import com.nju.tutorialtool.template.compose.ComposeYmlFile;
import org.springframework.stereotype.Service;

@Service
public class CreateComposeYmlService {
    /**
     * 根据composeInfo创建docker-compose.yml文件
     * @param composeInfo
     * @throws Exception
     */
    public void createComposeYml(ComposeInfo composeInfo) throws Exception {
        ComposeYmlFile composeYmlFile = new ComposeYmlFile(composeInfo.getBaseDir(), composeInfo.getServiceList());
        composeYmlFile.generate();
    }
}
