package com.example.myapplication.ui.database.classes;

public class GroupType {
    private int id;
    private String groupType;


    // Constructor, getters et setters
    public GroupType(int id, String groupType) {
        this.id = id;
        this.groupType = groupType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }
}
