package com.nju.tutorialtool.service;

import com.nju.tutorialtool.dao.ServiceDirMapDao;
import com.nju.tutorialtool.model.ServiceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceDirMapService {
    @Autowired
    private ServiceDirMapDao serviceDirMapDao;

    public void addServiceDirMap(ServiceInfo serviceInfo) {
        serviceDirMapDao.save(serviceInfo);
    }

    public List<ServiceInfo> getAllServices() {
        return serviceDirMapDao.findAll();
    }
}
