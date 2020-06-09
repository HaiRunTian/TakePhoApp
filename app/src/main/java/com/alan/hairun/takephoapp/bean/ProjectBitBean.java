package com.alan.hairun.takephoapp.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author: Alan
 * @date: 2020/5/25 0025
 * @time: 下午 8:32
 * @deprecated:
 * 一级项目
 */
@Entity
public class ProjectBitBean {
    @Id(autoincrement = true)
    public Long id;
    public String prjName;
    public String Date;
    public String remark;
    @Generated(hash = 969854179)
    public ProjectBitBean(Long id, String prjName, String Date, String remark) {
        this.id = id;
        this.prjName = prjName;
        this.Date = Date;
        this.remark = remark;
    }
    @Generated(hash = 809014754)
    public ProjectBitBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getPrjName() {
        return this.prjName;
    }
    public void setPrjName(String prjName) {
        this.prjName = prjName;
    }
    public String getDate() {
        return this.Date;
    }
    public void setDate(String Date) {
        this.Date = Date;
    }
    public String getRemark() {
        return this.remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
}
