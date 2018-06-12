package com.nju.tutorialtool.template.spring;

import com.nju.tutorialtool.base.JavaFile;

import java.util.LinkedList;
import java.util.List;

public class ApplicationClassFile extends JavaFile {
	private static final String CLASS_NAME = "Application";
	private static final String FILE_NAME = "Application.java";
	public ApplicationClassFile(String fileDir, String packagei, List<String> dependencies) {
		super(fileDir, FILE_NAME, packagei, CLASS_TYPE, CLASS_NAME);
		List<String> imports = new LinkedList<>();
		List<String> classAnnotations = new LinkedList<>();
		List<String> extendss = new LinkedList<>();
		List<String> implementss = new LinkedList<>();
		List<Field> fields = new LinkedList<>();
		List<Method> methods = new LinkedList<>();
		
		//imports
		imports.add("org.springframework.boot.SpringApplication");
		imports.add("org.springframework.boot.autoconfigure.SpringBootApplication");
//		imports.add("org.springframework.transaction.annotation.EnableTransactionManagement");

		
		//classAnnotations
		classAnnotations.add("SpringBootApplication");
//		classAnnotations.add("EnableTransactionManagement");

		for (String dependency: dependencies) {
			switch (dependency) {
				case "eurekaServer":
					imports.add("org.springframework.cloud.netflix.eureka.server.EnableEurekaServer");
					classAnnotations.add("EnableEurekaServer");
					break;
				case "eurekaDiscovery":
					if (!dependencies.contains("zuul")){
						imports.add("org.springframework.cloud.client.discovery.EnableDiscoveryClient");
						classAnnotations.add("EnableDiscoveryClient");
					}
					break;
				case "hystrix":
					imports.add("import org.springframework.cloud.netflix.hystrix.EnableHystrix;");
					classAnnotations.add("EnableHystrix");
					break;
				case "zuul":
					imports.add("org.springframework.cloud.netflix.zuul.EnableZuulProxy");
					classAnnotations.add("EnableZuulProxy");
					break;
				case "configServer":
					imports.add("org.springframework.cloud.config.server.EnableConfigServer");
					classAnnotations.add("EnableConfigServer");
					break;
				default:
					break;
			}
		}

		//extendss none
		
		//implementss none
		
		//fields none
		
		//methods
		//main
		List<String> mannotations = null;
		String mmodifier = "public static";
		String resultType = "void";
		String name = "main";
		String args = "String[] args";
		List<String> exceptions = null;
		String methodBody = buildMethod(new String[] {
				new StringBuilder()
				.append("SpringApplication.run(")
				.append(CLASS_NAME)
				.append(".class, args);")
				.toString()
		});
		Method method = new Method(mannotations, mmodifier, resultType, name, args, exceptions, methodBody);
		methods.add(method);
		
		super.init(imports, classAnnotations, extendss, implementss, fields, methods);
	}
	private static String buildMethod(String[] lines) {
		StringBuilder method = new StringBuilder();
		if(lines != null) {
			for (String line : lines) {
				method
				.append("\t\t")
				.append(line)
				.append("\n");
			}
		}
		return method.toString();
	}
}
