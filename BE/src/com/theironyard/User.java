package com.theironyard;

/**
 * Created by alhanger on 11/5/15.
 */
public class User {
    String username;
    String password;
    boolean gender;
    String location;
    int age;
    int stereotypeId;
    Stereotype stereotype;

    public User() {

    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isGender() {
        return gender;
    }

    public String getLocation() {
        return location;
    }

    public int getAge() {
        return age;
    }

    public int getStereotypeId() {
        return stereotypeId;
    }

    public Stereotype getStereotype() {
        return stereotype;
    }
}
