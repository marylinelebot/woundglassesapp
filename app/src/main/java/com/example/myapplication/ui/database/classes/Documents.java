package com.example.myapplication.ui.database.classes;

public class Documents {
    private int id;
    private String title;
    private String path;
    private String informations;


    // Constructor, getters et setters
    public Documents(int id, String title, String path, String informations) {
        this.id = id;
        this.title = title;
        this.path = path;
        this.informations = informations;
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

    public String getInformations() {
        return informations;
    }

    public void setInformations(String informations) {
        this.informations = informations;
    }
}

