package com.nju.tutorialtool.template.dockerfile;

import com.nju.tutorialtool.base.DockerFile;

public class MysqlDockerfile extends DockerFile {
    public MysqlDockerfile(String fileDir) {
        super(fileDir);
        String instructions = "FROM mysql\r\n" +
                "\r\n" +
                "#设置免密登录\r\n" +
                "ENV MYSQL_ALLOW_EMPTY_PASSWORD yes\r\n" +
                "\r\n" +
                "#将所需文件放到容器中\r\n" +
                "COPY setup.sh /mysql/setup.sh\r\n" +
                "COPY schema.sql /mysql/schema.sql\r\n" +
                "COPY privileges.sql /mysql/privileges.sql\r\n" +
                "\r\n" +
                "#设置容器启动时执行的命令\r\n" +
                "CMD [\"sh\", \"/mysql/setup.sh\"]";
        init(instructions);
    }
}
