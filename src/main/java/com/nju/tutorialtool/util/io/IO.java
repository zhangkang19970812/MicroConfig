package com.nju.tutorialtool.util.io;

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
                System.out.println(str);
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
     * @param pattern
     * @return
     */
    public static File getFile(String path, String pattern) {
        File file = new File(path);
        File[] files1 = file.listFiles();
        if (files1 == null) {
            return file;
        }
        for (File f : files1) {
            if (f.isFile() && Pattern.matches(pattern, f.getName())) {
                return f;
            }
        }
        return file;
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


//    public static void main(String[] args) {
//        File file = new File("H:/programs/web/tutorial-tool/src/main/resources/application.properties");
//        String s = readFromFile(file);
//        System.out.println(s);
////        for (String c : str) {
////            System.out.println(c);
////        }
//    }
}
