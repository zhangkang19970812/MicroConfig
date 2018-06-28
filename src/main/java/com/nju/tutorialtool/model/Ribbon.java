package com.nju.tutorialtool.model;

import com.nju.tutorialtool.model.dto.RibbonDTO;

import java.util.List;

public class Ribbon {
    private List<RibbonDTO> ribbonDTOList;
    private List<ServiceInfo> serviceInfoList;


    public Ribbon(List<RibbonDTO> ribbonDTOList, List<ServiceInfo> serviceInfoList) {
        this.ribbonDTOList = ribbonDTOList;
        this.serviceInfoList = serviceInfoList;
    }

    public List<RibbonDTO> getRibbonDTOList() {
        return ribbonDTOList;
    }

    public void setRibbonDTOList(List<RibbonDTO> ribbonDTOList) {
        this.ribbonDTOList = ribbonDTOList;
    }

    public List<ServiceInfo> getServiceInfoList() {
        return serviceInfoList;
    }

    public void setServiceInfoList(List<ServiceInfo> serviceInfoList) {
        this.serviceInfoList = serviceInfoList;
    }
}
