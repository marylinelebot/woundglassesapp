package com.example.myapplication.ui.database.classes;

public class RoleUser {
    private int id;
    private String roleTitle;


    // Constructor, getters et setters
    public RoleUser(int id, String roleTitle) {
        this.id = id;
        this.roleTitle = roleTitle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoleTitle() {
        return roleTitle;
    }

    public void setRoleTitle(String roleTitle) {
        this.roleTitle = roleTitle;
    }
}