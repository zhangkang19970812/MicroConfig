package com.nju.tutorialtool.controller;

import com.alibaba.fastjson.JSON;
import com.nju.tutorialtool.model.*;
import com.nju.tutorialtool.model.dto.RibbonDTO;
import com.nju.tutorialtool.service.*;
import com.nju.tutorialtool.service.HystrixService.AddHystrixService;
import com.nju.tutorialtool.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author YZ
 * @Date 2018/5/18
 */
@RestController
@RequestMapping("/general")
public class GeneralController {
    @Autowired
    private ServiceDirMapService serviceDirMapService;
    @Autowired
    private ShowServiceInfoService showServiceInfoService;
    @Autowired
    private CreateDockerfileService createDockerfileService;
    @Autowired
    private EurekaService eurekaService;
    @Autowired
    private AddHystrixService addHystrixService;
    @Autowired
    private RibbonService ribbonService;
    @Autowired
    private ZuulService zuulService;
    @Autowired
    private ConfigurationService configurationService;
    @Autowired
    private GenerateJarService generateJarService;
    @Autowired
    private SqlService sqlService;
    @Autowired
    private UserService userService;
    @Autowired
    private CreateComposeYmlService createComposeYmlService;

    private Logger logger = LoggerFactory.getLogger(GeneralController.class);

    @PostMapping(value = "/uploadFolder")
    public String uploadFolder(MultipartFile[] folder) {
        logger.info("传入的文件参数：{}", JSON.toJSONString(folder, true));
        userService.addUser();
        FileUtil.saveMultiFile(userService.getUserFolder(), folder);
        return "ok";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public void addGeneral(@RequestBody General general) throws Exception {
        List<ServiceInfo> services = general.getServices();

        Map<String, String> service2folder = services.stream()
                .collect(Collectors.toMap(ServiceInfo::getServiceName, ServiceInfo::getFolderName));

        // eureka server
        eurekaService.createEurekaServer(general.getEurekaServerInfo());
        createDockerfileService.createDockerfile(userService.getUserFolder() + File.separator + general.getEurekaServerInfo().getArtifactId(), "service");
        String eurekaServerName = general.getEurekaServerInfo().getArtifactId();
        Configuration eConfiguration = generateConfiguration("8761");
        serviceDirMapService.addServiceDirMap(new ServiceInfo(eurekaServerName, eurekaServerName, eConfiguration));

        // zuul
        if (general.isZuul()) {
            zuulService.createZuulProject(general.getZuulInfo());
            createDockerfileService.createDockerfile(userService.getUserFolder() + File.separator + general.getZuulInfo().getArtifactId(), "service");
            String zuulName = general.getZuulInfo().getArtifactId();
            Configuration zConfiguration = generateConfiguration("8040");
            serviceDirMapService.addServiceDirMap(new ServiceInfo(zuulName, zuulName, zConfiguration));
        }

        // ribbon
        if (general.isRibbon()) {
            for (RibbonDTO ribbonDTO  : general.getRibbonDTOList()) {
                String consumerDir = service2folder.get(ribbonDTO.getConsumer());
                ribbonService.addRibbon(getProjectPath(consumerDir));

                List<String> providersDir = ribbonDTO.getProviders().stream()
                        .map(service2folder::get)
                        .collect(Collectors.toList());
                List<String> providersPath = new ArrayList<>();

//                Ribbon ribbon = new Ribbon(consumerPath, providersPath);
                for (String s : providersDir) {
                    providersPath.add(getProjectPath(s));
                }
                ribbonService.replaceUrl(getProjectPath(consumerDir), providersPath);
            }
        }

        for (ServiceInfo service : services) {

            String serviceRootPath = userService.getUserFolder() + File.separator + service.getFolderName();

            //存入数据库
            MysqlInfo mysqlInfo = new MysqlInfo(service.getFolderName() + "_mysql");
            service.setMysqlInfo(mysqlInfo);
            for (ConfigurationItem configurationItem : configurationService.getDefaultPropertiesItems(service)) {
                configurationService.addConfigurationItem(configurationItem);
            }
            Configuration serviceConfiguration = new Configuration(configurationService.getDefaultPropertiesItems(service));
            configurationService.addConfiguration(serviceConfiguration);
            sqlService.addMysqlInfo(mysqlInfo);
            serviceDirMapService.addServiceDirMap(service);

            /**
             * 组件
             */
            // eureka client
            eurekaService.addEurekaClient(serviceRootPath);

            // hystrix
            if (general.isHystrix()) {
                addHystrixService.add(serviceRootPath,general.getMethods());
            }

            /**
             * 服务相关
             */
            // config
            configurationService.editConfiguration(service);
//            configurationService.editConfiguration(serviceRootPath, service.getConfig().getList());
//            configurationService.editServicesMysqlConfigurations();

            // 创建mysql(要检查是否该服务配置了mysql)
            if (configurationService.checkMysql(serviceRootPath)) {
                sqlService.createMysqlProject(serviceRootPath);
                serviceDirMapService.addServiceDirMap(new ServiceInfo(service.getFolderName() + "_mysql", service.getFolderName() + "_mysql"));
            }
//            createMysqlProjectService.createMysqlProject(service.getMysqlInfo());
//            serviceDirMapService.addServiceDirMap(new ServiceInfo(service.getMysqlInfo().getProjectName(), service.getMysqlInfo().getProjectName()));

            createDockerfileService.createDockerfile(serviceRootPath, "service");

            createComposeYmlService.createComposeYml();

            // 打包jar
            generateJarService.generateJar(serviceRootPath);
        }

    }

    private Configuration generateConfiguration(String port) {
        List<ConfigurationItem> elist = new ArrayList<>();
        ConfigurationItem configurationItem = new ConfigurationItem("server.port", port);
        elist.add(configurationItem);
        Configuration configuration = new Configuration(elist);
        configurationService.addConfigurationItem(configurationItem);
        configurationService.addConfiguration(configuration);
        return configuration;
    }

    private String getProjectPath(String dirName) {
        return userService.getUserFolder() + File.separator + dirName;
    }

    /**
     * 最后展示所有服务列表界面
     *
     * @return
     */
    @RequestMapping("/showAllServiceInfo")
    public List<ServiceShowInfo> showAllServiceInfo() {
        return showServiceInfoService.getAllServiceInfo();
    }

}