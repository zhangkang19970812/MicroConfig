package com.nju.tutorialtool.controller;

import com.alibaba.fastjson.JSON;
import com.nju.tutorialtool.model.*;
import com.nju.tutorialtool.model.dto.RibbonDTO;
import com.nju.tutorialtool.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author Shenmiu
 * @date 2018/05/20
 */
@RestController
@RequestMapping(path = "/test")
public class TestController {

    private static final String UPLOAD_FOLDER = "upload";
    private Logger logger = LoggerFactory.getLogger(TestController.class);

    @PostMapping(value = "/general")
    public General testGeneral(@RequestBody General general) {
        System.out.println();
        System.out.println();
        return general;
    }

    @PostMapping(value = "/services")
    public boolean testGeneral(@RequestBody List<ServiceInfo> serviceInfoList) {
        System.out.println();
        System.out.println();
        return true;
    }

    @PostMapping(value = "/eureka")
    public boolean testEureka(@RequestBody SpringCloudInfo eurekaServerInfo) {
        System.out.println();
        System.out.println();
        return true;
    }

    @PostMapping(value = "/ribbon")
    public boolean testZuul(@RequestBody RibbonDTO ribbon) {
        System.out.println();
        System.out.println();
        return true;
    }

    @PostMapping(value = "/zuul")
    public boolean testZuul(@RequestBody SpringCloudInfo zuul) {
        System.out.println();
        System.out.println();
        return true;
    }

    @PostMapping(value = "/compose")
    public boolean testComposeInfo(@RequestBody ComposeInfo composeInfo) {
        System.out.println();
        System.out.println();
        return true;
    }

    @PostMapping(value = "/server")
    public boolean testServer(@RequestBody DeployServer deployServer) {
        System.out.println();
        System.out.println();
        return true;
    }


    @PostMapping(value = "/uploadFolder")
    public String uploadFolder(MultipartFile[] folder) {
        logger.info("传入的文件参数：{}", JSON.toJSONString(folder, true));
        FileUtil.saveMultiFile(UPLOAD_FOLDER, folder);
        return "ok";
    }
}
