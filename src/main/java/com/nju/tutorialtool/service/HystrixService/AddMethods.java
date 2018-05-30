package com.nju.tutorialtool.service.HystrixService;

import com.nju.tutorialtool.util.io.IO;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @Author YZ
 * @Date 2018/5/11
 */
public class AddMethods {
    //针对某一个Controller进行改写
    FindControllers findControllers=new FindControllers();
    public void addMethods(String url) throws IOException {
        List<File> controllers=findControllers.getAllControllers(url);
        for(File f:controllers){
            addOnOneController(f);
        }
        //applications需要在getAllControllers的基础上获得；所以需要写在后面
        List<File> applications=findControllers.applications;
        for(File a:applications){
            modifyApplication(a);
        }
    }

    /**
     * 对项目的启动类添加断路器支持
     * @param applicationFile
     * @throws IOException
     */
    public void modifyApplication(File applicationFile) throws IOException {
        RandomAccessFile raf=new RandomAccessFile(applicationFile,"rw");
        String line=null;
        boolean findImportPointer = false;
        boolean findAnnotationPointer = false;
        long importPointer = 0;
        long annotationPointer = 0;
        while((line=raf.readLine())!=null && (!findImportPointer || !findAnnotationPointer)){
            if (line.contains("import org.") && !findImportPointer) {
                importPointer = raf.getFilePointer();
                findImportPointer = true;
            }
            if(line.contains("@SpringBootApplication") && !findAnnotationPointer){
                annotationPointer = raf.getFilePointer();
                findAnnotationPointer = true;
            }
        }

        String importPackage = "import org.springframework.cloud.netflix.hystrix.EnableHystrix;\n";
        IO.insert(importPointer, importPackage, applicationFile);
        String annotation="@EnableHystrix\n";
        insertAnnotation(annotationPointer,annotation,applicationFile);
    }
    /**
     * 对一个controller代码进行处理
     * @param controllerFile
     * @throws IOException
     */
    public void addOnOneController(File controllerFile) throws IOException {
        String fallback="";
        RandomAccessFile raf=new RandomAccessFile(controllerFile,"rw");
        String line=null;
        String lastLine=null;
        int signal=0;//表示该代码还未加过熔断
        boolean findImportPointer = false; //是否找到Import包
        while((line=raf.readLine())!=null){
            if (line.contains("import ") && !findImportPointer) {
                long pointer = raf.getFilePointer();
                String importPackage = "import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;\n";
                IO.insert(pointer, importPackage, controllerFile);
                findImportPointer = true;
            }
            lastLine=line;
            //找到方法所在的行数及代码
            if(Pattern.matches(".*RequestMethod.*",line)){
                long lastPointer=raf.getFilePointer();

                String methodLine=raf.readLine();
                if(!methodLine.contains("@HystrixCommand")) {
                    List<String> splits = splitMethodLine(methodLine);
                    String authority = splits.get(0);
                    String returnTye = splits.get(1);
                    String methodName = splits.get(2);
                    String parameter = getParameters(methodLine);
                    int spaceNum = Integer.parseInt(splits.get(splits.size() - 1));

                    //这里缩进减1，是因为raf的pointer已经有一位了
                    String annotation = getSpaces(spaceNum - 1) + "@HystrixCommand(fallbackMethod = \"" + methodName + "Fallback\")\n ";

                    insertAnnotation(getInsertPointer(lastPointer, methodLine), annotation, controllerFile);

                    fallback += "\n" + getSpaces(spaceNum) + authority + " " + returnTye + " " + methodName + "Fallback(" + parameter + "){\n" + getSpaces(spaceNum + 4) + ReturnType.getFallbackReturns(returnTye) + "\n" + getSpaces(spaceNum) + "}\n" + "\n";
                }else{
                    signal=1;
                }

            }
        }
//        raf.setLength(raf.length()+fallback.length());

        if(signal==0) {
            fallback += "}";
            raf.seek(raf.getFilePointer() - lastLine.length() * 2);
            raf.write(fallback.getBytes());
        }
        raf.close();
    }
    public static void main(String[]args) throws IOException {
        AddMethods ad=new AddMethods();
        ad.addOnOneController(new File("TestController"));
    }

    /**
     * 得到方法中的片段
     * @param methodLine
     * @return
     */
    public static List<String> splitMethodLine(String methodLine){
        List<String> result=new ArrayList<>();
        int space=0;
        for(int i=0;i<methodLine.length();i++){
            if(result.size()==0 && methodLine.charAt(i)==' '){
                space++;
            }
            if(methodLine.charAt(i)!=' '){
                String news=methodLine.charAt(i)+"";
                while(i<methodLine.length()-1 && methodLine.charAt(++i)!=' ' && methodLine.charAt(i)!='(' && methodLine.charAt(i)!=')'){
                    news+=methodLine.charAt(i);
                }
                result.add(news);
            }
        }
        result.add(space+"");
        return result;
    }
    public static String getParameters(String methodLine){
        String result=methodLine.substring(methodLine.indexOf('(')+1,methodLine.indexOf(')'));
        if(result.contains("@RequestBody")){
            result=result.replace("@RequestBody ","");
        }
        if(result.contains("@PathVariable")){
            result=result.replace("@PathVariable ","");
        }
//        System.out.print(result);
        return result;
    }

    /**
     * 缩进
     * @param num
     * @return
     */
    public static String getSpaces(int num){
        String s="";
        for(int i=0;i<num;i++){
            s+=" ";
        }
        return s;
    }

    /**
     * 获得注释添加的位置
     * @param currentPointer
     * @param methodLine
     * @return
     */
    public static long getInsertPointer(long currentPointer, String methodLine){
        long result=currentPointer-methodLine.indexOf("RequestMethod");
        System.out.println("计算后："+result);
        return result;
    }

    /**
     * 向指定位置插入字符串
     * @param pointer
     * @param str
     * @param fileName
     */
    public static void insertAnnotation(long pointer, String str, File fileName){
        try {
            RandomAccessFile raf = new RandomAccessFile(fileName,"rw");
            byte[] b = str.getBytes();
            raf.setLength(raf.length() + b.length);
            for(long i = raf.length() - 1; i > b.length + pointer - 1; i--){
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
}
