package com.nju.tutorialtool.main;

import com.nju.tutorialtool.dao.ConfigurationDao;
import com.nju.tutorialtool.dao.MysqlInfoDao;
import com.nju.tutorialtool.dao.ServiceDirMapDao;
import com.nju.tutorialtool.model.DeployServer;
import com.nju.tutorialtool.model.SpringCloudInfo;
import com.nju.tutorialtool.service.CreateDockerfileService;
import com.nju.tutorialtool.service.EurekaService;
import com.nju.tutorialtool.service.GitService;
import com.nju.tutorialtool.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Component
public class DataInitialization implements CommandLineRunner {

//    @Autowired
//    private DeployServerDao deployServerDao;
    @Autowired
    private ServiceDirMapDao serviceDirMapDao;
    @Autowired
    private ConfigurationDao configurationDao;
    @Autowired
    private MysqlInfoDao mysqlInfoDao;
    @Autowired
    private GitService gitService;
    @Autowired
    private EurekaService eurekaService;
    @Autowired
    private UserService userService;
    @Autowired
    private CreateDockerfileService createDockerfileService;

    @Override
    public void run(String... strings) throws Exception {
//        /**
//         * do not modify the method
//         */
//        // save a couple of users
//        deployServerDao.save(new DeployServer("1.1.1.1", "admin", "123456"));
//
//        // fetch all users
//        System.out.println("User found with findAll():");
//        System.out.println("-------------------------------");
//        for (DeployServer deployServer : deployServerDao.findAll()) {
//            System.out.println(deployServer.getIp() + deployServer.getPassword() + deployServer.getUsername());
//        }
//        ConfigurationItem configurationItem = new ConfigurationItem("a", "a");
//        List<ConfigurationItem> list = new ArrayList<>();
//        Configuration configuration = new Configuration(list);
//
//        ServiceInfo serviceInfo = new ServiceInfo("1", "1", configuration);
//        MysqlInfo mysqlInfo = new MysqlInfo("1");
//        serviceInfo.setMysqlInfo(mysqlInfo);
//        configurationDao.save(configuration);
//        mysqlInfoDao.save(mysqlInfo);
//        serviceDirMapDao.save(serviceInfo);
//        for (ServiceInfo serviceInfo1 : serviceDirMapDao.findAll()) {
//            System.out.println(serviceInfo1.getServiceName());
//        }
//        gitService.cloneToLocal("https://github.com/zhangkang19970812/test");
//        SpringCloudInfo springCloudInfo = new SpringCloudInfo();
//        springCloudInfo.setGroupId("com.nju");
//        springCloudInfo.setArtifactId("zk");
//        eurekaService.createEurekaServer(springCloudInfo);
//        createDockerfileService.createDockerfile(userService.getUserFolder() + File.separator + springCloudInfo.getArtifactId(), "service");
//        eurekaService.addEurekaClient(userService.getUserFolder() + File.separator + "account_service");
    }

    public List<DeployServer> helloWorld() {
        /**
         * write your code here
         */

        return null; // place holder, you should not return null here
    }
}
