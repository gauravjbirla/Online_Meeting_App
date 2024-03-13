package com.example.onlinemeetingapp;

public class UserData {

    private String name, email, phoneNo,key;



    public UserData(String name, String email, String phoneNo,String key) {
        this.name = name;
        this.email = email;
        this.phoneNo = phoneNo;
        this.key = key;

    }
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

  }