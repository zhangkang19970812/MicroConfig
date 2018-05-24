package com.nju.tutorialtool.service;

import com.nju.tutorialtool.model.ProjectInfo;
import com.nju.tutorialtool.model.SpringCloudInfo;
import com.nju.tutorialtool.util.io.IO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;

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
        createProjectService.createProject(projectInfo);
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