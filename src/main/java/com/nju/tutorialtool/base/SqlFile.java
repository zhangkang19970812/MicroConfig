package com.nju.tutorialtool.base;

import java.io.File;
import java.io.FileWriter;

public class SqlFile {
    protected String fileDir;
    protected String fileName;
    protected StringBuilder sql = new StringBuilder();

    public SqlFile(String fileDir, String fileName) {
        super();
        this.fileDir = fileDir;
        this.fileName = fileName;
    }
    protected void init(StringBuilder sql) {
        this.sql = sql;
    }
    protected File makeSqlDir() throws Exception{
        File dir = new File(fileDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }
    protected File makeSqlFile() throws Exception{
        File dir = makeSqlDir();
        File file = new File(dir, fileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }
    public void generate() throws Exception{
        //startWrite
        File file = makeSqlFile();
        FileWriter fileWriter = new FileWriter(file);
        //initString
        StringBuilder stringBuilder = new StringBuilder();
        //addResource
        if(sql != null) {
            stringBuilder.append(sql.toString());
        }
        //writeAll
        fileWriter.write(stringBuilder.toString());
        //endWrite
        fileWriter.flush();
        fileWriter.close();
    }
}
