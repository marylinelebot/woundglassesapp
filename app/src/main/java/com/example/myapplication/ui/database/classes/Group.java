package com.example.myapplication.ui.database.classes;

public class Group {
    private String name;
    private String email;
    private int typeId;


    // Constructor, getters et setters
    public Group(String name, String email, int typeId) {
        this.name = name;
        this.email = email;
        this.typeId = typeId;
    }
    public Group() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }
}
