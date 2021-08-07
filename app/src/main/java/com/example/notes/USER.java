package com.example.notes;

public class USER {
    String username;
    String email;
    String phoneNo;

    public USER(String username, String email, String phoneNo){
        this.username = username;
        this.email = email;
        this.phoneNo = phoneNo;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

}
