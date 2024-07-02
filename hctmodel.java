package com.example.skind;

public class hctmodel {
    String id,name,details1,details2,img;

    public hctmodel(String id, String name, String details1, String details2, String img) {
        this.id = id;
        this.name = name;
        this.details1 = details1;
        this.details2 = details2;
        this.img = img;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDetails1() {
        return details1;
    }

    public String getDetails2() {
        return details2;
    }

    public String getImg() {
        return img;
    }
}
