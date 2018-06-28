package com.nju.tutorialtool.service.HystrixService;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author YZ
 * @Date 2018/5/13
 */
@Service
public class AddHystrixService {
    AddMethods addMethods=new AddMethods();
    public void add(String url,Map<String,List<String>> methods){
        try {
            addMethods.addMethods(url, methods);
        } catch (IOException e) {
            System.out.println("文件不存在～");
        }
    }

    public Map<String,List<String>> getMethodNames(String url) {
        Map<String,List<String>> result=new HashMap<>();
        try {
            result = addMethods.getMethodNames(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
