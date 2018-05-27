package com.nju.tutorialtool.service;

import com.nju.tutorialtool.dao.DeployServerDao;
import com.nju.tutorialtool.dao.ServiceDirMapDao;
import com.nju.tutorialtool.model.ServiceDirMap;
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
        List<ServiceDirMap> serviceDirMapList = serviceDirMapService.getAllServices();
        for (ServiceDirMap serviceDirMap : serviceDirMapList) {
            String serviceName = serviceDirMap.getServiceName();
            String port = IO.getServicePort(BaseDirConstant.projectBaseDir + "/" + serviceDirMap.getDirName());
            String ip = deployServerService.getFirst().getIp();
            list.add(new ServiceShowInfo(serviceName, port, ip));
        }
        return list;
    }
}
