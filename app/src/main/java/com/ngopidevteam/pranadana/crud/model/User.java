package com.ngopidevteam.pranadana.crud.model;

public class User {
    String id;
    String email;
    String pass;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public User() {
    }

    public User(String id, String email, String pass) {
        this.id = id;
        this.email = email;
        this.pass = pass;
    }
}
