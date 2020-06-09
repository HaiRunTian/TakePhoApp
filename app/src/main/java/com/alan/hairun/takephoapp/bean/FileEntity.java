package com.alan.hairun.takephoapp.bean;

/**
 * Created by HaiRun on 2018/11/29 0029.
 * 读取手机文件夹bean
 */

public class FileEntity {


    public enum Type{
        FLODER,FILE
    }
    public String filePath; //文件路径
    public String fileName; //文件名
    public String fileSize; //文件长度
    public Type fileType; //文件类型
    public boolean isCheck; //是否被选中

    public boolean isCheck() {
        return isCheck;
    }
    public void setCheck(boolean check) {
        isCheck = check;
    }
    public String getFilePath() {
        return filePath;
    }
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public String getFileSize() {
        return fileSize;
    }
    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }
    public Type getFileType() {
        return fileType;
    }
    public void setFileType(Type fileType) {
        this.fileType = fileType;
    }


}
