package com.example.lmtapp;

public class ListPojos {
    private    String title;
    private   String description;
    private   String images;
    private   String deb_code;

    public ListPojos(String title, String description,String images,String deb_code){
        this.title = title;
        this.description = description;
        this.images = images;
        this.deb_code =  deb_code;
    }
    public String getTitle(){
        return title;
    }
    public  String getDescription(){
        return  description;
    }

    public  String getImages(){
        return images;
    }
    public  String getDeb_code(){
        return deb_code;
    }
}
