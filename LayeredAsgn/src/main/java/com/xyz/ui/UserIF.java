package com.xyz.ui;

import java.util.Objects;
import java.util.Scanner;
import com.xyz.bean.User;
import com.xyz.service.ServiceCl;
import com.xyz.service.ServiceIF;

public class UserIF {

    static User present;
    //Scanner scan;
	
	public static void main(String[] args) {

        System.out.println("Enter a number: 1->Create Account, 2->Login, 3->Deposit Money, 4->Withdraw Money, 5->Transfer Money, 6-> Get Account Details, 0-> Exit ");
        Scanner scan1 = new Scanner(System.in);	
        int  input = scan1.nextInt();
        scan1.nextLine();
        
        UserIF session = new UserIF();
        present = new User();
        
        while(input!=0){
            if(input == 1)          session.create();
            else if(input == 2)     session.login();
            else if(input == 3)     session.deposit();
            else if(input == 4)     session.withdraw();
            else if(input == 5)     session.transfer();
            else if(input == 6)     session.getdetails();
            else        System.out.println("Invalid input, retry!");
            
            System.out.println("Enter a number: 1->Create Account, 2->Login, 3->Deposit Money, 4->Withdraw Money, 5->Transfer Money, 6-> Get Account Details, 0-> Logout and Exit ");
            input = scan1.nextInt();
            scan1.nextLine();
        }

        System.out.println("Good Bye! See you again! :D");
        scan1.close();
	}
    
    
    void create(){
        Scanner scan = new Scanner(System.in);
        
        System.out.println("Enter Name");
        String name = scan.nextLine();
        
        System.out.println("Enter Number");
        long num = scan.nextLong();
        scan.nextLine();

        System.out.println("Enter Password");
        String pass = scan.nextLine();
        
        System.out.println("Enter City");
        String city = scan.nextLine();
        
        //create user
        ServiceIF service = new ServiceCl();
        present = service.createUser(name,num,city,pass);

        //user already present
        if(Objects.equals(present.getName(),null)){
            System.out.println("User already present, please login instead!");
            // scan.close();
            return;
        }

        //System.out.println("Congrats, your account is created, but it's empty! Go ahead, deposit some money!");
        // scan.close();
    }


    void login(){
        //login check
        if(!Objects.equals(present.getName(),null)){
            System.out.println(present.getName()+" is already logged in!");
            return;
        }

        Scanner scan = new Scanner(System.in);
        
        System.out.println("Enter Number");
        long num = scan.nextLong();
        scan.nextLine();

        System.out.println("Enter Password");
        String pass = scan.nextLine();
        // scan.close();

        ServiceIF service = new ServiceCl();

        //login
        present = service.login(num,pass);
        
        //wrong id password prompt
        if(Objects.equals(present.getName(),null)){
            System.out.println("Incorrect user or password");
            return;
        }
        
        //low balance prompt
        if(present.getBalance()<500) System.out.println("You have low balance, please deposit money!");

        System.out.println("Congrats, you have logged in! Happy Banking!");
        
    }


    void deposit(){
        //login check
        if(Objects.equals(present.getName(),null)){
            System.out.println("Please log in to your account to proceed!");
            return;
        }

        Scanner scan = new Scanner(System.in);
        
        System.out.println("Enter Amount to be deposited");
        int dep = scan.nextInt();
        scan.nextLine();
     
        ServiceIF service = new ServiceCl();

        //deposit money
        service.deposit(present,dep);
        present.setBalance(present.getBalance()+dep);

        //low balance prompt
        if(present.getBalance()<500) System.out.println("You still have low balance, please deposit more money!");

        // scan.close();
    }


    void withdraw(){
        //check login
        if(Objects.equals(present.getName(),null)){
            System.out.println("Please log in to your account to proceed!");
            return;
        }

        Scanner scan = new Scanner(System.in);
        
        System.out.println("Enter Amount to be withdrawn");
        int wit = scan.nextInt();
        scan.nextLine();
        // scan.close();
        //check if enough amount is there
        if(present.getBalance()<wit){
            System.out.println("Insufficient balance");
            return;
        }

        //withdraw
        ServiceIF service = new ServiceCl();
        service.withdraw(present,wit);

        present.setBalance(present.getBalance()-wit);

        //low balance prompt
        if(present.getBalance()<500) System.out.println("You have low balance, please deposit money!");

    }


    void transfer(){
        //check login
        if(Objects.equals(present.getName(),null)){
            System.out.println("Please log in to your account to proceed!");
            return;
        }
        Scanner scan = new Scanner(System.in);

        System.out.println("Enter Account number to which money is to be transferred");
        long num = scan.nextLong();
        scan.nextLine();

        System.out.println("Enter Amount to be transferred");
        int tran = scan.nextInt();
        scan.nextLine();
        // scan.close();

        ServiceIF service = new ServiceCl();


        //check if enough amount exists
        if(present.getBalance()<tran){
            System.out.println("Insufficient balance");
            return;
        } 

        //transfer
        service.transfer(present,num,tran);
        present.setBalance(present.getBalance()-tran);

        //low balance prompt
        if(present.getBalance()<500) System.out.println("You have low balance, please deposit money!");

        

    }


    void getdetails(){
        //check login
        if(Objects.equals(present.getName(),null)){
            System.out.println("Please log in to your account to proceed!");
            return;
        }

        Scanner scan = new Scanner(System.in);

        System.out.println("Enter a number to get respective details: 1-> balance, 2-> All transactions, 3-> Last five transactions, 4-> User details");
        int in = scan.nextInt();
        scan.nextLine();

        ServiceIF service = new ServiceCl();

        //get details
        if(in==1) {
            int bt = present.getBalance();
            System.out.println("Your account balance is:"+Integer.toString(bt));
            if(bt<500) System.out.println("You have low balance, please deposit money!");
        }
        else if(in ==2){
            service.getTransactions(Integer.MAX_VALUE,present);
        }
        else if(in ==3){
            service.getTransactions(5,present);
        }
        else if(in==4){
            System.out.println("Name: "+present.getName());
            System.out.println("Number: "+ Long.toString(present.getNumber()));
            System.out.println("City: "+present.getCity());
            int bat = present.getBalance();
            System.out.println("Your account balance is:"+Integer.toString(bat));
            if(bat<500) System.out.println("You have low balance, please deposit money!");
        }
        else{
            System.out.println("Invalid number entered!");
        }
        // scan.close();
    }
}