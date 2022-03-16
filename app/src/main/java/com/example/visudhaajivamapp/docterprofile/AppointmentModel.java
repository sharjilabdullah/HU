package com.example.visudhaajivamapp.docterprofile;

import java.io.Serializable;

public class AppointmentModel implements Serializable {
    private String id;
    private String date;
    private String time;
    private String name;
    private String phone;
    private String email;
    private String other;

    public AppointmentModel(){

    }

    public AppointmentModel(String id, String date, String time, String name, String phone, String email, String other) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.other = other;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }
}