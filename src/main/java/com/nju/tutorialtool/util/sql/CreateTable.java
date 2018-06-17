package com.nju.tutorialtool.util.sql;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class CreateTable {
//    public static void main(String[] args) throws Exception {
//        createSql("account", GetEntityClass.getEntity("C:/Users/zk/Desktop/account_service"), "C:/Users/zk/Desktop/account_service_mysql/schema.sql");
//    }

    public static void createSql(String database, List<Class> cList, String path) {
        StringBuilder finalSql = new StringBuilder();
        finalSql.append("create database `" + database + "` default character set utf8 collate utf8_general_ci;\n"
                + "use " + database + ";\n");
        for (Class c : cList) {
            // 表名
            String tableName = getTableName(c);
            // 创建建表语句
            StringBuilder sql = new StringBuilder("DROP TABLE IF EXISTS `" + tableName + "`;\n"
                    + "CREATE TABLE `" + tableName + "` (\n");
            // 字段集合
            ArrayList<String> columns = new ArrayList<String>();
            //属性集合
            Field[] list = c.getDeclaredFields();
            try {
                for (Field mField : list) {
                    // 字段名
                    String name = null;
                    // 类型
                    String type = null;
                    //键自动增加
                    String generate = "";
                    if (mField.getAnnotation(GeneratedValue.class) != null || isGenerate(c, mField)) {
                        generate = "AUTO_INCREMENT";
                    }

                    if (mField.getAnnotation(Column.class) != null) {
                        // 获取注解信息
                        Column mColumn = mField.getAnnotation(Column.class);
                        // 获得sql需要的别名
                        String nameC = mColumn.name();
                        // 获取注解里的长度
//                    int Clength = mColumn.length();
                        if (nameC.length() == 0) {
                            // 获得默认的字段名
                            name = mField.getName();
                        } else {
                            name = nameC;
                        }
                    }
                    else {
                        name = getColumnName(c, mField);
                    }
                    type = mField.getType().getName();
                    type = Types.getTypeSql(type);
                    columns.add("`" + name + "` " + type + " " + generate);
                }
                // 遍历集合 生成sql语句
                for (String str : columns) {
                    sql.append(" " + str + ",\n");
                }
                //primary key
                sql.append(getIdSql(c) + "\n");
                // 最终的创建表语句
                finalSql.append(sql + " ) ENGINE=InnoDB;\n");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            File file = new File(path);
            if (!file.getParentFile().exists()) {
                if (!file.getParentFile().mkdirs()) {
                }
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            System.out.println("文件路径:"+file.getAbsolutePath());
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,true)));
            out.write(finalSql.toString()) ;
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static <T>String getTableName(Class<T> c) {
        String tableName = null;
        try {
            if (c.isAnnotationPresent(Table.class)) {
                // 提取表名
                tableName = c.getAnnotation(Table.class).name();
            }
            else {
                tableName = c.getName().substring(tableName.lastIndexOf(".")+1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tableName;
    }

    private static boolean isGenerate(Class c, Field field) {
        Method[] m = c.getDeclaredMethods();
        String name = field.getName();
        for(int i = 0;i < m.length;i++){
            if(("get" + name).toLowerCase().equals(m[i].getName().toLowerCase())){
                if (m[i].getDeclaredAnnotation(GeneratedValue.class) != null) {
                    return true;
                }
            }
        }
        return false;
    }

    private static String getIdSql(Class c) {
        Field[] list = c.getDeclaredFields();
        Method[] m = c.getDeclaredMethods();
        String idName = "", methodName = "";
        for (Field field : list) {
            if (field.getAnnotation(Id.class) != null) {
                idName = field.getName();
                break;
            }
        }
        if ("".equals(idName)) {
            for (Method method : m) {
                if (method.getAnnotation(Id.class) != null) {
                    methodName = method.getName();
                    break;
                }
            }
            for (Field field : list) {
                if (("get" + field.getName()).toLowerCase().equals(methodName.toLowerCase())) {
                    idName = field.getName();
                }
            }
        }
        return " " + "PRIMARY KEY (`" + idName + "`)";
    }

    private static String getColumnName(Class c, Field field) {
        Method[] m = c.getDeclaredMethods();
        String name = field.getName();
        for(int i = 0;i < m.length;i++){
            if(("get" + name).toLowerCase().equals(m[i].getName().toLowerCase())){
                if (m[i].getDeclaredAnnotation(Column.class) != null) {
                    Column column = m[i].getDeclaredAnnotation(Column.class);
                    String nameC = column.name();
                    if (nameC.length() == 0) {
                        // 获得默认的字段名
                        return name;
                    } else {
                        return nameC;
                    }
                }
            }
        }
        return name;
    }
}
