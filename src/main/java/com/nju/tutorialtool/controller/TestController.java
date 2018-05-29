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

    private static final String UPLOAD_FOLDER = "/Users/harvey/test/test";

    @PostMapping(value = "/general")
    public General testGeneral(@RequestBody General general) {
        System.out.println();
        System.out.println();
        return general;
    }
}
