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
        String importPackage = "import org.springframework.cloud.client.loadbalancer.LoadBalanced;";
        String annotation="    @LoadBalanced";
        boolean findImportPointer = false;
        boolean findAnnotationPointer = false;
        String[] content = IO.readFromFile(applicationFile).split("\n");
        for (int i = 0; i < content.length; i ++) {
            if (findImportPointer && findAnnotationPointer) {
                break;
            }
            if (content[i].contains("import org.") && !findImportPointer) {
                IO.replaceFileStr(applicationFile, content[i], content[i] + "\n" + importPackage);
                findImportPointer = true;
            }
            if(content[i].contains("@Bean") && !findAnnotationPointer){
                if (content[i + 1].toLowerCase().contains("resttemplate")) {
                    IO.replaceFileStr(applicationFile, content[i] + "\n" + content[i + 1], content[i] + "\n" + annotation + "\n" + content[i + 1]);
                    findAnnotationPointer = true;
                }
            }
        }
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
