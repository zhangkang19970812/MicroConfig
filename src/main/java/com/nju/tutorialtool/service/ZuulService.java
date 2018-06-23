package com.nju.tutorialtool.service;

import com.nju.tutorialtool.model.ProjectInfo;
import com.nju.tutorialtool.model.SpringCloudInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ZuulService {

    @Autowired
    private CreateProjectService createProjectService = new CreateProjectService();

    /**
     * 创建Zuul项目
     * @param springCloudInfo
     */
    public void createZuulProject(SpringCloudInfo springCloudInfo) {
        ProjectInfo projectInfo = new ProjectInfo(springCloudInfo);
        projectInfo.addDependency("zuul");
        projectInfo.addDependency("eurekaDiscovery");
        createProjectService.createProject(projectInfo, "zuul");
    }

//    public static void main(String[] args) {
//        ZuulService zuulService = new ZuulService();
//        SpringCloudInfo springCloudInfo = new SpringCloudInfo();
//        springCloudInfo.setBaseDir("H:/programs");
//        springCloudInfo.setGroupId("com.nju");
//        springCloudInfo.setArtifactId("zuul");
//        zuulService.createZuulProject(springCloudInfo);
//    }

}