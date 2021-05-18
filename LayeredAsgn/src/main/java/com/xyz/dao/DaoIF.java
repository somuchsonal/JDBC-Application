package com.xyz.dao;

import com.xyz.bean.User;

public interface DaoIF {
    public void create(String name,long number,String city,String pass);
    public User login(long number, String pass);
    public void updateBalance(long number, int updated_bal);
    public boolean verify(long number);
    public void getTransactions(int n, long number);
    public int getBalance(long number);
    public void addTransac(long from, long to, int amount);
}