package com.example.visudhaajivamapp.models;

public class Category {

    int id,row_order,status;
    String image,web_image,subtitle,name;

    public Category(int id, int row_order,String name,String subtitle,String web_image, int status, String image ) {
        this.id = id;
        this.row_order = row_order;
        this.status = status;
        this.image = image;
        this.web_image = web_image;
        this.subtitle = subtitle;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRow_order() {
        return row_order;
    }

    public void setRow_order(int row_order) {
        this.row_order = row_order;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getWeb_image() {
        return web_image;
    }

    public void setWeb_image(String web_image) {
        this.web_image = web_image;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
