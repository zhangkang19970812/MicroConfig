package com.nju.tutorialtool.util;

import org.eclipse.jgit.api.Git;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class GitUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(GitUtil.class);

    public static boolean clone(String remoteUrl, String localDir) {
        boolean res = false;
        File file = new File(localDir);
        if (file.exists()) {
            deleteFolder(file);
        }
        try {
            Git git = Git.cloneRepository()
                    .setURI(remoteUrl)
                    .setDirectory(file)
                    .call();
            LOGGER.info("Cloning from " + remoteUrl + " to " + git.getRepository());
            res = true;
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
        }
        return res;
    }

    public static void commit(String localRepoGitConfig){
        File file = new File(localRepoGitConfig);
        if (!file.exists()) {
            return;
        }
        try {
            Git git = Git.open(file);
            //创建用户文件的过程
//            File myfile = new File(filePath);
//            myfile.createNewFile();
//            git.add().addFilepattern("pets").call();
            //提交
            git.commit().setMessage("").call();
            git.push().call();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void deleteFolder(File file){
        if(file.isFile() || file.list().length==0){
            file.delete();
        }else{
            File[] files = file.listFiles();
            for(int i=0;i<files.length;i++){
                deleteFolder(files[i]);
                files[i].delete();
            }
        }
    }
}
