package com.nju.tutorialtool.service.HystrixService;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @Author YZ
 * @Date 2018/5/12
 */
public class ReturnType {
    public static String getFallbackReturns(String type) {
        String code="return new "+type+"(";
        RandomAccessFile raf= null;
        try {
            raf = new RandomAccessFile(type,"rw");
        } catch (FileNotFoundException e) {
            return "return null;";
        }
        String line=null;
        try {
            while((line=raf.readLine())!=null){
                //找到方法所在的行数及代码
                if(line.contains("public "+type+"(")){
                    List<String> s=AddMethods.splitMethodLine(line);
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
