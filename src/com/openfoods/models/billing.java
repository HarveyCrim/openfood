/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.openfoods.models;

import java.time.LocalDate;

/**
 *
 * @author hornellama
 */
public class billing {
    
    private int idBilling;
    private String idClient;
    private int idRate;
    private double amountBilling;
    private String servername;
    private String dateBilling;
    private rates rateBilling;
    
    public billing(String idClient, rates idRate,double amountBilling,String serverName,String dateBilling){
        this.idBilling=idBilling;
        this.idClient=idClient;
        this.rateBilling=idRate;
        this.amountBilling=amountBilling;
        this.servername=serverName;
        this.dateBilling=dateBilling;
    }
    public void setIdBilling(int id){
        this.idBilling=id;
    }
    public int getIdBilling(){
        return this.idBilling;
    }
    public String getIdClient(){
        return this.idClient;
    }
    public rates getIdRate(){
        return this.rateBilling;
    }
    public double getAmountBilling(){
        return this.amountBilling;
    }
    public String getServerName(){
        return this.servername;
    }
    public String getDateBilling(){
        return this.dateBilling;
    }
    
}
