package com.nju.tutorialtool.template.common;


import com.nju.tutorialtool.base.ResourceFile;
import com.nju.tutorialtool.util.DependencyConstant;

import java.util.List;

public class PomXmlResourceFile extends ResourceFile {
//	private String groupId;
//	private String artifactId;
//	private String springVersion;
//	private List<String> dependencies;
	private static final String FILE_NAME = "pom.xml";
	public PomXmlResourceFile(String fileDir, String groupId, String artifactId, List<String> dependencies) {
		super(fileDir, FILE_NAME);
		String resource =
				"<project xmlns=\"http://maven.apache.org/POM/4.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd\">\r\n" + 
				"  <modelVersion>4.0.0</modelVersion>\r\n" + 
				"  <groupId>"+ groupId + "</groupId>\r\n" +
				"  <artifactId>"+ artifactId +"</artifactId>\r\n" +
				"  <version>0.0.1-SNAPSHOT</version>\r\n" + 
				"  <packaging>jar</packaging>\r\n" + 
				"  <parent>\r\n" +
				"	   <groupId>org.springframework.boot</groupId>\r\n" +
				"	   <artifactId>spring-boot-starter-parent</artifactId>\r\n" +
				"	   <version>1.5.3.RELEASE</version>\r\n" +
				"	   <relativePath/>\r\n" +
				"  </parent>\r\n" +
				"  <properties>\r\n" +
				"	   <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>\r\n" +
				"	   <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>\r\n" +
				"	   <java.version>1.8</java.version>\r\n" +
				"  </properties>\r\n" +
				"  <dependencies>\r\n" +
				"	   <dependency>\r\n" +
				"		   <groupId>org.springframework.boot</groupId>\r\n" +
				"		   <artifactId>spring-boot-starter-web</artifactId>\r\n" +
				"	   </dependency>\r\n" +
				"	   <dependency>\r\n" +
				"		   <groupId>org.springframework.boot</groupId>\r\n" +
				"		   <artifactId>spring-boot-starter-test</artifactId>\r\n" +
				"		   <scope>test</scope>\r\n" +
				"	   </dependency>\r\n" +
				"	   <dependency>\r\n" +
				"		   <groupId>org.springframework.boot</groupId>\r\n" +
				"		   <artifactId>spring-boot-starter-actuator</artifactId>\r\n" +
				"	   </dependency>\r\n" +
				DependencyConstant.getDependencies(dependencies) +
				"  </dependencies>\r\n" +
				"  <dependencyManagement>\r\n" +
				"	   <dependencies>\r\n" +
				"	   <dependency>\r\n" +
				"		   <groupId>org.springframework.cloud</groupId>\r\n" +
				"		   <artifactId>spring-cloud-dependencies</artifactId>\r\n" +
				"		   <version>Edgware.RELEASE</version>\r\n" +
				"		   <type>pom</type>\r\n" +
				"		   <scope>import</scope>\r\n" +
				"	   </dependency>\r\n" +
				"	   </dependencies>\r\n" +
				"  </dependencyManagement>\r\n" +
				"  <build>\r\n" +
				"	   <plugins>\r\n" +
				"		   <plugin>\r\n" +
				"			   <groupId>org.springframework.boot</groupId>\r\n" +
				"			   <artifactId>spring-boot-maven-plugin</artifactId>\r\n" +
				"		   </plugin>\r\n" +
				"	   </plugins>\r\n" +
				"  </build>\r\n" +
				"</project>";
		init(resource);
	}
}
