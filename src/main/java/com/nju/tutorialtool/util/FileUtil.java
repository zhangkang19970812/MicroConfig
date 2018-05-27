package com.nju.tutorialtool.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 上传文件夹
 *
 * @author Shenmiu
 */
public class FileUtil {

    /**
     * 在basePath下保存上传的文件夹
     *
     * @param basePath 根目录
     * @param files    所有微服务业务代码文件
     */
    public static void saveMultiFile(String basePath, MultipartFile[] files) {

        Path baseDir = Paths.get(basePath);
        System.out.println(baseDir.toAbsolutePath().toString());

        if (files == null || files.length == 0) {
            return;
        }
        if (basePath.endsWith(File.separator)) {
            basePath = basePath.substring(0, basePath.length() - 1);
        }
        for (MultipartFile file : files) {
            System.out.println(file.getOriginalFilename());
            String filePath = basePath + File.separator + file.getOriginalFilename();
            Paths.get(basePath);

            makeDir(filePath);
            try {
                byte[] bytes = file.getBytes();
                Path path = Paths.get(filePath);

                //文件写入指定路径
                Files.write(path, bytes);
            } catch (IllegalStateException | IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 确保文件的上级目录存在，不存在则创建
     *
     * @param filePath 要保存的文件全名，包含路径
     */
    private static void makeDir(String filePath) {
        if (filePath.lastIndexOf(File.separator) > 0) {
            String dirPath = filePath.substring(0, filePath.lastIndexOf('/'));
            File dir = new File(dirPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
        }
    }
}