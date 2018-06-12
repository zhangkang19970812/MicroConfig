package com.nju.tutorialtool.service;

import com.nju.tutorialtool.model.ServiceInfo;
import com.nju.tutorialtool.util.enums.BaseDirConstant;
import com.nju.tutorialtool.util.git.GitUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public class GitService {
    @Autowired
    private ServiceDirMapService serviceDirMapService;

    public void cloneToLocal(String githubUrl) {
        String folderName = githubUrl.substring(githubUrl.lastIndexOf("/") + 1);
        GitUtil.clone(githubUrl, BaseDirConstant.projectBaseDir + File.separator + folderName);
    }

    public void pushToGithub(String username, String password) throws Exception {
        List<ServiceInfo> list = serviceDirMapService.getAllServices();
        for (ServiceInfo serviceInfo : list) {
            if (serviceInfo.getMysqlInfo() != null) {
//                GitUtil.commitAndPush(getProjectGitPath(serviceInfo.getMysqlInfo()), username, password);
            }
//            GitUtil.commitAndPush(getProjectGitPath(serviceInfo.getFolderName()), username, password);
        }
    }

    private String getProjectGitPath(String dirName) {
        return BaseDirConstant.projectBaseDir + File.separator + dirName + File.separator + ".git";
    }
}
