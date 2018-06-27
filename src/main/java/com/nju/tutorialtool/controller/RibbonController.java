package com.nju.tutorialtool.controller;

import com.nju.tutorialtool.model.Ribbon;
import com.nju.tutorialtool.service.RibbonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/ribbon")
public class RibbonController {
    @Autowired
    private RibbonService ribbonService;

//    /**
//     * 替换某服务消费者所有java文件中出现的api中localhost:port部分
//     * @param ribbon 包含一个服务消费者路径，还有一个服务提供者路径列表
//     */
//    @RequestMapping(value = "/modifyUrl", method = RequestMethod.POST)
//    public boolean modifyUrl(@RequestBody Ribbon ribbon) {
//        ribbonService.replaceUrl(ribbon.getConsumerPath(), ribbon.getProviderPath());
//        return true;
//    }
//
//    /**
//     * 添加Ribbon注解
//     * @param projectPath
//     * @return
//     * @throws IOException
//     */
//    @RequestMapping(value = "/addRibbon", method = RequestMethod.POST)
//    public boolean addRibbon(String projectPath) throws IOException {
//        ribbonService.addRibbon(projectPath);
//        return true;
//    }
}
