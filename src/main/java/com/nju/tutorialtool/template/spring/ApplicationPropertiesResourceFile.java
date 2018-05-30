package com.nju.tutorialtool.template.spring;

import com.nju.tutorialtool.base.ResourceFile;

public class ApplicationPropertiesResourceFile extends ResourceFile {
	private static final String FILE_NAME = "application.properties";
	public ApplicationPropertiesResourceFile(String fileDir, String projectType) {
		super(fileDir, FILE_NAME);
		String port = "";
		if (projectType.equals("eurekaServer")) {
			port = "8761";
		}
		else if (projectType.equals("zuul")){
			port = "8040";
		}
		else {
			port = "8080";
		}
		String resource = 
				"server.port=" + port;
		init(resource);
	}
}
