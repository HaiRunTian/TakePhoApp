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
    private Long id;
    private String name;
    private String picture;
    private String date;
    private double lng;
    private double lat;
    @Generated(hash = 2056055858)
    public DatalistBean(Long id, String name, String picture, String date,
            double lng, double lat) {
        this.id = id;
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
}
