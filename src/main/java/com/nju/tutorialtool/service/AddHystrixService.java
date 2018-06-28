package com.nju.tutorialtool.service;

import com.nju.tutorialtool.model.HystrixMethod;
import com.nju.tutorialtool.model.ServiceInfoList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author YZ
 * @Date 2018/5/13
 */
@Service
public class AddHystrixService {
    @Autowired
    private UserService userService;
    AddMethods addMethods=new AddMethods();
    public void add(String url,Map<String,List<String>> methods){
        try {
            addMethods.addMethods(url, methods);
        } catch (IOException e) {
            System.out.println("文件不存在～");
        }
    }

    public List<HystrixMethod> getMethodNames(ServiceInfoList serviceInfoList) {
        List<HystrixMethod> result=new ArrayList<>();
        try {
            System.out.println(userService.getUserFolder());
            result = addMethods.getMethodNames(serviceInfoList,userService.getUserFolder());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
