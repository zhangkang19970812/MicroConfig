package com.nju.tutorialtool.template.springdatajpa;

import com.nju.tutorialtool.base.JavaFile;

import java.util.LinkedList;
import java.util.List;

public class RepositoryInterfaceFile extends JavaFile {
	private String model;
	public RepositoryInterfaceFile(String fileDir, String mainPackage, String curPackage, String model) {
		super(fileDir, getClassName(model).append(".java").toString(), mainPackage+"."+curPackage, INTERFACE_TYPE, getClassName(model).toString());
		this.model = model;
		List<String> imports = new LinkedList<>();
		List<String> classAnnotations = new LinkedList<>();
		List<String> extendss = new LinkedList<>();
		List<String> implementss = new LinkedList<>();
		List<Field> fields = new LinkedList<>();
		List<Method> methods = new LinkedList<>();
		
		//imports
		imports.add(getImportName(mainPackage, model).toString());
		imports.add("org.springframework.data.jpa.repository.JpaRepository");
		imports.add("org.springframework.data.jpa.repository.JpaSpecificationExecutor");
		
		//classAnnotations none
		
		//extendss
		StringBuilder extendsi = new StringBuilder();
		extendsi.append("JpaRepository<");
		extendsi.append(getModelClass(model));
		extendsi.append(",Integer>");
		extendss.add(extendsi.toString());
		extendsi = new StringBuilder();
		extendsi.append("JpaSpecificationExecutor<");
		extendsi.append(getModelClass(model));
		extendsi.append(">");
		extendss.add(extendsi.toString());
		
		//implementss none
		
		//fields none
		
		//methods none
		
		super.init(imports, classAnnotations, extendss, implementss, fields, methods);
	}
	public static StringBuilder getModelName(String model) {
		StringBuilder modelName = new StringBuilder(model.toLowerCase());
		modelName.append("_model");
		return modelName;
	}
	public static StringBuilder getModelClass(String model) {
		StringBuilder modelClass = new StringBuilder();
		modelClass.append((model.charAt(0)+"").toUpperCase());
		modelClass.append(model.toLowerCase().substring(1));
		return modelClass;
	}
	public static StringBuilder getClassName(String model) {
		StringBuilder className = new StringBuilder();
		className.append(getModelClass(model));
		className.append("Repository");
		return className;
	}
	public static StringBuilder getImportName(String packagei,String model) {
		StringBuilder importName = new StringBuilder();
		importName.append(packagei);
		importName.append(".model.");
		importName.append(getModelClass(model));
		return importName;
	}
}
