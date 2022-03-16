package com.example.visudhaajivamapp.models;

import java.io.Serializable;

public class SubCategory implements Serializable {

    private int id,subcategory_id,stock,product_id,category_id;
    private String name;
    private double discounted_price;
    private double price;
    private String image,manufacture,mande_in;

    public SubCategory(){

    }
    public SubCategory(int id, String name, double price, double discounted_price, int category_id, int subcategory_id, String manufacture, String mande_in, int stock, int product_id, String image) {
        this.id = id;
        this.name = name;
        this.discounted_price = discounted_price;
        this.price = price;
        this.image = image;
        this.category_id = category_id;
        this.subcategory_id = subcategory_id;
        this.stock = stock;
        this.product_id = product_id;
        this.manufacture = manufacture;
        this.mande_in = mande_in;

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

    public double getDiscounted_price() {
        return discounted_price;
    }

    public void setDiscounted_price(double discounted_price) {
        this.discounted_price = discounted_price;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }



    public int getSubcategory_id() {
        return subcategory_id;
    }

    public void setSubcategory_id(int subcategory_id) {
        this.subcategory_id = subcategory_id;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getManufacture() {
        return manufacture;
    }

    public void setManufacture(String manufacture) {
        this.manufacture = manufacture;
    }

    public String getMande_in() {
        return mande_in;
    }

    public void setMande_in(String mande_in) {
        this.mande_in = mande_in;
    }
}
