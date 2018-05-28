package com.nju.tutorialtool.main;

import com.nju.tutorialtool.dao.DeployServerDao;
import com.nju.tutorialtool.model.DeployServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitialization implements CommandLineRunner {

    @Autowired
    private DeployServerDao deployServerDao;


    @Override
    public void run(String... strings) throws Exception {
        /**
         * do not modify the method
         */
        // save a couple of users
        deployServerDao.save(new DeployServer("1.1.1.1", "admin", "123456"));

        // fetch all users
        System.out.println("User found with findAll():");
        System.out.println("-------------------------------");
        for (DeployServer deployServer : deployServerDao.findAll()) {
            System.out.println(deployServer.getIp() + deployServer.getPassword() + deployServer.getUsername());
        }
    }

    public List<DeployServer> helloWorld() {
        /**
         * write your code here
         */

        return null; // place holder, you should not return null here
    }
}
