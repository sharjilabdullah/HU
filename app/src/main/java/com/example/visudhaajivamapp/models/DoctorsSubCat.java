package com.example.visudhaajivamapp.models;

public class DoctorsSubCat {

    // private String id;
    private  int id;
    private String name;
    private String experience;
    private String address;
    private String photo;
    private String speciality;
    private String price;
    //private String songURL;


    public DoctorsSubCat(int id, String name, String experience, String address, String photo, String speciality, String price) {
        this.id = id;
        this.name = name;
        this.experience = experience;
        this.address = address;
        this.photo = photo;
        this.speciality = speciality;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
