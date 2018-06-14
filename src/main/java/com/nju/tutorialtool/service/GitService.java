package com.nju.tutorialtool.service;

import com.nju.tutorialtool.util.git.GitUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
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
}
