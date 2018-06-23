package com.nju.tutorialtool;

import com.nju.tutorialtool.service.HystrixService.AddHystrixService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author YZ
 * @Date 23/06/2018
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class HystrixTest {
    @Autowired
    private AddHystrixService addHystrixService;

    @Test
    public void testMethodNames(){
        addHystrixService.getMethodNames("/Users/YZ/Desktop/bff_service");
    }
}
