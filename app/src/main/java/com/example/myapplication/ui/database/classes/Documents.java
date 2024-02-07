package com.example.myapplication.ui.database.classes;

public class Documents {
    private int id;
    private String title;
    private String path;
    private String information;


    // Constructor, getters et setters
    public Documents(int id, String title, String path, String information) {
        this.id = id;
        this.title = title;
        this.path = path;
        this.information = information;
    }

    public Documents(int id, String title, String path) {
        this.id = id;
        this.title = title;
        this.path = path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }
}

