/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.openfoods.controllers;

import com.openfoods.configs.ActionType;
import com.openfoods.configs.SQLDB;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 *
 * @author hornellama
 */
public class trashController {
    private SQLDB sqldb=null;
    public static List<Map<String,Object>> arrayData;
    public boolean setDataInTrash(ActionType actionType) throws ClassNotFoundException, SQLException{
        LocalDate date=LocalDate.now();
        sqldb=new SQLDB();
        String query="";
        switch(actionType){
            case BOOKING:
                for (Map<String, Object> map : arrayData) {
                    query="INSERT INTO t_trash(invoiceid,datecmd,qte,idfood,foodname,price,datetrash,type) VALUES ('"+map.get("invoiceid")+"','"+map.get("datecmd")+"','"+map.get("qte")+"','"+map.get("idprod")+"','"+map.get("nameprod")+"','"+map.get("price")+"','"+date.toString()+"','BOOKING')";
                    sqldb.setQueryUpdate(query);
                }
                 for (Map<String, Object> map : arrayData) {
                    String idInvoice=map.get("invoiceid").toString();
                    query="DELETE FROM t_commands_booking WHERE idInvoice="+idInvoice;
                    sqldb.setQueryUpdate(query);
                }
                
                for (Map<String, Object> map : arrayData) {
                    String idInvoice=map.get("invoiceid").toString();
                    query="DELETE FROM t_invoices_booking WHERE id="+idInvoice;
                    sqldb.setQueryUpdate(query);
                }
                
                break;
                
            case ORDER:
                for (Map<String, Object> map : arrayData) {
                    query="INSERT INTO t_trash(invoiceid,datecmd,qte,idfood,foodname,price,datetrash,type) VALUES ('"+map.get("invoiceid")+"','"+map.get("datecmd")+"','"+map.get("qte")+"','"+map.get("idprod")+"','"+map.get("nameprod")+"','"+map.get("price")+"','"+date.toString()+"','ORDER')";
                    sqldb.setQueryUpdate(query);
                }
                for (Map<String, Object> map : arrayData) {
                    String idInvoice=map.get("invoiceid").toString();
                    query="DELETE FROM t_commands WHERE idInvoice="+idInvoice;
                    sqldb.setQueryUpdate(query);
                }
                
                for (Map<String, Object> map : arrayData) {
                    String idInvoice=map.get("invoiceid").toString();
                    query="DELETE FROM t_invoices WHERE id="+idInvoice;
                    sqldb.setQueryUpdate(query);
                }
                
                break;
        }
        return true;
    }
    
}
