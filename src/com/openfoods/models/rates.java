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
public class rates {
    private int id;
    private double usd;
    private double cdf;
    public rates(int idr,double usd,double cdf){
        this.id=idr;
        this.cdf=cdf;
        this.usd=usd;
}
    public int idRate(){
        return this.id;
    }
    public double getCDF(){
        return this.cdf;
    }
    public double getUSD(){
        return this.usd;
    }
    
}
