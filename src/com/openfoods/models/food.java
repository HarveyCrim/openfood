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
public class food {
    
    private int idFood;
    private String foodName;
    private double priceFood;
    private String typeFood;
    private int status;
    public food(int idFood,String nameFood,double priceFood,String typeFood,int status){
        this.idFood=idFood;
        this.foodName=nameFood;
        this.priceFood=priceFood;
        this.typeFood=typeFood;
        this.status=status;
    }
    
    public int getIdFood(){
        return this.idFood;
    }
    public String getNameFood(){
        return this.foodName;
    }
    public double getPriceFood(){
        return this.priceFood;
    }
    public String getTypeFood(){
        return this.typeFood;
    }
    public int getStatus(){
        return  this.status;
    }
}
