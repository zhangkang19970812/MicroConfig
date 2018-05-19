package com.nju.tutorialtool.controller;

import com.nju.tutorialtool.model.Zuul;
import com.nju.tutorialtool.service.ZuulService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/zuul")
public class ZuulController {

    @Autowired
    private ZuulService zuulService;

    /**
     * 替换某服务消费者所有java文件中出现的api中localhost:port部分
     * @param zuul 包含一个服务消费者路径，还有一个服务提供者路径列表
     */
    @RequestMapping(value = "/modifyUrl", method = RequestMethod.POST)
    public boolean modifyUrl(@RequestBody Zuul zuul) {
        zuulService.replaceUrl(zuul.getConsumerPath(), zuul.getProviderPath());
        return true;
    }
}
