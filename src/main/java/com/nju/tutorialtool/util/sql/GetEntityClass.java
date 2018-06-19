package com.nju.tutorialtool.util.sql;

import com.nju.tutorialtool.util.git.GitUtil;
import com.nju.tutorialtool.util.io.IO;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.tools.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GetEntityClass {
    public static List<Class> getEntity(String projectPath) throws Exception {
        List<Class> list = new ArrayList<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = factory.newDocumentBuilder();
        Document document = db.parse(new File(projectPath + "/src/main/resources/META-INF/persistence.xml"));

        NodeList node = document.getElementsByTagName("class");
        File dirFile = new File("./src/main/java/com/nju/tutorialtool/entity");
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }

        for(int i=0;i<node.getLength();i++){
            Element element = (Element)node.item(i);
            String content = element.getFirstChild().getNodeValue();
            String filePath = projectPath + "/src/main/java/" + replace(content) + ".java";
            IO.copyfile(filePath, "./src/main/java/com/nju/tutorialtool/entity/" + getClassName(content) + ".java");
            setPackage(content);
            compiler("./src/main/java/com/nju/tutorialtool/entity/" + getClassName(content) + ".java");
            list.add(Class.forName("com.nju.tutorialtool.entity." + getClassName(content)));
        }
        GitUtil.deleteFolder(new File("./src/main/java/com/nju/tutorialtool/entity/"));
        GitUtil.deleteFolder(new File("./target/classes/com/nju/tutorialtool/entity/"));
        return list;
    }

    public static String replace(String str) {
        return str.replaceAll("\\.", "/");
    }

    public static String getClassName(String content) {
        return content.substring(content.lastIndexOf(".") + 1);
    }

    public static void setPackage(String content) {
        File file = new File("./src/main/java/com/nju/tutorialtool/entity/" + getClassName(content) + ".java");
        String[] classContents = IO.readFromFile(file).split("\n");
        for (String s : classContents) {
            if (s.startsWith("package ")) {
                IO.replaceFileStr(file, s, "package com.nju.tutorialtool.entity;");
                break;
            }
        }
    }

    public static void compiler(String javaFile) throws IOException {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        // 建立DiagnosticCollector对象

        DiagnosticCollector diagnostics = new DiagnosticCollector();

        StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null);

        // 建立源文件对象，每个文件被保存在一个从JavaFileObject继承的类中
        Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromStrings(Arrays
                .asList(javaFile));

        // options命令行选项
        Iterable<String> options = Arrays.asList("-d",
                "./target/classes");// 指定的路径一定要存在，javac不会自己创建文件夹
        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnostics, options, null,
                compilationUnits);

        // 编译源程序
        boolean success = task.call();
        fileManager.close();
        System.out.println((success) ? "编译成功" : "编译失败");

        // 打印信息
        for (Object object : diagnostics.getDiagnostics()) {
            Diagnostic diagnostic = (Diagnostic) object;
            System.out.printf("Code: %s%n" + "Kind: %s%n" + "Position: %s%n" + "Start Position: %s%n"
                            + "End Position: %s%n" + "Source: %s%n" + "Message: %s%n", diagnostic.getCode(),
                    diagnostic.getKind(), diagnostic.getPosition(), diagnostic.getStartPosition(),
                    diagnostic.getEndPosition(), diagnostic.getSource(), diagnostic.getMessage(null));
        }
    }

//    public static void main(String[] args) throws Exception {
//
//    }
}
