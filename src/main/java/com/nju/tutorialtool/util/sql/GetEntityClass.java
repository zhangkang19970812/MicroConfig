package com.nju.tutorialtool.util.sql;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GetEntityClass {
    public static List<Class> getEntity(String projectPath) throws Exception {
        List<Class> list = new ArrayList<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = factory.newDocumentBuilder();
        Document document = db.parse(new File(projectPath + "/src/main/resources/META-INF/persistence.xml"));

        NodeList node = document.getElementsByTagName("class");
        for(int i=0;i<node.getLength();i++){
            Element element = (Element)node.item(i);
            String content = element.getFirstChild().getNodeValue();
            list.add(Class.forName(content));
        }
        return list;
    }
}
