package com.nju.tutorialtool.controller;

import com.nju.tutorialtool.service.GitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/git")
public class GitController {
    @Autowired
    private GitService gitService;

    /**
     * 从github clone到本地
     * @param githubUrl github仓库地址
     * @return
     */
    @PostMapping("/clone")
    public boolean cloneToLocal(String githubUrl) {
        gitService.cloneToLocal(githubUrl);
        return true;
    }

    /**
     * 从本地push到GitHub仓库
     * @param username
     * @param password
     * @return
     * @throws Exception
     */
    @PostMapping("/push")
    public boolean pushToGithub(String username, String password) throws Exception{
        gitService.pushToGithub(username, password);
        return true;
    }
}
