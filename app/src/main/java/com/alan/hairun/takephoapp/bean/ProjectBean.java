package com.alan.hairun.takephoapp.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author: Alan
 * @date: 2020/5/10 0010
 * @time: 上午 12:21
 * @deprecated:
 */
@Entity
public class ProjectBean {
    @Id(autoincrement = true)
    private Long id;
    private String name;
    private String date;
    @Generated(hash = 289345286)
    public ProjectBean(Long id, String name, String date) {
        this.id = id;
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
}
