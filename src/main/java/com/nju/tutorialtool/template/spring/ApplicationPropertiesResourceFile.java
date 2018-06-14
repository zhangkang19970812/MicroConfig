package com.nju.tutorialtool.template.spring;

import com.nju.tutorialtool.base.ResourceFile;

public class ApplicationPropertiesResourceFile extends ResourceFile {
	private static final String FILE_NAME = "application.properties";
	public ApplicationPropertiesResourceFile(String fileDir, String projectType) {
		super(fileDir, FILE_NAME);
		String resource = "";
		if (projectType.equals("eurekaServer")) {
			resource = "server.port=8761\n" +
					"eureka.client.service-url.defaultZone=http://eureka:8761/eureka/\n" +
					"eureka.client.register-with-eureka=false\n" +
					"eureka.client.fetch-registry=false";
		}
		else if (projectType.equals("zuul")){
			resource = "server.port=8040\n" +
					"spring.application.name=zuul\n" +
					"eureka.client.service-url.defaultZone=http://eureka:8761/eureka/";
		}
		else {
			resource = "server.port=8040";
		}
		init(resource);
	}
}
