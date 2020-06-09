package com.alan.hairun.takephoapp.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author: Alan
 * @date: 2020/5/17 0017
 * @time: 下午 11:56
 * @deprecated: 项目信息表
 */
@Entity
public class CheckDataBean {
    @Id(autoincrement = true)
    public Long id;
    public String bitName;
    public String type;
    public String projectName;
    public String standard;
    public String unit;
    public String planNum;
    public String one;
    public String two;
    public String three;
    @Generated(hash = 1574850473)
    public CheckDataBean(Long id, String bitName, String type, String projectName,
            String standard, String unit, String planNum, String one, String two,
            String three) {
        this.id = id;
        this.bitName = bitName;
        this.type = type;
        this.projectName = projectName;
        this.standard = standard;
        this.unit = unit;
        this.planNum = planNum;
        this.one = one;
        this.two = two;
        this.three = three;
    }
    @Generated(hash = 857365651)
    public CheckDataBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getProjectName() {
        return this.projectName;
    }
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
    public String getStandard() {
        return this.standard;
    }
    public void setStandard(String standard) {
        this.standard = standard;
    }
    public String getUnit() {
        return this.unit;
    }
    public void setUnit(String unit) {
        this.unit = unit;
    }
    public String getPlanNum() {
        return this.planNum;
    }
    public void setPlanNum(String planNum) {
        this.planNum = planNum;
    }
    public String getBitName() {
        return this.bitName;
    }
    public void setBitName(String bitName) {
        this.bitName = bitName;
    }
    public String getOne() {
        return this.one;
    }
    public void setOne(String one) {
        this.one = one;
    }
    public String getTwo() {
        return this.two;
    }
    public void setTwo(String two) {
        this.two = two;
    }
    public String getThree() {
        return this.three;
    }
    public void setThree(String three) {
        this.three = three;
    }
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }

}
