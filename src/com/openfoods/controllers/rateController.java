/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.openfoods.controllers;

import com.openfoods.configs.IAsyncQuery;
import com.openfoods.configs.SQLDB;
import com.openfoods.models.rates;
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
public class rateController {
    
    public int addRate(rates rt) throws ClassNotFoundException, SQLException{
        String query="INSERT INTO t_rates (USD,FC) VALUES ('"+rt.getUSD()+"','"+rt.getCDF()+"')";
        SQLDB sqldb=new SQLDB();
        int i=sqldb.setQueryUpdate(query);
        String queryUpdate ="UPDATE t_users SET rate='"+i+"'";
        sqldb.setQueryUpdate(queryUpdate);
        return i;
    }
    public List<rates> getRates() throws ClassNotFoundException, SQLException{
        List<rates> ratesList=new LinkedList<>();
        String query="SELECT * FROM t_rates";
        SQLDB sqldb=new SQLDB();
        sqldb.setQuery(query, new IAsyncQuery() {
            @Override
            public void getResult(ResultSet resultSet) {
                try {
                    while (resultSet.next()) {                        
                        rates rt=new rates
                        (
                                resultSet.getInt("idRate"),
                                1.0,
                                resultSet.getDouble("FC")
                        );
                        ratesList.add(rt);
                    }
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                } catch (SQLException ex) {
                    Logger.getLogger(rateController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        return ratesList;
    }
}
