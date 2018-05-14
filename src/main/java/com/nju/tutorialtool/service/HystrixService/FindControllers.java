package com.nju.tutorialtool.service.HystrixService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @Author YZ
 * @Date 2018/5/11
 */
public class FindControllers {
    static List<File> applications=new ArrayList<>();
    /**
     * 因为递归方法难以返回数据
     */
    static List<File> controllerFiles=new ArrayList<>();
    /**
     * 用户选择待搜索的文件目录
     * @param url
     * @return
     */
    public List<File> getAllControllers(String url){
        List<File> files=new ArrayList<>();
        getAllFiles(url);
        String pattern=".*Controller\\.java";
        String apattern=".*Application\\.java";
        for(File f:controllerFiles){
            if(Pattern.matches(pattern,f.getName())){
                files.add(f);
            }else if(Pattern.matches(apattern,f.getName())){
                applications.add(f);
            }
        }
//        for(File f1:applications){
//            System.out.println(f1.getName());
//        }
        return files;
    }

    /**
     * 已知目录，得到下面所有文件名.java
     * @param url
     * @return
     */
    public static List<File> getAllFiles(String url){
        List<File> files=new ArrayList<>();
        File file=new File(url);
        File[] files1=file.listFiles();
        if(files1==null){
            return files;
        }
        for(File f:files1){
            if(f.isFile() && Pattern.matches(".*java",f.getName())){
                files.add(f);
                controllerFiles.add(f);
            }else if(f.isDirectory()){
                getAllFiles(f.getAbsolutePath());
            }
        }
        return files;
    }
    public static void main(String[]args){
        FindControllers fd=new FindControllers();
        fd.getAllControllers("/Users/YZ/Desktop/inventory_service");
    }

}
