package com.theironyard;

/**
 * Created by alhanger on 11/5/15.
 */
public class User {
    String username;
    String password;
    String gender;
    String location;
    int age;
    Stereotype stereotype;
    String picURL;

    public User() {

    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String isGender() {
        return gender;
    }

    public String getLocation() {
        return location;
    }

    public int getAge() {
        return age;
    }

    public Stereotype getStereotype() {
        return stereotype;
    }

    public String getPicURL() {
        return picURL;
    }
}
