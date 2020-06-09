package com.alan.hairun.takephoapp.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author: Alan
 * @date: 2020/5/10 0010
 * @time: 上午 12:21
 * @deprecated:
 * 一级项目 项目类型  三级项目
 */
@Entity
public class ProjectBean {
    @Id(autoincrement = true)
    public Long id;
    public String bitName;
    public String type;
    public String name;
    public String date;
    @Generated(hash = 665778440)
    public ProjectBean(Long id, String bitName, String type, String name,
            String date) {
        this.id = id;
        this.bitName = bitName;
        this.type = type;
        this.name = name;
        this.date = date;
    }
    @Generated(hash = 882656566)
    public ProjectBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDate() {
        return this.date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getBitName() {
        return this.bitName;
    }
    public void setBitName(String bitName) {
        this.bitName = bitName;
    }
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }
}
