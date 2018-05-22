package com.nju.tutorialtool.base;

import java.io.File;
import java.io.FileWriter;

public class DockerFile {
    protected String fileDir;
    protected static final String FILE_NAME = "DockerfileInfo";
    protected String instructions;

    public DockerFile(String fileDir) {
        super();
        this.fileDir = fileDir;
    }
    protected void init(String instructions) {
        this.instructions = instructions;
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
        File file = new File(dir, FILE_NAME);
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
        if(instructions != null) {
            stringBuilder.append(instructions);
        }
        //writeAll
        fileWriter.write(stringBuilder.toString());
        //endWrite
        fileWriter.flush();
        fileWriter.close();
    }
}
