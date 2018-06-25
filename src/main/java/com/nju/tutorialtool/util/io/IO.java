package com.nju.tutorialtool.util.io;

import com.nju.tutorialtool.util.DependencyConstant;
import com.nju.tutorialtool.util.FileUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author zk
 */
public class IO {
    /**
     * 替换文件中的指定字符串
     */
    public static boolean replaceFileStr(File file, String sourceStr, String targetStr) {
        try {
            FileReader fis = new FileReader(file);  // 创建文件输入流
            BufferedReader br = new BufferedReader(fis);
            char[] data = new char[1024];               // 创建缓冲字符数组
            int rn = 0;
            StringBuilder sb = new StringBuilder();       // 创建字符串构建器
            //fis.read(data)：将字符读入数组。在某个输入可用、发生 I/O 错误或者已到达流的末尾前，此方法一直阻塞。读取的字符数，如果已到达流的末尾，则返回 -1
            while ((rn = br.read(data)) > 0) {         // 读取文件内容到字符串构建器
                String str = String.valueOf(data, 0, rn);//把数组转换成字符串
//                System.out.println(str);
                sb.append(str);
            }
            br.close();// 关闭输入流
            // 从构建器中生成字符串，并替换搜索文本
            String str = sb.toString().replace(sourceStr, targetStr);
            FileWriter fout = new FileWriter(file);// 创建文件输出流
            fout.write(str.toCharArray());// 把替换完成的字符串写入文件内
            fout.close();// 关闭输出流

            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * 向指定位置插入字符串
     */
    public static void insert(long pointer, String str, File fileName) {
        try {
            RandomAccessFile raf = new RandomAccessFile(fileName, "rw");
            byte[] b = str.getBytes();
            raf.setLength(raf.length() + b.length);
            for (long i = raf.length() - 1; i > b.length + pointer - 1; i--) {
                raf.seek(i - b.length);
                byte temp = raf.readByte();
                raf.seek(i);
                raf.writeByte(temp);
            }
            raf.seek(pointer);
            raf.write(b);
            raf.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 将一行字符串追加至文件末尾
     *
     * @param file
     * @param str
     */
    public static void insertToEnd(File file, String str) {
        try {
            FileWriter fw = new FileWriter(file, true);
            PrintWriter pw = new PrintWriter(fw);
            pw.println();
            pw.println(str);   //字符串末尾不需要换行符
            pw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 得到某目录下的所有java文件
     *
     * @param path
     * @return List<File>
     */
    public static List<File> getAllFiles(String path) {
        List<File> files = new ArrayList<>();
        File file = new File(path);
        File[] files1 = file.listFiles();
        if (files1 == null) {
            return files;
        }
        for (File f : files1) {
            if (f.isFile() && Pattern.matches(".*java", f.getName())) {
                files.add(f);
            } else if (f.isDirectory()) {
                files.addAll(getAllFiles(f.getAbsolutePath()));
            }
        }
        return files;
    }

    /**
     * 精确得到某个文件夹下的某个文件
     *
     * @param path
     * @param fileName
     * @return
     */
    public static File getFile(String path, String fileName) {
        File file = new File(path);
        File[] files1 = file.listFiles();
        if (files1 == null) {
            return file;
        }
        for (File f : files1) {
            if (f.isFile() && f.getName().equals(fileName)) {
                return f;
            }
        }
        return file;
    }

    /**
     * 获取某项目下的某文件
     * @param path
     * @param fileName
     * @return
     */
    public static File getFileInProject(String path, String fileName) {
        for (File file : getAllFiles(path)) {
            if (file.getName().equals(fileName)) {
                return file;
            }
        }
        return null;
    }

    /**
     * 从文件读出内容
     *
     * @param file
     * @return String
     */
    public static String readFromFile(File file) {
        if (!file.exists()) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String str = "";
            while ((str = bufferedReader.readLine()) != null) {
                stringBuilder.append(str + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    /**
     * 得到一个项目的Application启动类
     *
     * @param projectPath
     * @return
     */
    public static File getApplication(String projectPath) {
        for (File file : getAllFiles(projectPath + "/src/main/java")) {
            if (Pattern.matches(".*Application\\.java", file.getName())) {
                return file;
            }
        }
        return null;
    }

    /**
     * 向业务服务的pom文件中添加依赖
     * @param projectPath 服务路径
     * @param dependencyList 依赖名称列表
     */
    public static void addDependencyToPom(String projectPath, List<String> dependencyList) {
        File file = getFile(projectPath, "pom.xml");
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(file, "rw");
            String line = null;
            while ((line = raf.readLine()) != null) {
                if (line.contains("</dependency>")) {
                    long pointer = raf.getFilePointer();
                    String annotation = "\n"+ DependencyConstant.getDependencies(dependencyList);
                    IO.insert(pointer, annotation, file);
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 得到某项目的配置文件中的定义的port
     * @param projectPath
     * @return
     */
    public static String getServicePort(String projectPath) {
        File file = IO.getFile(projectPath + "/src/main/resources", "application.properties");
        String[] str = IO.readFromFile(file).split("\n");
        String port = "";
        if(file.getName().contains("properties")) {
            for (String s : str) {
                s = deleteSpaces(s);
                if (s.contains("server.port")) {
                    port = s.substring(s.indexOf("=") + 1);
                    break;
                }
            }
        }
        else {
            int sret = 0;
            for (String s : str) {
                if (s.equals("server:")) {
                    sret = 1;
                }
                if (s.equals("name:") && sret == 1) {
                    s = deleteSpaces(s);
                    port = s.substring(s.indexOf(":") + 1);
                    break;
                }
            }
        }
        return port;
    }

    public static void copyfile(String from,String to) throws IOException{
        BufferedReader in=new BufferedReader(new FileReader(from));
        BufferedWriter out=new BufferedWriter(new FileWriter(to));
        String line=null;
        int linenumber=0;
        while((line=in.readLine())!=null){
            out.write(line+"\n");
            linenumber++;
        }
        in.close();
        out.close();

        System.out.println("line number "+linenumber);
    }

    public static void copyfile(File from,String to) throws IOException{
        BufferedReader in=new BufferedReader(new FileReader(from));
        BufferedWriter out=new BufferedWriter(new FileWriter(to));
        String line=null;
        int linenumber=0;
        while((line=in.readLine())!=null){
            out.write(line+"\n");
            linenumber++;
        }
        in.close();
        out.close();

        System.out.println("line number "+linenumber);
    }

    /**
     * 通过Entity注解获取实体类
     * @param projectPath
     * @return
     */
    public static List<File> getEntityClass(String projectPath) {
        List<File> resList = new ArrayList<>();
        List<File> list = getAllFiles(projectPath + "/src/main/java/");
        for (File file : list) {
            String content = readFromFile(file);
            if (content.contains("@Entity")) {
                resList.add(file);
            }
        }
        return resList;
    }

    public static String deleteSpaces(String s) {
        return s.replace(" ", "");
    }

    public static void main(String[] args) throws IOException {
        File file = new File("C:\\Users\\zk\\Desktop\\MSConfig\\pom.xml");
        File newfile = new File("C:\\Users\\zk\\Desktop\\pom.xml");
        System.out.println(IO.getFileInProject("C:\\Users\\zk\\Desktop\\MSConfig", "CORSConfig.java").getName());
    }
}
