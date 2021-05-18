package com.xyz.bean;

public class User {
    private String name;
    private long number;
    private int balance;
    private String city;

    public User(){

    }

    public User(String na,long nu,String ci){
        this.name = na;
        this.number = nu;
        this.balance = 0;
        this.city = ci;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String na){
        this.name = na;
    }

    public String getCity(){
        return this.city;
    }

    public void setCity(String ci){
        this.city = ci;
    }

    public int getBalance(){
        return this.balance;
    }

    public void setBalance(int ba){
        this.balance = ba;
    }

    public long getNumber(){
        return this.number;
    }

    public void setNumber(long nu){
        this.number = nu;
    }

}