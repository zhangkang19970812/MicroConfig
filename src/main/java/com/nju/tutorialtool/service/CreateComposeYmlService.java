package com.nju.tutorialtool.service;

import com.nju.tutorialtool.model.ComposeInfo;
import com.nju.tutorialtool.template.compose.ComposeYmlFile;
import com.nju.tutorialtool.util.enums.BaseDirConstant;
import org.springframework.stereotype.Service;

@Service
public class CreateComposeYmlService {
    /**
     * 根据composeInfo创建docker-compose.yml文件
     * @param composeInfo
     * @throws Exception
     */
    public void createComposeYml(ComposeInfo composeInfo) throws Exception {
        ComposeYmlFile composeYmlFile = new ComposeYmlFile(BaseDirConstant.projectBaseDir, composeInfo.getServiceList());
        composeYmlFile.generate();
    }
}
