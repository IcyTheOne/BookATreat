package com.example.bookatreat;

import java.io.Serializable;

public class Restaurants implements Serializable {

    private String Address, Description, Email, Name;

    public Restaurants(String Address, String Description, String Email, String Name) {
        this.Name = Name;
        this.Address = Address;
        this.Description = Description;
        this.Email = Email;
    }

    public String getAddress() {
        return Address;
    }

    public String getDescription() {
        return Description;
    }

    public String getEmail() {
        return Email;
    }

    public String getName() {
        return Name;
    }
}
