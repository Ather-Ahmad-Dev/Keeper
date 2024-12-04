package com.example.keeper;

import java.util.ArrayList;

public class UserManager {
    private User user;

    public UserManager() {
//        this.user = new User();
    }

    public void addUser(User newUser) {
        user = newUser;
    }

    public void setUserName(String newUserName) {
        if (user == null){
            user = new User();
        }
        user.setUserName(newUserName);
    }

    public String getUserName() {
        if (user == null){
            return "";
        }
        return user.getUserName();
    }
}
