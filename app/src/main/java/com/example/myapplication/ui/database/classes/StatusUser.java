package com.example.myapplication.ui.database.classes;

public class StatusUser {
    private int id;
    private String statusTitle;


    // Constructor, getters et setters
    public StatusUser(int id, String statusTitle) {
        this.id = id;
        this.statusTitle = statusTitle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatusTitle() {
        return statusTitle;
    }

    public void setStatusTitle(String statusTitle) {
        this.statusTitle = statusTitle;
    }
}
