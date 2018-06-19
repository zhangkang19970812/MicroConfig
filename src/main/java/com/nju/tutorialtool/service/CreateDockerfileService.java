package com.nju.tutorialtool.service;

import com.nju.tutorialtool.template.dockerfile.MysqlDockerfile;
import com.nju.tutorialtool.template.dockerfile.ServiceDockerfile;
import com.nju.tutorialtool.util.io.IO;
import org.springframework.stereotype.Service;

@Service
public class CreateDockerfileService {
    /**
     * 为项目创建Dockerfile
     * @param projectPath
     * @param type
     * @throws Exception
     */
    public void createDockerfile(String projectPath, String type) throws Exception {
        if (type.equals("service")) {
            ServiceDockerfile serviceDockerfile = new ServiceDockerfile(projectPath, IO.getServicePort(projectPath));
            serviceDockerfile.generate();
        }
        else {
            MysqlDockerfile mysqlDockerfile = new MysqlDockerfile(projectPath);
            mysqlDockerfile.generate();
        }
    }

//    public static void main(String[] args) throws Exception {
//        CreateDockerfileService createDockerfileService = new CreateDockerfileService();
//        DockerfileInfo dockerfileInfo = new DockerfileInfo("H:/programs/account_service", "service");
//        createDockerfileService.createDockerfile(dockerfileInfo);
//    }
}
