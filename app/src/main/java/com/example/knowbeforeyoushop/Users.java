package com.example.knowbeforeyoushop;

public class Users {
    public String uid;
    public String name;
    public String email;
    public String password;
    public int usertype;



    public Users(){

    }

    public Users(String uid, String name, String email, String password,int usertype) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.password = password;
        this.usertype=usertype;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUsertype() {
        return usertype;
    }

    public void setUsertype(int usertype) {
        this.usertype = usertype;
    }
}
