package com.example.bookatreat;

public class User {

    private static final String TAG = "User";

    private String name, lastName, email, description, address, type, uid;


    public User(String name, String lastName, String email, String type, String uid) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.type = type;
        this.uid = uid;

    }


    public static String getTAG() {
        return TAG;
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
