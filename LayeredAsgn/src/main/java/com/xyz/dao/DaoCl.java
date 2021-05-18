package com.xyz.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
//import java.sql.Statement;
import java.sql.SQLException;

import com.xyz.bean.User;

public class DaoCl implements DaoIF{
    static Connection dbCon;
    PreparedStatement pstmt;

    public DaoCl(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            dbCon =DriverManager.getConnection("jdbc:mysql://localhost:3306/xyzbank", "root", "");
        } catch (ClassNotFoundException e) {
            System.out.println("Unable to load the driver..." + e);
        } catch (SQLException e) {
            System.out.println("Unable to establish connection" + e);
        }
    }


    @Override
    public void create(String name,long number,String city,String pass){
        String qry = "insert into users (Mobile, Name, Password, City, Balance) values (?,?,?,?,0)";
        try {
			pstmt = dbCon.prepareStatement(qry);
			
            pstmt.setLong(1, number);
            pstmt.setString(2, name);
            pstmt.setString(3, pass);
            pstmt.setString(4, city);

            if(pstmt.executeUpdate() > 0)   System.out.println("Your account is created, congrats. But, it's still empty, deposit some money!");
            else System.out.println("Account creation failed");
            
		} catch (SQLException e) {
			System.out.println("Issues while getting a reference to the PreparedStatement object: " + e);
		}
    }

    @Override
    public User login(long number, String pass){
        User present = new User();
        String qry = "select Name, City, Balance from users where Mobile = ? and Password =?";
        try{
            pstmt = dbCon.prepareStatement(qry);

            pstmt.setLong(1, number);
            pstmt.setString(2, pass);

            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                present.setName(rs.getString(1));
                present.setCity(rs.getString(2));
                present.setBalance(rs.getInt(3));
            }

            present.setNumber(number);
            
        }catch(SQLException e){
            System.out.println("Error while executing query"+e);
        }
        return present;
    }
    
    @Override
    public void updateBalance(long number, int updated_bal){
        String qry = "update users set Balance=? where Mobile=?";
        try {
            pstmt = dbCon.prepareStatement(qry);

            pstmt.setInt(1, updated_bal);
            pstmt.setLong(2, number);

            if(pstmt.executeUpdate()>0) System.out.println("Transaction successfull");
            else System.out.println("Transaction failed");

        } catch (SQLException e) {
            System.out.println("error in depositing: "+e);
        }
    }

    @Override
    public int getBalance(long number) {
        int balance=0;
        String qry = "select Balance from users where Mobile = ? ";
        try{
            pstmt = dbCon.prepareStatement(qry);

            pstmt.setLong(1, number);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                balance = rs.getInt(1);
            }
        }catch(SQLException e){
            System.out.println("Error while executing query get-Balance: "+e);
        }
        return balance;
    }

    @Override
    public boolean verify(long number){
        int k=0;
        String qry = "select Name from users where Mobile = ? ";
        try{
            pstmt = dbCon.prepareStatement(qry);

            pstmt.setLong(1, number);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                k++;
            }
        }catch(SQLException e){
            System.out.println("Error while executing query get-Balance: "+e);
        }
        if (k>0) return true;
        else return false;
    }

    @Override
    public void getTransactions(int n,long number){
        String qry = "select * from transactions where TFrom = ? or TTo = ? ";
        int k=0;
        try{
            pstmt = dbCon.prepareStatement(qry);

            pstmt.setLong(1, number);
            pstmt.setLong(2, number);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()&&k<n){
                k++;
                System.out.println("Transaction ID: "+Integer.toString(rs.getInt(1))+"; From: "+Long.toString(rs.getLong(2))+"; To: "+Long.toString(rs.getLong(3))+"; Amount: "+Integer.toString(rs.getInt(4)));
            }
            
        }catch(SQLException e){
            System.out.println("Error while executing query get-Balance: "+e);
        }

    }

    @Override
    public void addTransac(long from, long to, int amount){
        String qry = "insert into transactions (TFrom, TTo, Amount) values (?,?,?)";
        try {
			pstmt = dbCon.prepareStatement(qry);
			
            pstmt.setLong(1, from);
            pstmt.setLong(2, to);
            pstmt.setInt(3, amount);

            if(pstmt.executeUpdate() > 0)   System.out.println("Transaction added");
            else System.out.println("Couldn't add transaction");
            
		} catch (SQLException e) {
			System.out.println("Issues while getting a reference to the PreparedStatement object: " + e);
		}
    }
}