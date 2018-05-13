package com.nju.tutorialtool.template.mysql;

import com.nju.tutorialtool.base.SqlFile;
import com.nju.tutorialtool.model.Table;

import java.util.List;
import java.util.Map;

public class SchemaFile extends SqlFile {
    private static final String FILE_NAME = "schema.sql";
//    private String database;
//    private List<Table> tables;

    public SchemaFile(String fileDir, String database, List<Table> tables) {
        super(fileDir, FILE_NAME);
        StringBuilder sql = new StringBuilder();
        sql.append("create database `" + database + "` default character set utf8 collate utf8_general_ci;\r\n" +
                   "use " + database + ";\r\n");
        for (Table table : tables) {
            Map<String, String> columns = table.getColumns();
            sql.append("DROP TABLE IF EXISTS `" + table.getTableName() + "`;\r\n" +
                       "CREATE TABLE `account` (\r\n");
            for (Map.Entry<String, String> entry : columns.entrySet()) {
                sql.append("`"+ entry.getKey() +"` " + entry.getValue() + ",\r\n");
            }
            sql.append("PRIMARY KEY (`" + table.getPrimary_key() + "`)\r\n" +
                       ") ENGINE=InnoDB;");
        }
        init(sql);
    }
}
