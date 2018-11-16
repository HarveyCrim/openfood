/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.openfoods.controllers;

import com.openfoods.configs.IAsyncQuery;
import com.openfoods.configs.SQLDB;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hornellama
 */
public class clientController {
    private SQLDB sqldb;

    public clientController() throws ClassNotFoundException, SQLException {
        this.sqldb = new SQLDB();
    }
    public List<String> getListClients() throws SQLException{
        List<String> lst=new LinkedList<>();
        sqldb.setQuery("SELECT * FROM t_table_client", new IAsyncQuery() {
            @Override
            public void getResult(ResultSet resultSet) {
                try {
                    while(resultSet.next()){
                        
                    String s=resultSet.getString("nametable");
                    lst.add(s);
                    }
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                } catch (SQLException ex) {
                    Logger.getLogger(clientController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        return lst;
    }
   
    
}
