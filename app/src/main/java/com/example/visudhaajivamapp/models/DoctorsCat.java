package com.example.visudhaajivamapp.models;

public class DoctorsCat {
    private int id;
    private String title;
    private String disc;
    private String coverImage;
    //private String songURL;



    public DoctorsCat  (int id ,String name,String description,String photo){
        this.id = id;
        this.title = name;
        this.disc = description;
        this.coverImage = photo;
        //this.songURL = songURL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtists() {
        return disc;
    }

    public void setArtists(String artists) {
        this.disc= artists;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

//    public String getSongURL() {
//        return songURL;
//    }
//
//    public void setSongURL(String songURL) {
//        this.songURL = songURL;
//    }
}

