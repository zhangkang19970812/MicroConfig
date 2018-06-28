package com.nju.tutorialtool.controller;

import com.nju.tutorialtool.model.*;
import com.nju.tutorialtool.model.dto.RibbonDTO;
import com.nju.tutorialtool.service.PreviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 方法都是返回一个PreviewInfo列表
 * PreviewInfo包括服务名称，文件名称，文件内容，需要高亮的行数列表
 */
@RestController
@RequestMapping("/preview")
public class PreviewController {
    @Autowired
    private PreviewService previewService;

    /**
     * 生成eureka Server的overview
     * @param springCloudInfo
     * @return
     */
    @PostMapping("/eurekaServer")
    public PreviewInfo getEurekaServerInfo(@RequestBody SpringCloudInfo springCloudInfo) {
        return previewService.getEurekaServerInfo(springCloudInfo);
    }

    /**
     * serviceInfoList包含所有服务的服务名称和文件夹名称
     * @param serviceInfoList
     * @return
     */
    @PostMapping("/eurekaClient")
    public List<PreviewInfo> getEurekaClientInfo(@RequestBody ServiceInfoList serviceInfoList) {
        return previewService.getEurekaClientInfo(serviceInfoList.getServiceInfoList());
    }

    @PostMapping("/zuul")
    public PreviewInfo getZuulInfo(@RequestBody SpringCloudInfo springCloudInfo) {
        return previewService.getZuulInfo(springCloudInfo);
    }

    /**
     * 参数ribbont是包含多个消费者，每个消费者又有多个提供者的列表（因为微服务系统中会有多个消费者），还包括一个ServiceInfo列表
     * @param ribbon
     * @return
     */
    @PostMapping("/ribbon")
    public List<PreviewInfo> getRibbonInfo(@RequestBody Ribbon ribbon) {
        return previewService.getRibbonInfo(ribbon.getRibbonDTOList(), ribbon.getServiceInfoList());
    }

    @PostMapping("/hystrix")
    public List<PreviewInfo> getHystrixInfo(@RequestBody PreviewHystrix previewHystrix){
        return previewService.getHystrixInfo(previewHystrix.getServiceInfoList(),previewHystrix.getMethodsMap());
    }

}
