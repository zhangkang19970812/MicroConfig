package com.nju.tutorialtool.service;

import com.nju.tutorialtool.util.io.IO;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

@Service
public class RibbonService {
    /**
     * 添加Ribbon注解
     * @param projectPath
     * @throws IOException
     */
    public void addRibbon(String projectPath) throws IOException {
        File applicationFile = IO.getApplication(projectPath);
        RandomAccessFile raf=new RandomAccessFile(applicationFile,"rw");
        String line=null;
        while((line=raf.readLine())!=null){
            if(line.contains("@Bean")){
                long pointer=raf.getFilePointer();
                String annotation="@LoadBalanced\n";
                IO.insert(pointer,annotation,applicationFile);
            }
        }
    }
}
