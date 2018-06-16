package com.nju.tutorialtool.service;

import com.nju.tutorialtool.model.ServiceInfo;
import com.nju.tutorialtool.template.mysql.PrivilegesFile;
import com.nju.tutorialtool.template.mysql.SetupShFile;
import com.nju.tutorialtool.util.sql.CreateTable;
import com.nju.tutorialtool.util.sql.GetEntityClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public class SqlService {
    @Autowired
    private CreateDockerfileService createDockerfileService;
    @Autowired
    private UserService userService;

    public void createSql(String serviceName) throws Exception {
        CreateTable.createSql(GetEntityClass.getEntity(userService.getUserFolder() + File.separator + serviceName), userService.getUserFolder() + File.separator + serviceName + "_mysql/schema.sql");

//        PrivilegesFile privilegesFile = new PrivilegesFile(userService.getUserFolder() + File.separator + mysqlInfo.getProjectName(),
//                mysqlInfo.getUser(), mysqlInfo.getPassword(), mysqlInfo.getDatabase());
//        privilegesFile.generate();
//
//        SetupShFile setupShFile = new SetupShFile(userService.getUserFolder() + File.separator + mysqlInfo.getProjectName());
//        setupShFile.generate();
//
//        createDockerfileService.createDockerfile(userService.getUserFolder() + File.separator + mysqlInfo.getProjectName(), "mysql");
    }
}
