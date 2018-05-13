package com.nju.tutorialtool.template.mysql;

import java.io.File;
import java.io.FileWriter;

public class SetupShFile {
    private String fileDir;
    private static final String FILE_NAME = "setup.sh";
    private static final String shell = "#!/bin/bash\r\n" +
            "set -e\r\n" +
            "\r\n" +
            "#查看mysql服务的状态，方便调试，这条语句可以删除\r\n" +
            "echo `service mysql status`\r\n" +
            "\r\n" +
            "echo '1.启动mysql....'\r\n" +
            "#启动mysql\r\n" +
            "service mysql start\r\n" +
            "sleep 3\r\n" +
            "echo `service mysql status`\r\n" +
            "\r\n" +
            "echo '2.开始导入数据....'\r\n" +
            "#导入数据\r\n" +
            "mysql < /mysql/schema.sql\r\n" +
            "echo '3.导入数据完毕....'\r\n" +
            "\r\n" +
            "sleep 3\r\n" +
            "echo `service mysql status`\r\n" +
            "\r\n" +
            "#重新设置mysql密码\r\n" +
            "echo '4.开始修改密码....'\r\n" +
            "mysql < /mysql/privileges.sql\r\n" +
            "echo '5.修改密码完毕....'\r\n" +
            "\r\n" +
            "#sleep 3\r\n" +
            "echo `service mysql status`\r\n" +
            "echo `mysql容器启动完毕,且数据导入成功`\r\n" +
            "\r\n" +
            "tail -f /dev/null";

    public SetupShFile(String fileDir) {
        this.fileDir = fileDir;
    }

    private File makeSqlDir() throws Exception{
        File dir = new File(fileDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }
    private File makeSqlFile() throws Exception{
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
        stringBuilder.append(shell);

        //writeAll
        fileWriter.write(stringBuilder.toString());
        //endWrite
        fileWriter.flush();
        fileWriter.close();
    }
}
