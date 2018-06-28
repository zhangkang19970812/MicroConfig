package com.nju.tutorialtool.service;

import com.nju.tutorialtool.util.io.IO;
import com.nju.tutorialtool.util.jar.Jar;
import org.springframework.scheduling.support.SimpleTriggerContext;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

@Service
public class GenerateJarService {
    private static String build = "<finalName>msconfig</finalName>\n" +
            "\t\t<sourceDirectory>src/main/java</sourceDirectory>\n" +
            "\t\t<resources>\n" +
            "\t\t\t<!-- 控制资源文件的拷贝 -->\n" +
            "\t\t\t<resource>\n" +
            "\t\t\t\t<directory>src/main/resources</directory>\n" +
            "\t\t\t\t<includes>\n" +
            "\t\t\t\t\t<include>**/*.properties</include>\n" +
            "\t\t\t\t\t<include>**/*.xml</include>\n" +
            "\t\t\t\t\t<include>**/*.tld</include>\n" +
            "\t\t\t\t</includes>\n" +
            "\t\t\t\t<filtering>true</filtering>\n" +
            "\n" +
            "\t\t\t\t<!-- 配置文件外置（在打包时取消注释）\n" +
            "                    资源文件拷贝到编译输出路径target的子文件夹config目录下，最终结构如下:\n" +
            "                    target\n" +
            "                       ...config\n" +
            "                           ...spring\n" +
            "                           ...mapper\n" +
            "                           ...env\n" +
            "                       ...lib\n" +
            "                       ...generateImportCarSql.jar\n" +
            "                 -->\n" +
            "\t\t\t\t<targetPath>${project.build.directory}/config</targetPath>\n" +
            "\t\t\t</resource>\n" +
            "\t\t</resources>\n";

    public void generateJar(String projectPath) throws IOException {
        addPre(projectPath);
        Jar.execCMD(projectPath);
    }

    public void addPre(String projectPath) throws IOException {
        File file = new File(projectPath + "/pom.xml");
        RandomAccessFile raf=new RandomAccessFile(file,"rw");
        String line=null;
        boolean findBuild = false, findDependencies = false;
        long buildPointer = 0, dependenciesPointer = 0;
        while((line=raf.readLine())!=null){
            if (line.contains("</dependencies>") && !findDependencies) {
                dependenciesPointer = raf.getFilePointer();
                findDependencies = true;
            }
            if (line.contains("<build>")) {
                buildPointer = raf.getFilePointer();
                findBuild = true;
                break;
            }
        }
        if (!findBuild) {
            String addbuild = "\n    <build>\n" + build + "    </build>";
            IO.insert(dependenciesPointer, addbuild, file);
        }
        else {
            IO.insert(buildPointer, build, file);
        }
    }

    public String getServiceName(String projectPath) {
        return projectPath.substring(projectPath.lastIndexOf("/") + 1);
    }
}
