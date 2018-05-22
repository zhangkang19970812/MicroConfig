package com.nju.tutorialtool.service;

import com.nju.tutorialtool.util.jar.Jar;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class GenerateJarService {
    public void generateJar(String projectPath) {
        File file = new File(projectPath);
        Jar.execCMD(file);
    }

}
