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
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author hornellama
 */
public class loginController {
    public static int _idUser;
    public static String _usernameLogger;
    private String _userName;
    private String _PassWord;
    public static String _priority;
    private SQLDB sqldb;
    private static boolean isData=false;
    public static double taux=0.0;
    public static List<food>listFoods;
    public static List<String>listClients;
    public static int idRate=0;
    public void setUserName(String username){
        this._userName=username;
    }
    public void setPassWord(String pwd){
        this._PassWord=pwd;
    }
    public boolean getResponseLogging() throws SQLException, ClassNotFoundException{
        sqldb=new SQLDB();
        String query="SELECT * FROM t_users JOIN t_rates ON rate=idRate WHERE name='"+this._userName+"' AND password='"+this._PassWord+"'";
        sqldb.setQuery(query, new IAsyncQuery() {
            @Override
            public void getResult(ResultSet resultSet) {
                try {
                    
                    while (resultSet.next()) {
                        _idUser=resultSet.getInt("id");
                        _priority=resultSet.getString("priority");
                        _usernameLogger=resultSet.getString("fullname");
                        taux=resultSet.getDouble("FC");
                        idRate=resultSet.getInt("idRate");
                        loginController.isData=true;
                    }
                    if (loginController.isData) {
                        loginController.listFoods=new productController().getListProducts();
                        loginController.listClients=new clientController().getListClients();
                        
                        //JOptionPane.showMessageDialog(null, "Szie foods:"+loginController.listFoods.size());
                    }
                  
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                } catch (SQLException ex) {
                    Logger.getLogger(loginController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(loginController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        return loginController.isData;
    }
   
    
}
