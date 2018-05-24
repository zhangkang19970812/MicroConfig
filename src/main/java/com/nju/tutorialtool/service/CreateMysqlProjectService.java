package com.nju.tutorialtool.service;

import com.nju.tutorialtool.model.Column;
import com.nju.tutorialtool.model.MysqlInfo;
import com.nju.tutorialtool.model.Table;
import com.nju.tutorialtool.template.mysql.PrivilegesFile;
import com.nju.tutorialtool.template.mysql.SchemaFile;
import com.nju.tutorialtool.template.mysql.SetupShFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CreateMysqlProjectService {
    @Autowired
    private CreateDockerfileService createDockerfileService;

    public void createMysqlProject(MysqlInfo mysqlInfo) throws Exception {
        SchemaFile schemaFile = new SchemaFile(mysqlInfo.getBaseDir() + "/" + mysqlInfo.getProjectName(),
                mysqlInfo.getDatabase(), mysqlInfo.getTables());
        schemaFile.generate();

        PrivilegesFile privilegesFile = new PrivilegesFile(mysqlInfo.getBaseDir() + "/" + mysqlInfo.getProjectName(),
                mysqlInfo.getUser(), mysqlInfo.getPassword(), mysqlInfo.getDatabase());
        privilegesFile.generate();

        SetupShFile setupShFile = new SetupShFile(mysqlInfo.getBaseDir() + "/" + mysqlInfo.getProjectName());
        setupShFile.generate();

        createDockerfileService.createDockerfile(mysqlInfo.getBaseDir() + "/" + mysqlInfo.getProjectName(), "mysql");
    }

//    public static void main(String[] args) throws Exception {
//        CreateMysqlProjectService createMysqlProjectService = new CreateMysqlProjectService();
//        Column column1 = new Column("id", "INT(20)");
//        Column column2 = new Column("username", "VARCHAR(255)");
//        Column column3 = new Column("password", "VARCHAR(255)");
//        Column column4 = new Column("email", "VARCHAR(255)");
//        List<Column> list = new ArrayList<>();
//        list.add(column1);
//        list.add(column2);
//        list.add(column3);
//        list.add(column4);
//        Table table = new Table("account", list, "id");
//        List<Table> tables = new ArrayList<>();
//        tables.add(table);
//        MysqlInfo mysqlInfo = new MysqlInfo("H:/programs", "account_mysql", "pet_store_account", tables, "docker", "123456");
//        createMysqlProjectService.createMysqlProject(mysqlInfo);
//    }
}
