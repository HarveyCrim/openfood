/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.openfoods.models;

/**
 *
 * @author hornellama
 */
public class booking {

    public int getIdBooking() {
        return idBooking;
    }

    public void setIdBooking(int idBooking) {
        this.idBooking = idBooking;
    }
    private int idBooking;

    public String getIdClient() {
        return idClient;
    }

    public int getIdRate() {
        return idRate;
    }

    public double getAmountBooking() {
        return amountBooking;
    }

    public String getServername() {
        return servername;
    }

    public String getDateBooking() {
        return dateBooking;
    }

    public rates getRateBooking() {
        return rateBooking;
    }
    private String idClient;
    private int idRate;
    private double amountBooking;
    private String servername;
    private String dateBooking;
    private rates rateBooking;
    
    public booking(String idClient, rates idRate,double amountBooking,String serverName,String dateBooking){
        this.idBooking=idBooking;
        this.idClient=idClient;
        this.rateBooking=idRate;
        this.amountBooking=amountBooking;
        this.servername=serverName;
        this.dateBooking=dateBooking;
    }
    
}
