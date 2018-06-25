package com.nju.tutorialtool.model;

import java.util.List;

public class PreviewFileInfo {
    private String fileName;
    private String fileContent;
    /**
     * 需要高亮的行数列表
     */
    private List<Integer> linesList;

    public PreviewFileInfo(String fileName, String fileContent, List<Integer> linesList) {
        this.fileName = fileName;
        this.fileContent = fileContent;
        this.linesList = linesList;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileContent() {
        return fileContent;
    }

    public void setFileContent(String fileContent) {
        this.fileContent = fileContent;
    }

    public List<Integer> getLinesList() {
        return linesList;
    }

    public void setLinesList(List<Integer> linesList) {
        this.linesList = linesList;
    }
}
