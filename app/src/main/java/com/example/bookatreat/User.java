package com.example.bookatreat;

public class User {

    private static final String TAG = "LoginActivity";

    private String mName, mLastName, mEmail, mAddress, mGender, mPhoneNumber;
    private String mNameLastName, mRestaurantDescription, mRestaurantType, mRestaurantPrice;


    //Constructors
    User (String name, String lastName, String email, String address, String gender, String phoneNumber) {
        this.mName = name;
        this.mLastName = lastName;
        this.mEmail = email;
        this.mAddress = address;
        this.mGender = gender;
        this.mPhoneNumber = phoneNumber;
        this.mNameLastName = name + " " + lastName;
    }

    User (String restaurantName, String restaurantDescription, String restaurantEmail, String restaurantAddress, String restaurantType, String restaurantPhoneNumber, String restaurantPriceRange) {
        this.mName = restaurantName;
        this.mRestaurantDescription = restaurantDescription;
        this.mEmail = restaurantEmail;
        this.mAddress = restaurantAddress;
        this.mRestaurantType = restaurantType;
        this.mPhoneNumber = restaurantPhoneNumber;
        this.mRestaurantPrice = restaurantPriceRange;
    }

    //Getters & Setters
    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        mLastName = lastName;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public String getGender() {
        return mGender;
    }

    public void setGender(String gender) {
        mGender = gender;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        mPhoneNumber = phoneNumber;
    }

    public String getNameLastName() { return mNameLastName; }

}
