package com.alan.hairun.takephoapp.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author: Alan
 * @date: 2020/5/11 0011
 * @time: 下午 9:20
 * @deprecated:
 */
@Entity
public class UserBean {
    public Long id;
    public String userName;
    public String userPasswrod;
    public String crateTime;
    public String img;
    public String sex;
    @Generated(hash = 600293102)
    public UserBean(Long id, String userName, String userPasswrod, String crateTime,
            String img, String sex) {
        this.id = id;
        this.userName = userName;
        this.userPasswrod = userPasswrod;
        this.crateTime = crateTime;
        this.img = img;
        this.sex = sex;
    }
    @Generated(hash = 1203313951)
    public UserBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUserName() {
        return this.userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserPasswrod() {
        return this.userPasswrod;
    }
    public void setUserPasswrod(String userPasswrod) {
        this.userPasswrod = userPasswrod;
    }
    public String getCrateTime() {
        return this.crateTime;
    }
    public void setCrateTime(String crateTime) {
        this.crateTime = crateTime;
    }
    public String getImg() {
        return this.img;
    }
    public void setImg(String img) {
        this.img = img;
    }
    public String getSex() {
        return this.sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }

}
