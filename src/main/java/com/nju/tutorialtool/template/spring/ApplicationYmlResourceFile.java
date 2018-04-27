package com.nju.tutorialtool.template.spring;

import com.nju.tutorialtool.base.ResourceFile;

public class ApplicationYmlResourceFile extends ResourceFile {
	private static final String FILE_NAME = "application.yml";
	public ApplicationYmlResourceFile(String fileDir) {
		super(fileDir, FILE_NAME);
		String resource = 
				"server:\r\n" + 
				"  port: 8080";
		init(resource);
	}
}
