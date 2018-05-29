package com.nju.tutorialtool.service;

import com.nju.tutorialtool.model.ServiceInfo;
import com.nju.tutorialtool.model.ServiceShowInfo;
import com.nju.tutorialtool.util.enums.BaseDirConstant;
import com.nju.tutorialtool.util.io.IO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShowServiceInfoService {
    @Autowired
    private ServiceDirMapService serviceDirMapService;

    @Autowired
    private DeployServerService deployServerService;

    public List<ServiceShowInfo> getAllServiceInfo() {
        List<ServiceShowInfo> list = new ArrayList<>();
        List<ServiceInfo> serviceInfoList = serviceDirMapService.getAllServices();
        for (ServiceInfo serviceInfo : serviceInfoList) {
            String serviceName = serviceInfo.getServiceName();
            String port = IO.getServicePort(BaseDirConstant.projectBaseDir + "/" + serviceInfo.getFolderName());
//            String ip = deployServerService.getFirst().getIp();
            list.add(new ServiceShowInfo(serviceName, port, ""));
        }
        return list;
    }
}
