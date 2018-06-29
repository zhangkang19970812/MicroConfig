package com.nju.tutorialtool.service;

import com.nju.tutorialtool.dao.ConfigurationDao;
import com.nju.tutorialtool.dao.ConfigurationItemDao;
import com.nju.tutorialtool.model.Configuration;
import com.nju.tutorialtool.model.ConfigurationItem;
import com.nju.tutorialtool.model.PreviewInfo;
import com.nju.tutorialtool.model.ServiceInfo;
import com.nju.tutorialtool.util.io.IO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ConfigurationService {
    @Autowired
    private ServiceDirMapService serviceDirMapService;
    @Autowired
    private UserService userService;
    @Autowired
    private ConfigurationDao configurationDao;
    @Autowired
    private ConfigurationItemDao configurationItemDao;

    /**
     * 后台自动配置服务（默认）
     * @param serviceInfo
     */
    public void editConfiguration(ServiceInfo serviceInfo) {
        File file = IO.getFile(userService.getUserFolder() + File.separator + serviceInfo.getFolderName() + "/src/main/resources", "application.properties");
        String[] str = IO.readFromFile(file).split("\n");
        boolean ret = false;

        for (String s : str) {
            if ("spring.datasource.url".equals(s.split("=")[0])) {
                String replace = "jdbc:mysql://"+ serviceInfo.getFolderName() + "_mysql";
                IO.replaceFileStr(file, s.split("=")[1].substring(0, s.indexOf(":3306")), replace);
            }
            if ("spring.application.name".equals(s.split("=")[0])) {
                IO.replaceFileStr(file, s, "spring.application.name=" + getSpringApplicationName(serviceInfo.getServiceName()));
                ret = true;
            }
        }
        String items = "";
        if (!ret) {
            items = getDefaultProperties(serviceInfo);
        }
        else {
           items = "eureka.client.service-url.defaultZone=http://eureka:8761/eureka/\n" +
                    "eureka.instance.preferIpAddress=true";
        }
        IO.insertToEnd(file, items);
    }


    /**
     * 生成默认配置
     * @return
     */
    public String getDefaultProperties(ServiceInfo serviceInfo) {
        return "spring.application.name=" + getSpringApplicationName(serviceInfo.getServiceName()) + "\n" +
                "eureka.client.service-url.defaultZone=http://eureka:8761/eureka/\n" +
                "eureka.instance.preferIpAddress=true";
    }

    /**
     * 生成默认配置,返回ConfigurationItem列表
     * @param serviceInfo
     * @return
     */
    public List<ConfigurationItem> getDefaultPropertiesItems(ServiceInfo serviceInfo) {
        List<ConfigurationItem> list = new ArrayList<>();
        for (String s : getDefaultProperties(serviceInfo).split("\n")) {
            list.add(new ConfigurationItem(s.split("=")[0], s.split("=")[1]));
        }
        return list;
    }

    /**
     * 根据服务名称生成服务的spring-application-name值
     * @param serviceName
     * @return
     */
    public String getSpringApplicationName(String serviceName) {
        if (serviceName.contains("_")) {
            return serviceName.replaceAll("_", "-");
        }
        return serviceName;
    }

    /**
     * 得到某项目的配置文件中的定义的spring.application.name
     * @param projectPath
     * @return
     */
    public String getServiceName(String projectPath) {
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
     * 修改某项目的配置文件
     * @param projectPath
     * @param list
     */
    public void editConfiguration(String projectPath, List<ConfigurationItem> list) {
        File file = IO.getFile(projectPath + "/src/main/resources", "application.properties");
        String[] str = IO.readFromFile(file).split("\n");
        for (ConfigurationItem configurationItem : list) {
            int ret = 0;
            for (String s : str) {
                if (configurationItem.getItemName().equals(s.split("=")[0])) {
                    IO.replaceFileStr(file, s, configurationItem.toString());
                    ret = 1;
                    break;
                }
            }
            if (ret == 0) {
                IO.insertToEnd(file, configurationItem.toString());

            }
        }
    }

    /**
     * 返回所有服务的配置，用于之后的配置检查
     * @return
     */
    public List<Configuration> getAllServicesConfigurations() {
        List<Configuration> list = new ArrayList<>();
        List<ServiceInfo> serviceInfoList = serviceDirMapService.getAllServices();
        for (ServiceInfo serviceInfo : serviceInfoList) {
            String serviceRootPath = userService.getUserFolder() + File.separator + serviceInfo.getFolderName();
            list.add(new Configuration(getConfigurations(serviceRootPath)));
        }
        return list;
    }

    /**
     * 返回某服务的配置项列表
     * @param projectPath
     * @return
     */
    public List<ConfigurationItem> getConfigurations(String projectPath) {
        List<ConfigurationItem> list = new ArrayList<>();
        File file = IO.getFile(projectPath + "/src/main/resources", "application.properties");
        String[] str = IO.readFromFile(file).split("\n");
        for (String s : str) {
            s = IO.deleteSpaces(s);
            if (s.contains("=")){
                String[] c = s.split("=");
                ConfigurationItem configurationItem = new ConfigurationItem(c[0], c[1]);
                list.add(configurationItem);
            }
        }
        return list;
    }

    /**
     * 从数据库中得到某服务的port
//     * @param serviceInfo
     * @return
     */
//    public String getPort(ServiceInfo serviceInfo) {
//        for (ConfigurationItem configurationItem : serviceInfo.getConfig().getList()) {
//            if (configurationItem.getItemName().equals("server.port")) {
//                return configurationItem.getValue();
//            }
//        }
//        return "";
//    }

    public boolean checkMysql(String projectPath) {
        List<ConfigurationItem> list = getConfigurations(projectPath);
        for (ConfigurationItem configurationItem : list) {
            if ("spring.datasource.url".equals(configurationItem.getItemName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 返回某服务的配置项中数据库名称，用户，密码
     * @param projectPath
     * @return
     */
    public String[] getMysqlInfo(String projectPath) {
        String[] res = new String[3];
        List<ConfigurationItem> list = getConfigurations(projectPath);
        boolean database = false, username = false, password = false;
        for (ConfigurationItem configurationItem : list) {
            if (database && username && password) {
                break;
            }
            if ("spring.datasource.url".equals(configurationItem.getItemName())) {
                String url = configurationItem.getValue();
                int start = url.indexOf(":3306/") + 6, end = (url.contains("?")) ? url.indexOf("?") : url.length();
                res[0] = url.substring(start, end);
                database = true;
                continue;
            }
            if ("spring.datasource.username".equals(configurationItem.getItemName())) {
                res[1] = configurationItem.getValue();
                username = true;
                continue;
            }
            if ("spring.datasource.password".equals(configurationItem.getItemName())) {
                res[2] = configurationItem.getValue();
                password = true;
            }
        }
        return res;
    }

    public void addConfiguration(Configuration configuration) {
        configurationDao.save(configuration);
    }

    public void addConfigurationItem(ConfigurationItem configurationItem) {
        configurationItemDao.save(configurationItem);
    }

//    /**
//     * 将各服务的配置文件中关于数据库的配置都更改一下
//     */
//    public void editServicesMysqlConfigurations() {
//        List<ServiceInfo> serviceInfoList = serviceDirMapService.getAllServices();
//        for (ServiceInfo serviceInfo : serviceInfoList) {
//            String serviceRootPath = userService.getUserFolder() + File.separator + serviceInfo.getFolderName();
//            File file = IO.getFile(serviceRootPath + "/src/main/resources", "application.properties");
//            String[] str = IO.readFromFile(file).split("\n");
//            for (String s : str) {
//                if (s.split("=")[0].equals("spring.datasource.url")) {
//                    String replace = "jdbc:mysql://"+ serviceInfo.getMysqlInfo().getProjectName();
//                    IO.replaceFileStr(file, s.split("=")[1].substring(0, s.indexOf(":3306")), replace);
//                }
//                if (s.split("=")[0].equals("spring.datasource.username")) {
//                    IO.replaceFileStr(file, s, "spring.datasource.username=" + serviceInfo.getMysqlInfo().getUser());
//                }
//                if (s.split("=")[0].equals("spring.datasource.password")) {
//                    IO.replaceFileStr(file, s, "spring.datasource.password=" + serviceInfo.getMysqlInfo().getPassword());
//                }
//            }
//        }
//    }

//    public static void main(String[] args) {
//        ConfigurationItem configurationItem1 = new ConfigurationItem("server.port", "5000");
//        ConfigurationItem configurationItem2 = new ConfigurationItem("spring.application.name", "account");
//        ConfigurationItem configurationItem3 = new ConfigurationItem("eureka.client.allow-redirects", "false");
//        List<ConfigurationItem> list = new ArrayList<>();
//        list.add(configurationItem1);
//        list.add(configurationItem2);
//        list.add(configurationItem3);
//        ConfigurationService configurationService = new ConfigurationService();
//        configurationService.editConfiguration("H:/programs/account_service", list);
//    }
}
