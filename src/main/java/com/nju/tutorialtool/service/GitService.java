package com.nju.tutorialtool.service;

import com.nju.tutorialtool.model.ServiceInfo;
import com.nju.tutorialtool.util.git.GitUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class GitService {
    @Autowired
    private UserService userService;

    public void cloneToLocal(String githubUrl) {
        userService.addUser();
        GitUtil.clone(githubUrl, userService.getUserFolder());
    }

    public void pushToGithub(String username, String password) throws Exception {
        GitUtil.commitAndPush(userService.getUserFolder() + File.separator + ".git", username, password);
    }

    public List<ServiceInfo> getAllService() {
        List<ServiceInfo> resList = new ArrayList<>();
        File dir = new File(userService.getUserFolder());
        File[] list = dir.listFiles();
        if (list.length == 0) {
            return resList;
        }
        else {
            for (File file : list) {
                resList.add(new ServiceInfo(file.getName(), file.getName()));
            }
        }
        return resList;
    }
}
