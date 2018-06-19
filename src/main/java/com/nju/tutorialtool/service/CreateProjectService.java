package com.nju.tutorialtool.service;

import com.nju.tutorialtool.model.ProjectInfo;
import com.nju.tutorialtool.template.common.PomXmlResourceFile;
import com.nju.tutorialtool.template.spring.ApplicationClassFile;
import com.nju.tutorialtool.template.spring.ApplicationPropertiesResourceFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * @author Shenmiu
 */

@Service("createProjectService")
public class CreateProjectService {
    @Autowired
    private UserService userService;

    public void createProject(ProjectInfo projectInfo, String projectType) {
        try {
            createEmptyProject(projectInfo, projectType);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String toPackage(String packageDir) {
        return packageDir.replaceAll("/", ".");
    }

    private static String toDir(String packageDir) {
        return packageDir.replaceAll("\\.", "/");
    }

    private void createEmptyProject(ProjectInfo projectInfo, String projectType) throws Exception {
        PomXmlResourceFile pxrf = new PomXmlResourceFile(userService.getUserFolder() + File.separator + projectInfo.getArtifactId(), projectInfo.getGroupId(), projectInfo.getArtifactId(), projectInfo.getDependencies());
        pxrf.generate();

        ApplicationPropertiesResourceFile ayrf = new ApplicationPropertiesResourceFile(userService.getUserFolder() + File.separator + projectInfo.getArtifactId() + "/" + "src/main/resources", projectType);
        ayrf.generate();

        ApplicationClassFile acf = new ApplicationClassFile(userService.getUserFolder() + File.separator + projectInfo.getArtifactId() + "/" + "src/main/java" + "/" + toDir(projectInfo.getGroupId()) + "/" + projectInfo.getArtifactId(), toPackage(projectInfo.getGroupId() + "/" + projectInfo.getArtifactId()), projectInfo.getDependencies());
        acf.generate();
    }
}
