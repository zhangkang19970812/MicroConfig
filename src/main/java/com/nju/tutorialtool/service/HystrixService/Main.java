package com.nju.tutorialtool.service.HystrixService;

import java.io.IOException;

/**
 * @Author YZ
 * @Date 2018/5/12
 */
public class Main {
    public static void main(String[]args){
        AddMethods ad=new AddMethods();
        try {
            ad.addMethods("/Users/YZ/Desktop/微服务/PetStore-/order_service");
        } catch (IOException e) {
            System.out.print("文件不存在！");
        }
    }
}
