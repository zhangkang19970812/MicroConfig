package com.nju.tutorialtool.util.sql;

import java.util.HashMap;
import java.util.Map;

public class Types {
    // 自定义的基本类型，和数据对应,最后组成字符串
    public static final String VARCHAR = "VARCHAR";
    public static final String INTEGER = "INTEGER";
    public static final String BOOLEAN = "BOOLEAN";
    public static final String LONG = "BIGINT";
    public static final String TEXT = "TEXT";
    public static final String BLOB = "BLOB";
    public static final String FLOAT = "FLOAT";
    public static final String DOUBLE = "DOUBLE";
    public static final String DATE = "DATE";
    public static final String TIME = "TIME";

    // 默认长度
    public static final String VARCHAR_LENGTH = "255";
    public static final String INTEGER_LENGTH = "255";
    public static final String LONG_LENGTH = "200";
    public static final String TEXT_LENGTH = "null";
    public static final String BLOB_LENGTH = "null";
    public static final String FLOAT_LENGTH = "200";
    public static final String DOUBLE_LENGTH = "200";
    public static final String DATE_LENGTH = "null";
    public static final String TIME_LENGTH = "null";

    // 将类型 默认长度，放入集合
    public static Map<String, String> map = new HashMap<String, String>();
    static {
        map.put(VARCHAR, VARCHAR_LENGTH);
        map.put(INTEGER, INTEGER_LENGTH);
        map.put(BOOLEAN, "0");
        map.put(LONG, LONG_LENGTH);
        map.put(TEXT, TEXT_LENGTH);
        map.put(BLOB, BLOB_LENGTH);
        map.put(FLOAT, FLOAT_LENGTH);
        map.put(DOUBLE, DOUBLE_LENGTH);
        map.put(DATE, DATE_LENGTH);
        map.put(TIME, TIME_LENGTH);
    }

    public static String getTypeSql(String type) {
        String temp = type.substring(type.lastIndexOf(".") + 1);
        String sql = "";
        switch (temp) {
            case "String":
                sql = VARCHAR + "(" + VARCHAR_LENGTH + ")";
                break;
            case "Long":
                sql = INTEGER + "(" + INTEGER_LENGTH + ")";
                break;
            case "Integer":
                sql = INTEGER + "(" + INTEGER_LENGTH + ")";
                break;
            case "int":
                sql = INTEGER + "(" + INTEGER_LENGTH + ")";
                break;
            case "boolean":
                sql = BOOLEAN;
                break;
            case "BigInteger":
                sql = LONG + " " + "UNSIGNED";
                break;
            case "float":
                sql = FLOAT + "(" + FLOAT_LENGTH + ")";
                break;
            case "double":
                sql = DOUBLE + "(" + DOUBLE_LENGTH + ")";
                break;
            case "Date":
                sql = DATE;
                break;
            case "Time":
                sql = TIME;
                break;
            default:
                break;
        }
        return sql;
    }
}
