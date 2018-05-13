package com.nju.tutorialtool.service;

import com.nju.tutorialtool.model.MysqlInfo;
import com.nju.tutorialtool.template.mysql.PrivilegesFile;
import com.nju.tutorialtool.template.mysql.SchemaFile;
import com.nju.tutorialtool.template.mysql.SetupShFile;
import org.springframework.stereotype.Service;

@Service
public class CreateMysqlProjectService {
    public void createMysqlProject(MysqlInfo mysqlInfo) throws Exception {
        SchemaFile schemaFile = new SchemaFile(mysqlInfo.getBaseDir() + "/" + mysqlInfo.getProjectName(),
                mysqlInfo.getDatabase(), mysqlInfo.getTables());
        schemaFile.generate();

        PrivilegesFile privilegesFile = new PrivilegesFile(mysqlInfo.getBaseDir() + "/" + mysqlInfo.getProjectName(),
                mysqlInfo.getUser(), mysqlInfo.getPassword(), mysqlInfo.getDatabase());
        privilegesFile.generate();

        SetupShFile setupShFile = new SetupShFile(mysqlInfo.getBaseDir() + "/" + mysqlInfo.getProjectName());
        setupShFile.generate();
    }
}
