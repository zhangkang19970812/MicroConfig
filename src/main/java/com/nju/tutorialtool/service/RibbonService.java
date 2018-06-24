package com.nju.tutorialtool.service;

import com.nju.tutorialtool.util.io.IO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;

@Service
public class RibbonService {

    @Autowired
    private ConfigurationService configurationService;

    /**
     * 替换所有java文件中出现的api中localhost:port部分
     * @param projectPath
     * @param providerPath
     */
    public void replaceUrl(String projectPath, List<String> providerPath) {
        List<File> list = IO.getAllFiles(projectPath + "/src/main/java/");
        for (File file : list) {
            for (String path : providerPath) {
                IO.replaceFileStr(file, "localhost:" + IO.getServicePort(path), configurationService.getServiceName(path));
                IO.replaceFileStr(file, "127.0.0.1:" + IO.getServicePort(path), configurationService.getServiceName(path));
            }
        }
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
                line = raf.readLine();
                annotationPointer = raf.getFilePointer();
                if (line.toLowerCase().contains("resttemplate")) {
                    findAnnotationPointer = true;
                }
                else {
                    annotationPointer = 0;
                }
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
