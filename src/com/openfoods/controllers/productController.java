/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.openfoods.controllers;

import com.openfoods.configs.IAsyncQuery;
import com.openfoods.configs.SQLDB;
import com.openfoods.models.food;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hornellama
 */
public class productController {
    private SQLDB sqldb=null;
    private IAsyncQuery iaq=null;
    private List<food>foodsList=null;
    public productController(){
        this.foodsList=new LinkedList<>();
    }
    public List<food>getListProducts() throws SQLException, ClassNotFoundException{
        sqldb=new SQLDB();
        String query="SELECT * FROM t_foods Foods JOIN t_type_food tFood ON Foods.typeFood=tFood.codeType WHERE status=1";
        sqldb.setQuery(query, new IAsyncQuery() {
            @Override
            public void getResult(ResultSet resultSet) {
                try {
                    
                    while (resultSet.next()) {
                        food _food=new food(resultSet.getInt("id"),
                                resultSet.getString("namefood"),
                                resultSet.getDouble("price"),
                                resultSet.getString("typeFood"),
                                resultSet.getInt("status"));
                        foodsList.add(_food);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(productController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        return foodsList;
    }
    public Map<String,Object> addProduct(food f) throws ClassNotFoundException, SQLException{
        final boolean isSuccess=false;
        sqldb=new SQLDB();
        String query="INSERT INTO t_foods (namefood,price,typeFood,status) VALUES ('"+f.getNameFood()+"','"+f.getPriceFood()+"','"+f.getTypeFood()+"','"+f.getStatus()+"')";
       Map<String,Object> map=new HashMap<>();
        int response=sqldb.setQueryUpdate(query);
        if (response>0) {
           map.put("status", true);
           map.put("key", response);
        }else{
            map.put("status", false);
            map.put("key", null);
        }
        
        return map;
    }
    public boolean updateProduct(food f) throws ClassNotFoundException, SQLException{
        String query="UPDATE t_foods SET namefood='"+f.getNameFood()+"',price='"+f.getPriceFood()+"',typeFood='"+f.getTypeFood()+"',status='"+f.getStatus()+"' WHERE id='"+f.getIdFood()+"'";
        SQLDB sqldb=new SQLDB();
        sqldb.setQueryUpdate(query);
        return true;
        
    }
    public boolean updateProductByCategory(String categ,double amount,String operation) throws ClassNotFoundException, SQLException{
        
        String query="";
        switch(operation){
            case "+":
               query= "UPDATE t_foods SET price=price + "+amount+" WHERE typeFood='"+categ+"'";
             break;
            default:
                query="UPDATE t_foods SET price=price - "+amount+" WHERE typeFood='"+categ+"'";
                break;
        }
        SQLDB sqldb=new SQLDB();
        sqldb.setQueryUpdate(query);
        System.out.println(query);
        return  true;
    }
}
