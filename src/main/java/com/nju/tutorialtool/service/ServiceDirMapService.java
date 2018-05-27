package com.nju.tutorialtool.service;

import com.nju.tutorialtool.dao.ServiceDirMapDao;
import com.nju.tutorialtool.model.ServiceDirMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceDirMapService {
    @Autowired
    private ServiceDirMapDao serviceDirMapDao;

    public void addServiceDirMap(ServiceDirMap serviceDirMap) {
        serviceDirMapDao.save(serviceDirMap);
    }

    public List<ServiceDirMap> getAllServices() {
        return serviceDirMapDao.findAll();
    }
}
