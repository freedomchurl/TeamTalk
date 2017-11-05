package com.example.churl.teamproject_app;

/**
 * Created by churl on 2017-11-05.
 */

public class User {
    private String name;
    private String phonenum;
    private String email;

    User(String name, String num, String email)
    {
        this.name = name;
        this.phonenum = num;
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
