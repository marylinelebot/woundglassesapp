package com.example.myapplication.ui.database.classes;

public class User {
    private int id;
    private String name;
    private String surname;
    private int groupId;
    private int roleId;
    private String email;
    private String pwd;
    private int statusId;
    private String serialNumber;



    // Constructor, getters et setters
    public User(String name, String surname, int groupId, int roleId, String email, String pwd, int statusId, String serialNumber) {
        this.name = name;
        this.surname = surname;
        this.groupId = groupId;
        this.roleId = roleId;
        this.email = email;
        this.pwd = pwd;
        this.statusId = statusId;
        this.serialNumber = serialNumber;
    }

    public User(String surname, String name, String email, String pwd) {
        this.surname = surname;
        this.name = name;
        this.email = email;
        this.pwd = pwd;
    }
    public User() {

    }

    public User(int id, String name, String surname, String email, String pwd, int statusId) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.pwd = pwd;
        this.statusId = statusId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }
}
