package com.nju.tutorialtool.service;

import com.nju.tutorialtool.model.ServiceInfo;
import com.nju.tutorialtool.template.compose.ComposeYmlFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
public class CreateComposeYmlService {

    @Autowired
    private ServiceDirMapService serviceDirMapService;

    @Autowired
    private ConfigurationService configurationService;

    @Autowired
    private UserService userService;

    /**
     * 创建docker-compose.yml文件
     * @throws Exception
     */
    public void createComposeYml() throws Exception {
        List<ServiceInfo> list = serviceDirMapService.getAllServices();
        List<com.nju.tutorialtool.model.Service> serviceList = new ArrayList<>();
        for (ServiceInfo serviceInfo : list) {
            if (serviceInfo.getMysqlInfo() != null) {
                com.nju.tutorialtool.model.Service service = new com.nju.tutorialtool.model.Service(serviceInfo.getServiceName(), "", false);
                com.nju.tutorialtool.model.Service mysqlService = new com.nju.tutorialtool.model.Service(serviceInfo.getMysqlInfo().getProjectName(), "", true);
                serviceList.add(service);
                serviceList.add(mysqlService);
            }
            else {
                com.nju.tutorialtool.model.Service toolService = new com.nju.tutorialtool.model.Service(serviceInfo.getServiceName(), configurationService.getPort(serviceInfo), false);
                serviceList.add(toolService);
            }
        }
        ComposeYmlFile composeYmlFile = new ComposeYmlFile(userService.getUserFolder(), serviceList);
        composeYmlFile.generate();
    }

}
