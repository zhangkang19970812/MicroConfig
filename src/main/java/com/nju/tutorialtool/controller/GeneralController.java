package com.nju.tutorialtool.controller;

import com.alibaba.fastjson.JSON;
import com.nju.tutorialtool.model.General;
import com.nju.tutorialtool.model.ServiceInfo;
import com.nju.tutorialtool.model.ServiceShowInfo;
import com.nju.tutorialtool.model.dto.RibbonDTO;
import com.nju.tutorialtool.service.*;
import com.nju.tutorialtool.service.HystrixService.AddHystrixService;
import com.nju.tutorialtool.util.FileUtil;
import com.nju.tutorialtool.util.enums.BaseDirConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
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
    private DeployServerService deployServerService;
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
    private CreateMysqlProjectService createMysqlProjectService;

    private Logger logger = LoggerFactory.getLogger(GeneralController.class);

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public void addGeneral(@RequestBody General general) throws IOException {
        List<ServiceInfo> services = general.getServices();

        Map<String, String> service2folder = services.stream()
                .collect(Collectors.toMap(ServiceInfo::getServiceName, ServiceInfo::getFolderName));

        // eureka server
        eurekaService.createEurekaServer(general.getEurekaServerInfo());
        String eurekaServerName = general.getEurekaServerInfo().getArtifactId();
        serviceDirMapService.addServiceDirMap(new ServiceInfo(eurekaServerName, eurekaServerName));

        // zuul
        if (general.isZuul()) {
            zuulService.createZuulProject(general.getZuulInfo());
            String zuulName = general.getZuulInfo().getArtifactId();
            serviceDirMapService.addServiceDirMap(new ServiceInfo(zuulName, zuulName));
        }

        for (ServiceInfo service : services) {

            String serviceRootPath = BaseDirConstant.projectBaseDir + File.separator + service.getFolderName();

            serviceDirMapService.addServiceDirMap(new ServiceInfo(service.getServiceName(), service.getFolderName()));

            /**
             * 组件
             */
            // eureka client
            eurekaService.addEurekaClient(serviceRootPath);

            // hystrix
            if (general.isHystrix()) {
                addHystrixService.add(serviceRootPath);
            }

            /**
             * 服务相关
             */
            // config
            configurationService.editConfiguration(serviceRootPath, service.getConfig().getList());

            // ribbon
            if (general.isRibbon()) {
                ribbonService.addRibbon(serviceRootPath);
                RibbonDTO ribbonDTO = general.getRibbonDTO();

                String consumerDir = service2folder.get(ribbonDTO.getConsumer());
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

            // 数据库创建
            try {
                createMysqlProjectService.createMysqlProject(service.getMysqlInfo());
                serviceDirMapService.addServiceDirMap(new ServiceInfo(service.getMysqlInfo().getProjectName(), service.getMysqlInfo().getProjectName()));
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                createDockerfileService.createDockerfile(serviceRootPath, "service");
            } catch (Exception e) {
                e.printStackTrace();
            }
            // 打包jar
            generateJarService.generateJar(serviceRootPath);
        }

    }

    private String getProjectPath(String dirName) {
        return BaseDirConstant.projectBaseDir + File.separator + dirName;
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

    @PostMapping(value = "/uploadFolder")
    public String uploadFolder(MultipartFile[] folder) {
        logger.info("传入的文件参数：{}", JSON.toJSONString(folder, true));
        FileUtil.saveMultiFile(BaseDirConstant.projectBaseDir, folder);
        return "ok";
    }

}