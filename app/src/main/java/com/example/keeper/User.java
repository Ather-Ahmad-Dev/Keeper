package com.example.keeper;

public class User {
    private String userName;

    public User(String userName){
        this.userName = userName;
    }
    public User(){

    }
    public void setUserName(String userName){
        this.userName = userName;
    }
    public String getUserName(){
        return userName;
    }
}
