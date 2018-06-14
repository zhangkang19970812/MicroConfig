package com.nju.tutorialtool.service;

import com.nju.tutorialtool.model.ServiceInfo;
import com.nju.tutorialtool.model.ServiceShowInfo;
import com.nju.tutorialtool.util.io.IO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShowServiceInfoService {
    @Autowired
    private ServiceDirMapService serviceDirMapService;

    @Autowired
    private DeployServerService deployServerService;

    @Autowired
    private UserService userService;

    public List<ServiceShowInfo> getAllServiceInfo() {
        List<ServiceShowInfo> list = new ArrayList<>();
        List<ServiceInfo> serviceInfoList = serviceDirMapService.getAllServices();
        for (ServiceInfo serviceInfo : serviceInfoList) {
            String serviceName = serviceInfo.getServiceName();
            String port = IO.getServicePort(userService.getUserFolder() + File.separator + serviceInfo.getFolderName());
//            String ip = deployServerService.getFirst().getIp();
            list.add(new ServiceShowInfo(serviceName, port, ""));
            if (serviceInfo.getMysqlInfo() != null) {
                list.add(new ServiceShowInfo(serviceInfo.getMysqlInfo().getProjectName(), "3306", ""));
            }
        }
        return list;
    }
}
