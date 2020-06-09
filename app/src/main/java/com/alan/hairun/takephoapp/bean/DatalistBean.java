package com.alan.hairun.takephoapp.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author: Alan
 * @date: 2020/5/10 0010
 * @time: 上午 12:23
 * @deprecated:
 */
@Entity
public class DatalistBean {
    @Id(autoincrement = true)
    public Long id;
    public String bitName;
    public String name;
    public String picture;
    public String date;
    public double lng;
    public double lat;
    @Generated(hash = 31356846)
    public DatalistBean(Long id, String bitName, String name, String picture,
            String date, double lng, double lat) {
        this.id = id;
        this.bitName = bitName;
        this.name = name;
        this.picture = picture;
        this.date = date;
        this.lng = lng;
        this.lat = lat;
    }
    @Generated(hash = 1654847457)
    public DatalistBean() {
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
    public String getPicture() {
        return this.picture;
    }
    public void setPicture(String picture) {
        this.picture = picture;
    }
    public String getDate() {
        return this.date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public double getLng() {
        return this.lng;
    }
    public void setLng(double lng) {
        this.lng = lng;
    }
    public double getLat() {
        return this.lat;
    }
    public void setLat(double lat) {
        this.lat = lat;
    }
    public String getBitName() {
        return this.bitName;
    }
    public void setBitName(String bitName) {
        this.bitName = bitName;
    }
}
