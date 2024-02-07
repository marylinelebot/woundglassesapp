package com.example.myapplication.ui.database.classes;

public class GroupUser {
    private String userEmail;
    private String groupName;


    // Constructor, getters et setters
    public GroupUser(String userEmail, String groupName) {
        this.userEmail = userEmail;
        this.groupName = groupName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
