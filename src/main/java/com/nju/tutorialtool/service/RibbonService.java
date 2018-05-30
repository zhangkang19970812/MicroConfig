package com.nju.tutorialtool.service;

import com.nju.tutorialtool.util.io.IO;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

@Service
public class RibbonService {
    /**
     * 替换所有java文件中出现的api中localhost:port部分
     * @param projectPath
     * @param providerPath
     */
    public void replaceUrl(String projectPath, List<String> providerPath) {
        List<File> list = IO.getAllFiles(projectPath + "/src/main/java/");
        for (File file : list) {
            for (String path : providerPath) {
                IO.replaceFileStr(file, "localhost:" + IO.getServicePort(path), getServiceName(path));
            }
        }
    }

    /**
     * 得到某项目的配置文件中的定义的spring.application.name
     * @param projectPath
     * @return
     */
    private String getServiceName(String projectPath) {
        File file = IO.getFile(projectPath + "/src/main/resources", "application.properties");
        String[] str = IO.readFromFile(file).split("\n");
        String name = "";
        if(file.getName().contains("properties")) {
            for (String s : str) {
                s = IO.deleteSpaces(s);
                if (s.contains("spring.application.name")) {
                    name = s.substring(s.indexOf("=") + 1);
                    break;
                }
            }
        }
        else {
            int sret = 0, aret = 0;
            for (String s : str) {
                if (s.equals("spring:")) {
                    sret = 1;
                }
                if (s.equals("application:") && sret == 1) {
                    aret = 1;
                }
                if (s.equals("name:") && sret == 1 && aret == 1) {
                    s = IO.deleteSpaces(s);
                    name = s.substring(s.indexOf(":") + 1);
                    break;
                }
            }
        }
        return name;
    }

    /**
     * 添加Ribbon注解
     * @param projectPath
     * @throws IOException
     */
    public void addRibbon(String projectPath) throws IOException {
        File applicationFile = IO.getApplication(projectPath);
        RandomAccessFile raf=new RandomAccessFile(applicationFile,"rw");
        String line=null;
        boolean findImportPointer = false;
        boolean findAnnotationPointer = false;
        long importPointer = 0;
        long annotationPointer = 0;
        while((line=raf.readLine())!=null && (!findImportPointer || !findAnnotationPointer)){
            if (line.contains("import org.") && !findImportPointer) {
                importPointer = raf.getFilePointer();
                findImportPointer = true;
            }

            if(line.contains("@Bean") && !findAnnotationPointer){
                annotationPointer = raf.getFilePointer();
                findAnnotationPointer = true;
            }
        }
        String importPackage = "import org.springframework.cloud.client.loadbalancer.LoadBalanced;\n";
        IO.insert(importPointer, importPackage, applicationFile);
        String annotation="    @LoadBalanced\n";
        IO.insert(annotationPointer,annotation,applicationFile);
    }

//    public static void main(String[] args) throws Exception {
//        RibbonService ribbonService = new RibbonService();
//        String account = "H:/programs/web/Petstore_YuiChen/account_service";
//        String category = "H:/programs/web/Petstore_YuiChen/category_service";
//        String order = "H:/programs/web/Petstore_YuiChen/order_service";
//        String pet = "H:/programs/web/Petstore_YuiChen/pet_service";
//        List<String> list = new ArrayList<>();
//        list.add(account);
//        list.add(category);
//        list.add(order);
//        list.add(pet);
//        ribbonService.replaceUrl("H:/programs/bff_service", list);
//        ribbonService.addRibbon("H:/programs/bff_service");
//    }
}
