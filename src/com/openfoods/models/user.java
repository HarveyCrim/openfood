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
public class user {
    private int idUser=0;
    private String name;
    private String fullname;
    private String password;
    private String priority;
    
    public user(int id,String name, String fullname,String password,String priority){
        this.idUser=id;
        this.name=name;
        this.fullname=fullname;
        this.password=password;
        this.priority=priority;
    }
    
    public int getIdUser(){
        return this.idUser;
    }
    public String getName(){
        return this.name;
    }
    public String getFullName(){
        return this.fullname;
    }
    public String getPassWord(){
        return this.password;
    }
    public String getPriority(){
        return this.priority;
    }
}
