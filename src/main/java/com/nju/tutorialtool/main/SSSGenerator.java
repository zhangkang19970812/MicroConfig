package com.nju.tutorialtool.main;

import com.nju.tutorialtool.template.common.PomXmlResourceFile;
import com.nju.tutorialtool.template.spring.ApplicationClassFile;
import com.nju.tutorialtool.template.spring.ApplicationPropertiesResourceFile;

import java.util.ArrayList;
import java.util.List;

public class SSSGenerator {
	public static void main(String[] args) throws Exception {
//		Scanner scanner = new Scanner(System.in);
        System.out.print("BaseDir:");
        String baseDir = "C:/Users/zk/Desktop";
        System.out.print("ProjectName:");
//		String projectName = scanner.next();
        String resourcesDir = "src/main/resources";
        String javaDir = "src/main/java";
        System.out.print("PackageDir:");
        String packageDir = "";
        System.out.print("groupId:");
        String groupId = "com.zk";
        System.out.print("artifactId:");
        String artifactId = "zuul";
        System.out.print("dependencies:");
        List<String> dependencies = new ArrayList<>();
        dependencies.add("zuul");
        dependencies.add("eurekaDiscovery");
//		while (scanner.hasNext()) {
//			dependencies.add(scanner.next());
//		}

        PomXmlResourceFile pxrf = new PomXmlResourceFile(baseDir + "/" + artifactId, groupId, artifactId, dependencies);
        pxrf.generate();

        ApplicationPropertiesResourceFile ayrf = new ApplicationPropertiesResourceFile(baseDir + "/" + artifactId + "/" + resourcesDir, "zuul");
        ayrf.generate();

        ApplicationClassFile acf = new ApplicationClassFile(baseDir + "/" + artifactId + "/" + javaDir + "/" + toDir(groupId) + "/" + artifactId, toPackage(groupId + "/" + artifactId), dependencies);
        acf.generate();
    }
//
////		ResultClassFile rcf = new ResultClassFile(baseDir+"/"+projectName+"/"+javaDir+"/"+packageDir+"/util", toPackage(packageDir), "util");
////		rcf.personalGenerate();
////		String model;
////		while(true) {
////			System.out.print("PojoName(input \"exit\" to exit):");
////			model = scanner.next();
////			if(model.equals("exit")) {
////				break;
////			}
////			PojoClassFile pcf = new PojoClassFile(baseDir+"/"+projectName+"/"+javaDir+"/"+packageDir+"/model", toPackage(packageDir), "model", model);
////			pcf.generate();
////			RepositoryInterfaceFile rif = new RepositoryInterfaceFile(baseDir+"/"+projectName+"/"+javaDir+"/"+packageDir+"/repository",toPackage(packageDir),"repository",model);
////			rif.generate();
////			ServiceInterfaceFile sif = new ServiceInterfaceFile(baseDir+"/"+projectName+"/"+javaDir+"/"+packageDir+"/service",toPackage(packageDir),"service",model);
////			sif.generate();
////			ServiceClassFile scf = new ServiceClassFile(baseDir+"/"+projectName+"/"+javaDir+"/"+packageDir+"/service/impl",toPackage(packageDir),"service.impl",model);
////			scf.generate();
////			ControllerClassFile ccf = new ControllerClassFile(baseDir+"/"+projectName+"/"+javaDir+"/"+packageDir+"/controller", toPackage(packageDir),"controller", model);
////			ccf.generate();
////		}
//	}
    private static String toPackage (String packageDir){
	    return packageDir.replaceAll("/", ".");
	}

    private static String toDir (String packageDir){
	    return packageDir.replaceAll("\\.", "/");
	}

}
