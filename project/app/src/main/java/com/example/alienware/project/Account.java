package com.example.alienware.project;

public class Account {

    private String email;
    private String password;
    private String accountType;

    public Account(){

    }
    public Account(String email, String password,String accountType){
        this.email=email;
        this.password=password;
        this.accountType=accountType;

    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getAccountType() {
        return accountType;
    }
}
