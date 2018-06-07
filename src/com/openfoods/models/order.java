/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.openfoods.models;

import com.openfoods.configs.SQLDB;
import com.openfoods.controllers.loginController;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Stream;

/**
 *
 * @author hornellama
 */
public class order {
    
    private food _food=null;
    private int qte;
    private double amount;
    private int idUser;
    private int idInvoice;
    private String serverName;
    private SQLDB sqldb=null;
    private user uid;
    private billing bl;
    public order(food f,int qty,double amount,int iduser,int idinvoice,String serverName){
       
        this._food=f;
        this.qte=qty;
        this.amount=amount;
        this.idUser=idUser;
        this.idInvoice=idinvoice;
        this.serverName=serverName;
    }
    public food getFoood(){
        return this._food;
    }
    public int getQuantity(){
        return this.qte;
    }
    public double getAmount(){
        return this.amount;
    }
    public int getdUser(){
        return this.idUser;
    }
    public int getInvoice(){
        return this.idInvoice;
    }
    public void setUserId(int id,String name,String fullname,String pwd,String priority){
        this.uid=new user(id, name, fullname, pwd,priority);
    }
    public void setBillingOrder(String idClient,rates idRate,double amountBilling,String servername,String dateBlling){
        this.bl=new billing(idClient, idRate, amountBilling, servername,dateBlling);
    }
    public user getUserID(){
        return this.uid;
    }
    public billing getBillingOrder(){
        return this.bl;
    }
    
}
