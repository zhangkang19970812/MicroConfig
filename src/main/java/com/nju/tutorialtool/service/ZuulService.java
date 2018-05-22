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
    private CreateProjectService createProjectService;

    /**
     * 创建Zuul项目
     * @param springCloudInfo
     */
    public void createZuulProject(SpringCloudInfo springCloudInfo) {
        ProjectInfo projectInfo = new ProjectInfo(springCloudInfo);
        projectInfo.getDependencies().add("zuul");
        createProjectService.createProject(projectInfo);
    }

//    /**
//     * 对项目的启动类添加Zuul支持
//     * @param projectPath
//     * @throws IOException
//     */
//    public void modifyApplication(String projectPath) throws IOException {
//        File applicationFile = IO.getApplication(projectPath);
//        RandomAccessFile raf=new RandomAccessFile(applicationFile,"rw");
//        String line=null;
//        while((line=raf.readLine())!=null){
//            if(line.contains("@SpringBootApplication")){
//                long pointer=raf.getFilePointer();
//                String annotation="@EnableZuulProxy\n";
//                IO.insert(pointer,annotation,applicationFile);
//            }
//        }
//    }

}