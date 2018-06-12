package com.nju.tutorialtool.controller;

import com.nju.tutorialtool.service.GitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/git")
public class GitController {
    @Autowired
    private GitService gitService;

    @PostMapping("/clone")
    public boolean cloneToLocal(String githubUrl) {
        gitService.cloneToLocal(githubUrl);
        return true;
    }

    @PostMapping("/push")
    public boolean pushToGithub(String username, String password) throws Exception{
        gitService.pushToGithub(username, password);
        return true;
    }
}
