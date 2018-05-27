package com.nju.tutorialtool.service;

import com.nju.tutorialtool.dao.DeployServerDao;
import com.nju.tutorialtool.model.DeployServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeployServerService {
    @Autowired
    private DeployServerDao deployServerDao;

    public void addServer(DeployServer deployServer) {
        deployServerDao.save(deployServer);
    }

    public DeployServer getFirst() {
        return deployServerDao.findAll().get(0);
    }
}
