package com.nju.tutorialtool.service.HystrixService;

import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @Author YZ
 * @Date 2018/5/13
 */
@Service
public class AddHystrixService {
    public void add(String url){
        AddMethods addMethods=new AddMethods();
        try {
            addMethods.addMethods(url);
        } catch (IOException e) {
            System.out.println("文件不存在～");
        }
    }
}
