package com.nju.tutorialtool.template.mysql;

import com.nju.tutorialtool.base.SqlFile;

public class PrivilegesFile extends SqlFile {
    private static final String FILE_NAME = "privileges.sql";

    public PrivilegesFile(String fileDir, String user, String password, String database) {
        super(fileDir, FILE_NAME);
        StringBuilder sql = new StringBuilder();
        sql.append("use mysql;\r\n" +
                "select host, user from user;\r\n" +
                "create user "+ user +" identified by '"+ password +"';\r\n" +
                "grant all on "+ database +".* to "+ user +"@'%' identified by '"+ password +"' with grant option;\r\n" +
                "flush privileges;");
        init(sql);
    }
}
