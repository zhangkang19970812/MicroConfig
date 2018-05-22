package com.nju.tutorialtool.template.spring;

import com.nju.tutorialtool.base.ResourceFile;

public class ApplicationPropertiesResourceFile extends ResourceFile {
	private static final String FILE_NAME = "application.properties";
	public ApplicationPropertiesResourceFile(String fileDir) {
		super(fileDir, FILE_NAME);
		String resource = 
				"server.port=8080";
		init(resource);
	}
}
