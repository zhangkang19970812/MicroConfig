package com.nju.tutorialtool.service;

import com.nju.tutorialtool.model.*;
import com.nju.tutorialtool.model.dto.RibbonDTO;
import com.nju.tutorialtool.template.common.PomXmlResourceFile;
import com.nju.tutorialtool.template.spring.ApplicationClassFile;
import com.nju.tutorialtool.template.spring.ApplicationPropertiesResourceFile;
import com.nju.tutorialtool.util.DependencyConstant;
import com.nju.tutorialtool.util.io.IO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PreviewService {
    @Autowired
    private GitService gitService;
    @Autowired
    private UserService userService;
    @Autowired
    private ConfigurationService configurationService;

    public PreviewInfo getEurekaServerInfo(SpringCloudInfo springCloudInfo) {
        return getInfo(springCloudInfo, "eurekaServer");
    }

    public List<PreviewInfo> getEurekaClientInfo(List<ServiceInfo> serviceInfoList) {
        List<PreviewInfo> list = new ArrayList<>();

        for (ServiceInfo serviceInfo : serviceInfoList) {
            List<PreviewFileInfo> fileInfoList = new ArrayList<>();
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
            fileInfoList.add(new PreviewFileInfo(applicationFile.getName(), newContents, new ArrayList<>(Arrays.asList(importCount, annotationCount))));

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
            fileInfoList.add(new PreviewFileInfo("pom.xml", newContents, getLineList(dependencyCount, dependencyCount + 3)));

            //配置文件修改
            File proFile = IO.getFile(getProjectPath(serviceInfo.getFolderName()) + "/src/main/resources", "application.properties");
            String str = IO.readFromFile(proFile);
            int fileLines = str.split("\n").length;
            count = 0;
            int springCount = 0;
            if (str.contains("spring.application.name")) {
                for (String s : str.split("\n")) {
                    count ++;
                    if ("spring.application.name".equals(s.split("=")[0])) {
                        springCount = count;
                        str = str.replace(s, "spring.application.name=" + configurationService.getSpringApplicationName(serviceInfo.getServiceName()));
                        break;
                    }
                }
                str += "eureka.client.service-url.defaultZone=http://eureka:8761/eureka/\n" +
                        "eureka.instance.preferIpAddress=true";
                fileInfoList.add(new PreviewFileInfo("application.properties", str, new ArrayList<>(Arrays.asList(springCount, fileLines + 1, fileLines + 2))));
            }
            else {
                str += "spring.application.name=" + configurationService.getSpringApplicationName(serviceInfo.getServiceName()) + "\n" +
                        "eureka.client.service-url.defaultZone=http://eureka:8761/eureka/\n" +
                        "eureka.instance.preferIpAddress=true";
                fileInfoList.add(new PreviewFileInfo("application.properties", str, getLineList(fileLines + 1, fileLines + 3)));
            }

            list.add(new PreviewInfo(serviceInfo.getServiceName(), fileInfoList));
        }

        return list;
    }

    public PreviewInfo getZuulInfo(SpringCloudInfo springCloudInfo) {
        return getInfo(springCloudInfo, "zuul");
    }

    private PreviewInfo getInfo(SpringCloudInfo springCloudInfo, String type) {
        List<PreviewFileInfo> list = new ArrayList<>();
        ProjectInfo projectInfo = new ProjectInfo(springCloudInfo);
        projectInfo.addDependency(type);

        PomXmlResourceFile pxrf = new PomXmlResourceFile("", projectInfo.getGroupId(), projectInfo.getArtifactId(), projectInfo.getDependencies());
        list.add(new PreviewFileInfo("pom.xml", pxrf.getResource(), getLineList(32, ("eurekaServer".equals(type)) ? 35 : 39)));

        ApplicationPropertiesResourceFile ayrf = new ApplicationPropertiesResourceFile("", type);
        list.add(new PreviewFileInfo("application.properties", ayrf.getResource(), getLineList(1, ("eurekaServer".equals(type)) ? 4 : 3)));

        ApplicationClassFile acf = new ApplicationClassFile("", toPackage(projectInfo.getGroupId() + "/" + projectInfo.getArtifactId()), projectInfo.getDependencies());
        list.add(new PreviewFileInfo("Application.java", acf.getResource(), getLineList(7, 13)));

        return new PreviewInfo(projectInfo.getArtifactId(), list);
    }

    public List<PreviewInfo> getRibbonInfo(List<RibbonDTO> ribbonDTOList, List<ServiceInfo> serviceInfoList) {
        List<PreviewInfo> list = new ArrayList<>();
        Map<String, String> service2folder = serviceInfoList.stream()
                .collect(Collectors.toMap(ServiceInfo::getServiceName, ServiceInfo::getFolderName));

        for (RibbonDTO ribbonDTO : ribbonDTOList) {
            List<PreviewFileInfo> fileInfoList = new ArrayList<>();

            //添加Ribbon注解
            File applicationFile = IO.getApplication(getProjectPath(service2folder.get(ribbonDTO.getConsumer())));
            String contents = IO.readFromFile(applicationFile);
            String newContents = contents;
            String importPackage = "import org.springframework.cloud.client.loadbalancer.LoadBalanced;\n";
            String annotation = "    @LoadBalanced\n";

            String line = null;
            boolean findImportPointer = false;
            boolean findAnnotationPointer = false;
            int count = 0, importCount= 0, annotationCount = 0, length = 0;
            while ((count < contents.split("\n").length) && (line = contents.split("\n")[count]) != null && (!findImportPointer || !findAnnotationPointer)) {
                count ++;
                length += line.length() + 1;
                if (line.contains("import org.") && !findImportPointer) {
                    importCount = count + 1;
                    newContents = insertString(newContents, importPackage, length);
                    findImportPointer = true;
                }
                if (line.contains("@Bean") && !findAnnotationPointer) {
                    if (contents.split("\n")[count].toLowerCase().contains("resttemplate")) {
                        annotationCount = count + 1;
                        newContents = insertString(newContents, annotation, length + importPackage.length());
                        findAnnotationPointer = true;
                    }
                }
            }
            fileInfoList.add(new PreviewFileInfo(applicationFile.getName(), newContents, new ArrayList<>(Arrays.asList(importCount, annotationCount))));

            //替换调用的url
            List<File> fileList = IO.getAllFiles(getProjectPath(service2folder.get(ribbonDTO.getConsumer())) + "/src/main/java/");
            List<String> providersDir = ribbonDTO.getProviders().stream()
                    .map(service2folder::get)
                    .collect(Collectors.toList());
            List<String> providersPath = new ArrayList<>();
            for (String s : providersDir) {
                providersPath.add(getProjectPath(s));
            }
            for (File file : fileList) {
                String str = IO.readFromFile(file);
                count = 0;
                List<Integer> countList = new ArrayList<>();
                boolean ret = false;
                for (String path : providersPath) {
                    if (str.contains("localhost:" + IO.getServicePort(path)) || str.contains("127.0.0.1:" + IO.getServicePort(path))) {
                        ret = true;
                        for (String s : str.split("\n")) {
                            count ++;
                            if (s.contains("localhost:" + IO.getServicePort(path))) {
                                countList.add(count);
                                String temp = s.replace("localhost:" + IO.getServicePort(path), configurationService.getServiceName(path));
                                str = str.replace(s, temp);
                            }
                            else if (s.contains("127.0.0.1:" + IO.getServicePort(path))) {
                                countList.add(count);
                                String temp = s.replace("127.0.0.1:" + IO.getServicePort(path), configurationService.getServiceName(path));
                                str = str.replace(s, temp);
                            }
                        }
                    }
                }
                if (ret) {
                    fileInfoList.add(new PreviewFileInfo(file.getName(), str, countList));
                }

            }
            list.add(new PreviewInfo(ribbonDTO.getConsumer(), fileInfoList));
        }

        return list;
    }

    private String toPackage(String packageDir) {
        return packageDir.replaceAll("/", ".");
    }

    private String toDir(String packageDir) {
        return packageDir.replaceAll("\\.", "/");
    }

    /**
     * 创建从start到end的行数列表
     * @param start
     * @param end
     * @return
     */
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

//    public static void main(String[] args) {
//        String s = "abc";
//        s = s.replace("d", "a");
//        s = s.replace("a", "f");
//        System.out.println(s);
//    }

    public List<PreviewInfo> getHystrixInfo(ServiceInfoList serviceInfoList, Map<String,List<String>> methodsMap){
        List<PreviewInfo> result=new ArrayList<>();
        return result;
    }
}
