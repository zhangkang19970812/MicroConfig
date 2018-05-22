package com.nju.tutorialtool.service;

import com.nju.tutorialtool.model.DockerfileInfo;
import com.nju.tutorialtool.template.dockerfile.MysqlDockerfile;
import com.nju.tutorialtool.template.dockerfile.ServiceDockerfile;
import com.nju.tutorialtool.util.io.IO;
import org.springframework.stereotype.Service;

@Service
public class CreateDockerfileService {
    /**
     * 为项目创建Dockerfile
     * @param dockerfileInfo
     * @throws Exception
     */
    public void createDockerfile(DockerfileInfo dockerfileInfo) throws Exception {
        if (dockerfileInfo.getProjectType().equals("service")) {
            ServiceDockerfile serviceDockerfile = new ServiceDockerfile(dockerfileInfo.getProjectPath(), IO.getServicePort(dockerfileInfo.getProjectPath()));
            serviceDockerfile.generate();
        }
        else {
            MysqlDockerfile mysqlDockerfile = new MysqlDockerfile(dockerfileInfo.getProjectPath());
            mysqlDockerfile.generate();
        }
    }
}
