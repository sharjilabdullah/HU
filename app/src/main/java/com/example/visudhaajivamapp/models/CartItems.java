package com.example.visudhaajivamapp.models;

public class CartItems {
    private int id, quantity,urid;
    private String name, image,identity;
    private double discounted_price;
    private double price;
    private boolean selected;




    public CartItems(int urid, String identity, int id, String name, double price, double discounted_price, int quantity, String image, boolean selected) {
        this.urid = urid;
        this.identity = identity;
        this.id = id;
        this.quantity = quantity;
        this.name = name;
        this.image = image;
        this.discounted_price = discounted_price;
        this.price = price;
        this.selected=selected;
    }


    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUrid() {
        return urid;
    }

    public void setUrid(int urid) {
        this.urid = urid;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
}
