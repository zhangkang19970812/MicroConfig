package com.nju.tutorialtool.service;

import com.nju.tutorialtool.model.ProjectInfo;
import com.nju.tutorialtool.model.SpringCloudInfo;
import com.nju.tutorialtool.util.io.IO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Shenmiu
 * @date 2018/05/20
 * <p>
 * Eureka服务实现：
 * 1. 创建Eureka Server项目
 * 2. 将给所有服务添加Eureka Client组件
 */
@Service("eurekaService")
public class EurekaService {

    @Autowired
    private CreateProjectService createProjectService;

    /**
     * 创建Eureka Server项目
     */
    public void createEurekaServer(SpringCloudInfo springCloudInfo) {
        ProjectInfo projectInfo = new ProjectInfo(springCloudInfo);
        projectInfo.getDependencies().add("eurekaServer");
        createProjectService.createProject(projectInfo);
    }


    /**
     * 给已有微服务添加Eureka Client组件
     *
     * @param serviceRootPaths 所有微服务项目的根目录
     */
    public void addEurekaClient(List<String> serviceRootPaths) {

        List<String> dependencies = new ArrayList<>();
        dependencies.add("eurekaDiscovery");

        for (String serviceRootPath : serviceRootPaths) {
            // 1. 找到有@SpringBootApplication
            File applicationFile = IO.getApplication(serviceRootPath);
            // 确定根目录下有一个applicationFile
            assert applicationFile != null;

            // 2. 添加@EnableDiscoveryClient注解
            RandomAccessFile raf = null;
            try {
                raf = new RandomAccessFile(applicationFile, "rw");
                String line = null;
                while ((line = raf.readLine()) != null) {
                    if (line.contains("@SpringBootApplication")) {
                        long pointer = raf.getFilePointer();
                        String annotation = "@EnableDiscoveryClient\n";
                        IO.insert(pointer, annotation, applicationFile);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            // 3. pom文件添加注解
            IO.addDependencyToPom(serviceRootPath, dependencies);
        }
    }

//    public static void main(String[] args) {
//        EurekaService eurekaService = new EurekaService();
//        List<String> serviceRootPaths = new ArrayList<>();
//        serviceRootPaths.add("/Users/harvey/Projects/IdeaProjects/tutorial-tool");
//        eurekaService.addEurekaClient(serviceRootPaths);
//    }
}
