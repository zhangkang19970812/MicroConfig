package com.nju.tutorialtool.template.dockerfile;

import com.nju.tutorialtool.base.DockerFile;

public class ServiceDockerfile extends DockerFile {
     public ServiceDockerfile(String fileDir, String port) {
        super(fileDir);
        String projectName = getProjectName(fileDir);
        String instructions = "FROM ewolff/docker-java\r\n" +
                "COPY target/msconfig.jar .\r\n" +
                "COPY target/config/application.properties /config/application.properties\r\n" +
                "RUN sh -c 'touch /msconfig.jar'\r\n" +
                "ENV JAVA_OPTS=\"\"\r\n" +
                "ENTRYPOINT [ \"sh\", \"-c\", \"java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /msconfig.jar\" ]\r\n" +
                "EXPOSE " + port;
        init(instructions);
    }

    private static String getProjectName(String fileDir) {
        return fileDir.substring(fileDir.lastIndexOf("/") + 1);
    }
}
