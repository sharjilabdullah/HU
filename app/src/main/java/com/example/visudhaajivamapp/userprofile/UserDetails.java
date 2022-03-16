package com.example.visudhaajivamapp.userprofile;

public class UserDetails {
    private String name;
    private String user_name;
    private String phone;
    private String address;
    private String aadhar;
    private String dist;
    private String country;
    private String blood;
    private String pincode;
    private String dob;
    private String expiry;
    private String photo;
    private String identity;

    public UserDetails(){

    }

    public UserDetails(String name, String user_name, String phone, String address, String aadhar, String dist, String country, String blood, String pincode, String dob, String expiry, String photo, String identity) {
        this.name = name;
        this.user_name = user_name;
        this.phone = phone;
        this.address = address;
        this.aadhar = aadhar;
        this.dist = dist;
        this.country = country;
        this.blood = blood;
        this.pincode = pincode;
        this.dob = dob;
        this.expiry = expiry;
        this.photo = photo;
        this.identity = identity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAadhar() {
        return aadhar;
    }

    public void setAadhar(String aadhar) {
        this.aadhar = aadhar;
    }

    public String getDist() {
        return dist;
    }

    public void setDist(String dist) {
        this.dist = dist;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getBlood() {
        return blood;
    }

    public void setBlood(String blood) {
        this.blood = blood;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }
}