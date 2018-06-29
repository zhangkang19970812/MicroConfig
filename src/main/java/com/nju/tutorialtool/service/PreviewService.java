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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.*;
import java.util.regex.Pattern;
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

    public List<PreviewInfo> getHystrixInfo(List<ServiceInfo> serviceInfos, Map<String,List<String>> methodsMap) {
        List<PreviewInfo> result=new ArrayList<>();
//        List<ServiceInfo> serviceInfos=serviceInfoList.getServiceInfoList();

//        public class PreviewInfo {
//            private String serviceName;
//            private List<PreviewFileInfo> fileInfoList;

        for(ServiceInfo info:serviceInfos){
            //这个是每个 service有的
            List<PreviewFileInfo> servicePreview=new ArrayList<>();
            String servicePath=getProjectPath(info.getFolderName());
            String serviceName=info.getServiceName();

            //下面对一个service进行处理
//            System.out.println(servicePath);
            if(methodsMap.containsKey(serviceName)){
                controllerFiles=new ArrayList<>();
                List<String> serviceMethods=methodsMap.get(serviceName);
                List<File> controllers=getAllControllers(servicePath);
                System.out.println("account_service的controllers:"+controllers);

                for(File controllerFile:controllers){
                    List<String> methods=getMethodsFromOne(controllerFile);
                    String newContent="";
                    System.out.println("methods!~~~~:"+methods);
                    for(String s:methods){
                        //是要加fallback的方法
                        if(serviceMethods.contains(s)){
                            newContent+=getMethodContent(controllerFile,s);
                            newContent+="\n";
                        }
                    }
                    PreviewFileInfo fileInfo=new PreviewFileInfo(controllerFile.getName(),newContent,new ArrayList<>(1));
                    servicePreview.add(fileInfo);
                }
            }
            PreviewInfo previewInfo=new PreviewInfo(info.getServiceName(),servicePreview);
            result.add(previewInfo);
        }
        System.out.println(result);
        return result;
    }

    /**
     * 已知controller文件名称和方法名称，返回修改过的代码片段
     * @param controllerFile
     * @param methodName
     * @return
     */
    public String getMethodContent(File controllerFile, String methodName){
        String content="";
        Stack s=new Stack();
        RandomAccessFile raf= null;
        try {
            raf = new RandomAccessFile(controllerFile,"rw");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String line=null;
        String lastLine=null;

        int o=0;
        try {
            while((line=raf.readLine())!=null){

                if(Pattern.matches(".*RequestMethod.*",line)){
                    lastLine=line;

                    String methodLine=raf.readLine();
                    if(!methodLine.contains("@HystrixCommand")) {
                        List<String> splits = splitMethodLine(methodLine);
                        String method = splits.get(2);
                        String returnTye = splits.get(1);
                        String parameter = getParameters(methodLine);


                        if (method.equals(methodName)){
                            content+=lastLine;
                            content+='\n';

                            content+="    @HystrixCommand(fallbackMethod = "+"\""+methodName+"Fallback"+"\")\n";
                            s.push("{");
                            content+=methodLine;
                            content+='\n';

                            while(!s.isEmpty()){
                                line=raf.readLine();
                                if (!line.equals("")) {

                                    if (line.contains("{")) {
                                        s.push('{');
                                    }
                                    if (line.contains("}") && !s.isEmpty()) {
                                        s.pop();
                                    }
                                    content += line;
                                    content += '\n';
                                }
                            }
                            o=1;

                            content+="    public "+returnTye+" "+methodName+"Fallback("+parameter+"){\n"+"        "+ReturnType.getFallbackReturns(returnTye)+"\n"+"    }\n";
                        }
                    }
                }
//                break;
                if (o==1){
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(content);
        return content;
    }

    static List<File> controllerFiles=new ArrayList<>();

    /**
     * 得到某个service目录下所有的controller文件
     * @param url
     * @return
     */
    public List<File> getAllControllers(String url){
        List<File> files=new ArrayList<>();
        getAllFiles(url);
        String pattern=".*Controller\\.java";
        for(File f:controllerFiles){
            if(Pattern.matches(pattern,f.getName())){
                files.add(f);
            }
        }
        return files;
    }
    /**
     * 已知目录，得到下面所有文件名.java
     * @param url
     * @return
     */
    public static List<File> getAllFiles(String url){
        List<File> files=new ArrayList<>();
        File file=new File(url);
        File[] files1=file.listFiles();
        if(files1==null){
            return files;
        }
        for(File f:files1){
            if(f.isFile() && Pattern.matches(".*java",f.getName())){
                files.add(f);
                controllerFiles.add(f);
            }else if(f.isDirectory()){
                getAllFiles(f.getAbsolutePath());
            }
        }
        return files;
    }

    public static String getParameters(String methodLine){
        String result=methodLine.substring(methodLine.indexOf('(')+1,methodLine.indexOf(')'));
        if(result.contains("@RequestBody")){
            result=result.replace("@RequestBody ","");
        }
        if(result.contains("@PathVariable")){
            result=result.replace("@PathVariable ","");
        }
//        System.out.print(result);
        return result;
    }

    /**
     * 得到一个Controller文件中的所有方法名
     * @param f
     * @return
     * @throws IOException
     */
    public List<String> getMethodsFromOne(File f) {
        List<String> result=new ArrayList<>();
        RandomAccessFile raf= null;
        try {
            raf = new RandomAccessFile(f,"rw");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String line=null;
        String lastLine=null;

        try {
            while((line=raf.readLine())!=null){
                lastLine=line;
                if(Pattern.matches(".*RequestMethod.*",line)){
                    String methodLine=raf.readLine();
                    if(!methodLine.contains("@HystrixCommand")) {
                        List<String> splits = splitMethodLine(methodLine);
                        String methodName = splits.get(2);
                        result.add(methodName);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(result);
        return result;
    }
    /**
     * 得到方法中的片段
     * @param methodLine
     * @return
     */
    public static List<String> splitMethodLine(String methodLine){
        List<String> result=new ArrayList<>();
        int space=0;
        for(int i=0;i<methodLine.length();i++){
            if(result.size()==0 && methodLine.charAt(i)==' '){
                space++;
            }
            if(methodLine.charAt(i)!=' '){
                String news=methodLine.charAt(i)+"";
                while(i<methodLine.length()-1 && methodLine.charAt(++i)!=' ' && methodLine.charAt(i)!='(' && methodLine.charAt(i)!=')'){
                    news+=methodLine.charAt(i);
                }
                result.add(news);
            }
        }
        result.add(space+"");
        return result;
    }


}

