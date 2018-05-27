package com.nju.tutorialtool.controller;

import com.nju.tutorialtool.model.*;
import com.nju.tutorialtool.service.*;
import com.nju.tutorialtool.service.HystrixService.AddHystrixService;
import com.nju.tutorialtool.util.enums.BaseDirConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * @Author YZ
 * @Date 2018/5/18
 */
@RestController
@RequestMapping("/addGeneral")
public class GeneralController {
    @Autowired
    private DeployServerService deployServerService;
    @Autowired
    private ServiceDirMapService serviceDirMapService;
    @Autowired
    private ShowServiceInfoService showServiceInfoService;
    @Autowired
    private EurekaService eurekaService;
    @Autowired
    private AddHystrixService addHystrixService;
    @Autowired
    private RabbitmqService rabbitmqService;
    @Autowired
    private RibbonService ribbonService;
    @Autowired
    private ZuulService zuulService;
    @Autowired
    private ConfigurationService configurationService;
    @Autowired
    private GenerateJarService generateJarService;
    @Autowired
    private CreateMysqlProjectService createMysqlProjectService;
    @Autowired
    private UploadService uploadService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public void addGeneral(@RequestBody General general) throws IOException {
        DeployServer deployServer = general.getDeployServer();
        List<ServiceDirMap> services = general.getServices();
        List<Configuration> configurations = general.getConfigurationList();
        List<String> serviceURLs = new ArrayList<>();

        /**
         * 用户添加部署服务器
         */
        deployServerService.addServer(deployServer);


        for (ServiceDirMap serviceDirMap : services) {
            serviceURLs.add(BaseDirConstant.projectBaseDir + "/" + serviceDirMap.getDirName());
        }

        // eureka server
        eurekaService.createEurekaServer(general.getEurekaServerInfo());
        String eurekaServerName = general.getEurekaServerInfo().getArtifactId();
        serviceDirMapService.addServiceDirMap(new ServiceDirMap(eurekaServerName, eurekaServerName));

        // eureka client
        eurekaService.addEurekaClient(serviceURLs);

        /**
         * 配置文件
         */
        for (int i = 0; i < configurations.size(); i++) {
            configurationService.editConfiguration(configurations.get(i).getProjectPath(), configurations.get(i).getList());
        }

        if (general.isRibbon()) {
            for (int i = 0; i < serviceURLs.size(); i++) {
                ribbonService.addRibbon(serviceURLs.get(i));
            }
            Ribbon ribbon = new Ribbon(general.getRibbonDTO(), general.getServices());
            ribbonService.replaceUrl(ribbon.getConsumerPath(), ribbon.getProviderPath());
        }
        if (general.isHystrix()) {
            for (int i = 0; i < serviceURLs.size(); i++) {
                addHystrixService.add(serviceURLs.get(i));
            }
        }
        /**
         * 新增：创建Zuul项目
         */
        if (general.isZuul()) {
            zuulService.createZuulProject(general.getZuulInfo());
            String zuulName = general.getZuulInfo().getArtifactId();
            serviceDirMapService.addServiceDirMap(new ServiceDirMap(zuulName, zuulName));
        }
        /**
         * 数据库创建(多个)
         */
        try {
            for (MysqlInfo mysqlInfo : general.getMysqlInfoList()) {
                createMysqlProjectService.createMysqlProject(mysqlInfo);
                serviceDirMapService.addServiceDirMap(new ServiceDirMap(mysqlInfo.getProjectName(), mysqlInfo.getProjectName()));
            }
        } catch (Exception e) {
            System.out.println("数据库创建出错");
        }


        /**
         * 打包jar
         */
        for (String path : serviceURLs) {
            generateJarService.generateJar(path);
        }
        /**
         * 项目部署
         */
        uploadService.upload(general.getServerInfo());
    }

    /**
     * 最后展示所有服务列表界面
     * @return
     */
    @RequestMapping("/showAllServiceInfo")
    public List<ServiceShowInfo> showAllServiceInfo() {
        return showServiceInfoService.getAllServiceInfo();
    }

    public void addRabbitmq(String location) {
        String path = rabbitmqService.find(location);
        String direct = path.substring(location.length() + 15);
        rabbitmqService.addQueue(path, direct);
    }

    public void addSender(String location) {
        String path = rabbitmqService.find(location);
        String direct = path.substring(location.length() + 15);
        rabbitmqService.addSender(path, direct);
    }

    public void addReceiver(String location) {
        String path = rabbitmqService.find(location);
        String direct = path.substring(location.length() + 15);
        rabbitmqService.addRecevier(path, direct);
    }

    public List<ConfigurationItem> getListFromMap(HashMap<String, String> map) {
        List<ConfigurationItem> configList = new ArrayList<>();
        Iterator<String> it = map.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next();
            configList.add(new ConfigurationItem(key, map.get(key)));
        }
        return configList;
    }
}