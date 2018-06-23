package com.nju.tutorialtool.service;

import com.nju.tutorialtool.model.*;
import com.nju.tutorialtool.template.common.PomXmlResourceFile;
import com.nju.tutorialtool.template.spring.ApplicationClassFile;
import com.nju.tutorialtool.template.spring.ApplicationPropertiesResourceFile;
import com.nju.tutorialtool.util.DependencyConstant;
import com.nju.tutorialtool.util.io.IO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class PreviewService {
    @Autowired
    private GitService gitService;
    @Autowired
    private UserService userService;

    public List<PreviewInfo> getEurekaInfo(SpringCloudInfo springCloudInfo) {
        List<PreviewInfo> list = new ArrayList<>();
        list.addAll(getEurekaServerInfo(springCloudInfo));

        for (ServiceInfo serviceInfo : gitService.getAllService()) {
            List<String> dependencies = new ArrayList<>();
            dependencies.add("eurekaDiscovery");

            //Application类添加注解
            File applicationFile = IO.getApplication(getProjectPath(serviceInfo.getFolderName()));
            assert applicationFile != null;
            String contents = IO.readFromFile(applicationFile);
            String newContents = contents;
            String importPackage = "import org.springframework.cloud.client.discovery.EnableDiscoveryClient;\n";
            String annotation = "@EnableDiscoveryClient\n";

            String line = null;
            int count = 0, importCount= 0, annotationCount = 0, length = 0;
            boolean findImportPointer = false;
            boolean findAnnotationPointer = false;
            while ((count < contents.split("\n").length) && (line = contents.split("\n")[count]) != null && (!findImportPointer || !findAnnotationPointer)) {
                count ++;
                length += line.length() + 1;
                if (line.contains("import org.") && !findImportPointer) {
                    importCount = count + 1;
                    newContents = insertString(newContents, importPackage, length);
                    findImportPointer = true;
                }
                if (line.contains("@SpringBootApplication") && !findAnnotationPointer) {
                    annotationCount = count + 1;
                    newContents = insertString(newContents, annotation, length + importPackage.length());
                    findAnnotationPointer = true;
                }
            }
            PreviewInfo servicePreview = new PreviewInfo(serviceInfo.getServiceName(), applicationFile.getName(), newContents, new ArrayList<>(Arrays.asList(importCount, annotationCount)));
            list.add(servicePreview);

            //pom文件添加依赖
            File file = IO.getFile(getProjectPath(serviceInfo.getFolderName()), "pom.xml");
            contents = IO.readFromFile(file);
            newContents = contents;
            count = 0;
            length = 0;
            int dependencyCount = 0;
            line = null;
            while ((count < contents.split("\n").length) && (line = contents.split("\n")[count]) != null) {
                count ++;
                length += line.length() + 1;
                if (line.contains("</dependency>")) {
                    annotation = DependencyConstant.getDependencies(dependencies);
                    dependencyCount = count + 1;
                    newContents = insertString(newContents, annotation, length);
                    break;
                }
            }
            servicePreview = new PreviewInfo(serviceInfo.getServiceName(), "pom.xml", newContents, getLineList(dependencyCount, dependencyCount + 3));
            list.add(servicePreview);

            //配置文件修改
            File proFile = IO.getFile(getProjectPath(serviceInfo.getFolderName()) + "/src/main/resources", "application.properties");
            String str = IO.readFromFile(proFile);
            int fileLines = str.split("\n").length;
            boolean ret = false;
            count = 0;
            int springCount = 0;
            for (String s : str.split("\n")) {
                count ++;
                if ("spring.application.name".equals(s.split("=")[0])) {
                    springCount = count;
                    str.replace(s, "spring.application.name=" + getSpringApplicationName(serviceInfo.getServiceName()));
                    ret = true;
                    break;
                }
            }
            if (!ret) {
                str += "spring.application.name=" + getSpringApplicationName(serviceInfo.getServiceName()) + "\n" +
                        "eureka.client.service-url.defaultZone=http://eureka:8761/eureka/\n" +
                        "eureka.instance.preferIpAddress=true";
                servicePreview = new PreviewInfo(serviceInfo.getServiceName(), "application.properties", str, getLineList(fileLines + 1, fileLines + 3));
                list.add(servicePreview);
            }
            else {
                str += "eureka.client.service-url.defaultZone=http://eureka:8761/eureka/\n" +
                        "eureka.instance.preferIpAddress=true";
                servicePreview = new PreviewInfo(serviceInfo.getServiceName(), "application.properties", str, new ArrayList<>(Arrays.asList(springCount, fileLines + 1, fileLines + 2)));
                list.add(servicePreview);
            }

        }

        return list;
    }

    public List<PreviewInfo> getZuulInfo(SpringCloudInfo springCloudInfo) {
        List<PreviewInfo> list = new ArrayList<>();
        ProjectInfo projectInfo = new ProjectInfo(springCloudInfo);
        projectInfo.addDependency("zuul");
        PomXmlResourceFile pxrf = new PomXmlResourceFile("", projectInfo.getGroupId(), projectInfo.getArtifactId(), projectInfo.getDependencies());
        PreviewInfo pomPreview = new PreviewInfo(projectInfo.getArtifactId(), "pom.xml", pxrf.getResource(), getLineList(32, 39));
        list.add(pomPreview);

        ApplicationPropertiesResourceFile ayrf = new ApplicationPropertiesResourceFile("", "zuul");
        PreviewInfo proPreview = new PreviewInfo(projectInfo.getArtifactId(), "application.properties", ayrf.getResource(), getLineList(1, 3));
        list.add(proPreview);

        ApplicationClassFile acf = new ApplicationClassFile("", toPackage(projectInfo.getGroupId() + "/" + projectInfo.getArtifactId()), projectInfo.getDependencies());
        PreviewInfo appPreview = new PreviewInfo(projectInfo.getArtifactId(), "Application.java", acf.getResource(), getLineList(7, 13));
        list.add(appPreview);

        return list;
    }

    private List<PreviewInfo> getEurekaServerInfo(SpringCloudInfo springCloudInfo) {
        List<PreviewInfo> list = new ArrayList<>();
        ProjectInfo projectInfo = new ProjectInfo(springCloudInfo);
        projectInfo.addDependency("eurekaServer");
        PomXmlResourceFile pxrf = new PomXmlResourceFile("", projectInfo.getGroupId(), projectInfo.getArtifactId(), projectInfo.getDependencies());
        PreviewInfo pomPreview = new PreviewInfo(projectInfo.getArtifactId(), "pom.xml", pxrf.getResource(), getLineList(32, 35));
        list.add(pomPreview);

        ApplicationPropertiesResourceFile ayrf = new ApplicationPropertiesResourceFile("", "eurekaServer");
        PreviewInfo proPreview = new PreviewInfo(projectInfo.getArtifactId(), "application.properties", ayrf.getResource(), getLineList(1, 4));
        list.add(proPreview);

        ApplicationClassFile acf = new ApplicationClassFile("", toPackage(projectInfo.getGroupId() + "/" + projectInfo.getArtifactId()), projectInfo.getDependencies());
        PreviewInfo appPreview = new PreviewInfo(projectInfo.getArtifactId(), "Application.java", acf.getResource(), getLineList(7, 13));
        list.add(appPreview);

        return list;
    }

    private String getSpringApplicationName(String serviceName) {
        if (serviceName.contains("_")) {
            return serviceName.replaceAll("_", "-");
        }
        return serviceName;
    }

    private String toPackage(String packageDir) {
        return packageDir.replaceAll("/", ".");
    }

    private String toDir(String packageDir) {
        return packageDir.replaceAll("\\.", "/");
    }

    private List<Integer> getLineList(int start, int end) {
        List<Integer> list = new ArrayList<>();
        for (int i = start; i <= end; i ++) {
            list.add(i);
        }
        return list;
    }

    private String getProjectPath(String folderName) {
        return userService.getUserFolder() + File.separator + folderName;
    }

    private String insertString(String original, String append, int index) {
        return original.substring(0, index) + append + original.substring(index);
    }

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>(Arrays.asList(0, 1));
        for (Integer integer : list) {
            System.out.println(integer);
        }
    }
}
