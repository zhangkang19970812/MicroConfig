package com.nju.tutorialtool.controller;

import com.nju.tutorialtool.model.PreviewInfo;
import com.nju.tutorialtool.model.ServiceInfo;
import com.nju.tutorialtool.model.SpringCloudInfo;
import com.nju.tutorialtool.model.dto.RibbonDTO;
import com.nju.tutorialtool.service.PreviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
     * 参数springCloudInfo包含groupId，artifactId
     * serviceInfoList包含所有服务的服务名称和文件夹名称
     * @param springCloudInfo
     * @param serviceInfoList
     * @return
     */
    @PostMapping("/eureka")
    public List<PreviewInfo> getEurekaInfo(@RequestBody SpringCloudInfo springCloudInfo, @RequestBody List<ServiceInfo> serviceInfoList) {
        return previewService.getEurekaInfo(springCloudInfo, serviceInfoList);
    }

    @PostMapping("/zuul")
    public List<PreviewInfo> getZuulInfo(@RequestBody SpringCloudInfo springCloudInfo) {
        return previewService.getZuulInfo(springCloudInfo);
    }

    /**
     * 参数ribbonDTOList是一个包含一个消费者，多个提供者的列表（因为微服务系统中会有多个消费者）
     * @param ribbonDTOList
     * @param serviceInfoList
     * @return
     */
    @PostMapping("/ribbon")
    public List<PreviewInfo> getRibbonInfo(List<RibbonDTO> ribbonDTOList, List<ServiceInfo> serviceInfoList) {
        return previewService.getRibbonInfo(ribbonDTOList, serviceInfoList);
    }
}
