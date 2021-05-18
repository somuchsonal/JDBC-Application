package com.xyz.service;

import com.xyz.bean.User;
import com.xyz.dao.DaoCl;

public class ServiceCl implements ServiceIF{

    DaoCl dao = new DaoCl();

    @Override
    public User createUser(String name,long number,String city,String pass){
        User present= new User();
        if(dao.verify(number)) return present;

        dao.create(name,number,city,pass);
        present = new User(name, number, city);
        return present;
    }

    @Override
    public User login(long number, String pass){
        User present = dao.login(number, pass);
        return present;
    }

    @Override
    public boolean verifydeposit(int dep){
        //For verifying actual payment
        return true;
    }

    @Override
    public void deposit(User present, int dep){
        if(verifydeposit(dep)) dao.updateBalance(present.getNumber(), present.getBalance()+dep);
        dao.addTransac(present.getNumber(),1000000000, dep);
    }

    public void giveoutmoney(int wit){
        // to physically give the money to user
    }

    @Override
    public void withdraw(User present, int wit){
        dao.updateBalance(present.getNumber(),present.getBalance()-wit);
        dao.addTransac(1000000000, present.getNumber(), wit);
        giveoutmoney(wit);
    }

    @Override
    public void transfer(User present, long number, int tran){
        if(dao.verify(number)){
            dao.updateBalance(present.getNumber(), present.getBalance()- tran);
            dao.updateBalance(number, dao.getBalance(number) +tran);
            dao.addTransac(present.getNumber(), number, tran);
        }else{
            System.out.println("The account you want to transfer money to, doesn't exist");
        }
    }

    @Override
    public void getTransactions(int n, User present){
        dao.getTransactions(n,present.getNumber());
    }
}