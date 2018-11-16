/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.openfoods.controllers;

import com.openfoods.configs.ActionType;
import com.openfoods.configs.SQLDB;
import com.openfoods.models.billing;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 *
 * @author hornellama
 */
public class billingController {
    
    private billing bl;
    private ActionType actionType;
    SQLDB sqldb;
    public Double amountBooking=0.0;
    
    public billingController(billing bl,ActionType at) throws ClassNotFoundException, SQLException{
        sqldb=new SQLDB();
        this.bl=bl;
        this.actionType=at;
    }
    public billing executeUpdateBilling() throws SQLException{
        
        LocalDate localDate=LocalDate.now();
        String dateBilling=localDate.getYear()+"/"+localDate.getMonthValue()+"/"+localDate.getDayOfMonth();
        String query="";
        Double TotalTopaid=0.0;
        switch(this.actionType){
            case ORDER:
                query="INSERT INTO t_invoices(idRate,totalPaie,servername,idclient,dateInvoice)VALUES('"+loginController.idRate+"','"+this.bl.getAmountBilling()+"','"+this.bl.getServerName()+"','"+this.bl.getIdClient()+"','"+this.bl.getDateBilling()+"')";
                int idkey=sqldb.setQueryUpdate(query);
                this.bl.setIdBilling(idkey);
                break;
                
            case BOOKING:
                if (this.bl.getAmountBilling()>this.amountBooking) {
                    TotalTopaid=this.bl.getAmountBilling()-this.amountBooking;
                }
                 query="INSERT INTO t_invoices_booking(idRate,totalPaie,servername,idclient,dateInvoice,amountPaie,totaltopaid)VALUES('"+loginController.idRate+"','"+this.bl.getAmountBilling()+"','"+this.bl.getServerName()+"','"+this.bl.getIdClient()+"','"+this.bl.getDateBilling()+"','"+this.amountBooking+"','"+TotalTopaid+"')";
                int idkey2=sqldb.setQueryUpdate(query);
                this.bl.setIdBilling(idkey2);
                break;
        }
        
        
        return this.bl;
    }
    
}
