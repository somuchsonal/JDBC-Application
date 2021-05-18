package com.xyz.service;

import com.xyz.bean.User;

public interface ServiceIF {
    public User createUser(String name,long number,String city,String pass);
    public User login(long number, String pass);
    public boolean verifydeposit(int dep);
    public void deposit(User present, int dep);
    public void withdraw(User present, int wit);
    public void transfer(User present, long number, int tran);
    public void getTransactions(int n, User present);
}