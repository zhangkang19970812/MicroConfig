package com.nju.tutorialtool.model;

import com.nju.tutorialtool.model.dto.RibbonDTO;

import java.util.List;

public class Ribbon {
    private List<RibbonDTO> ribbonDTOList;

    public Ribbon(List<RibbonDTO> ribbonDTOList) {
        this.ribbonDTOList = ribbonDTOList;
    }

    public List<RibbonDTO> getRibbonDTOList() {
        return ribbonDTOList;
    }

    public void setRibbonDTOList(List<RibbonDTO> ribbonDTOList) {
        this.ribbonDTOList = ribbonDTOList;
    }
}
