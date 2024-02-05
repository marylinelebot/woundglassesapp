package com.example.myapplication.ui.database.classes;

public class GroupUser {
    private String userEmail;
    private int groupTypeId;


    // Constructor, getters et setters
    public GroupUser(String userEmail, int groupTypeId) {
        this.userEmail = userEmail;
        this.groupTypeId = groupTypeId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public int getGroupTypeId() {
        return groupTypeId;
    }

    public void setGroupTypeId(int groupTypeId) {
        this.groupTypeId = groupTypeId;
    }
}
