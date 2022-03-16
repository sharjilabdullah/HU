package com.example.visudhaajivamapp.manageaddress;

import android.content.Intent;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class BuyNowModel implements Serializable {
    private String user_id;
    private String user_name;
    private String mobile;
    private String deliverymethod;
    private double tax;
    private double total; // Total price
    private String cash;
    private  ArrayList<Integer> id;
    private ArrayList<Double> price;
    private ArrayList<Integer> quantity;
    private ArrayList<String> pname;
    private String username;
    private String address;//address
    private String address1;//pincode
    private String address2;//landmark
    private String getAddress3;//city
    private String paymethod;
    private String country;
    private String state;

    BuyNowModel(){

    }

    public BuyNowModel(String user_id, String user_name, String mobile, String deliverymethod, double tax, double total, String cash, ArrayList<Integer> id, ArrayList<Double> price, ArrayList<Integer> quantity, ArrayList<String> pname, String username, String address, String address1, String address2, String getAddress3, String paymethod, String country, String state) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.mobile = mobile;
        this.deliverymethod = deliverymethod;
        this.tax = tax;
        this.total = total;
        this.cash = cash;
        this.id = id;
        this.price = price;
        this.quantity = quantity;
        this.pname = pname;
        this.username = username;
        this.address = address;
        this.address1 = address1;
        this.address2 = address2;
        this.getAddress3 = getAddress3;
        this.paymethod = paymethod;
        this.country = country;
        this.state = state;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDeliverymethod() {
        return deliverymethod;
    }

    public void setDeliverymethod(String deliverymethod) {
        this.deliverymethod = deliverymethod;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getCash() {
        return cash;
    }

    public void setCash(String cash) {
        this.cash = cash;
    }

    public ArrayList<Integer> getId() {
        return id;
    }

    public void setId(ArrayList<Integer> id) {
        this.id = id;
    }

    public ArrayList<Double> getPrice() {
        return price;
    }

    public void setPrice(ArrayList<Double> price) {
        this.price = price;
    }

    public ArrayList<Integer> getQuantity() {
        return quantity;
    }

    public void setQuantity(ArrayList<Integer> quantity) {
        this.quantity = quantity;
    }

    public ArrayList<String> getPname() {
        return pname;
    }

    public void setPname(ArrayList<String> pname) {
        this.pname = pname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddress3() {
        return getAddress3;
    }

    public void setAddress3(String getAddress3) {
        this.getAddress3 = getAddress3;
    }

    public String getPaymethod() {
        return paymethod;
    }

    public void setPaymethod(String paymethod) {
        this.paymethod = paymethod;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
