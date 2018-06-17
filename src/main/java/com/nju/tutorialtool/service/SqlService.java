package com.nju.tutorialtool.service;

import com.nju.tutorialtool.template.mysql.PrivilegesFile;
import com.nju.tutorialtool.template.mysql.SetupShFile;
import com.nju.tutorialtool.util.sql.CreateTable;
import com.nju.tutorialtool.util.sql.GetEntityClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SqlService {
    @Autowired
    private CreateDockerfileService createDockerfileService;
    @Autowired
    private ConfigurationService configurationService;

    public void createMysqlProject(String projectPath) throws Exception {
        String[] mysql = configurationService.getMysqlInfo(projectPath);
//        CreateTable.createSql(mysql[0], GetEntityClass.getEntity(projectPath), projectPath + "_mysql/schema.sql");

        PrivilegesFile privilegesFile = new PrivilegesFile(projectPath + "_mysql", mysql[1], mysql[2], mysql[0]);
        privilegesFile.generate();

        SetupShFile setupShFile = new SetupShFile(projectPath + "_mysql");
        setupShFile.generate();

        createDockerfileService.createDockerfile(projectPath + "_mysql", "mysql");
    }
}
