package com.nju.tutorialtool.controller;

import com.nju.tutorialtool.model.ServiceInfo;
import com.nju.tutorialtool.service.GitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/git")
public class GitController {
    @Autowired
    private GitService gitService;

    /**
     * 从github clone到本地
     *
     * @param gitPath github仓库地址
     * @return
     */
    @PostMapping("/clone")
    public List<ServiceInfo> cloneToLocal(@RequestParam("gitPath") String gitPath) {
        System.out.println(gitPath);
        gitService.cloneToLocal(gitPath);
        return gitService.getAllService();
    }

    /**
     * 从本地push到GitHub仓库
     *
     * @param username
     * @param password
     * @return
     * @throws Exception
     */
    @PostMapping("/push")
    public boolean pushToGithub(@RequestParam("username") String username, @RequestParam("password") String password) throws Exception {
        gitService.pushToGithub(username, password);
        return true;
    }
}
