package com.nju.tutorialtool.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;

/**
 * @Author YZ
 * @Date 2018/5/12
 */
public class ReturnType {
    //这里需要寻找VO文件
    public static String getFallbackReturns(String type) {
        String code="return new "+type+"(";
        RandomAccessFile raf= null;

        for(File file: FindControllers.vos){
            if((file.getName().substring(0,file.getName().indexOf('.'))).equals(type)){
                try {
                    raf=new RandomAccessFile(file,"r");
                } catch (FileNotFoundException e) {
                    return "return null;";
                }
                break;
            }
        }
        if(raf==null){
            return "return null;";
        }
        String line=null;
        try {
            while((line=raf.readLine())!=null){
                //找到方法所在的行数及代码
                if(line.contains("public "+type+"(")){
                    List<String> s= AddMethods.splitMethodLine(line);
                    for(String tmp:s){
                        if(tmp.equals("String")){
                            code+="\"默认字符串\",";
                        }else if(tmp.equals("int") || tmp.equals("long")){
                            code+="-1,";
                        }else if(tmp.equals("double")){
                            code+="-1.0,";
                        }else if(tmp.equals("char")){
                            code+="默认字符,";
                        }else if(tmp.equals("boolean")){
                            code+="false,";
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(code.charAt(code.length()-1)!=','){
            //可能是（，也就是说参数都不是基本类型；或者压根没找到构造方法
            code="return null;";
        }else {
            code = code.substring(0, code.length() - 1);
            code+=");\n";
        }

        return code;
    }
    public static void main(String[]args) throws IOException {
        ReturnType rt=new ReturnType();
        System.out.print(rt.getFallbackReturns("ResultVO"));
    }
}
