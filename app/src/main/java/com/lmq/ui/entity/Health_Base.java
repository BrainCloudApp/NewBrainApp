package com.lmq.ui.entity;

import java.io.Serializable;

public class Health_Base implements Serializable{
    public String name;
    public String img;
    public String sex;
    public String birth;
    public String healthstatus;
    public String healthproblem;
    public String phone;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getHealthstatus() {
        return healthstatus;
    }

    public void setHealthstatus(String healthstatus) {
        this.healthstatus = healthstatus;
    }

    public String getHealthproblem() {
        return healthproblem;
    }

    public void setHealthproblem(String healthproblem) {
        this.healthproblem = healthproblem;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
