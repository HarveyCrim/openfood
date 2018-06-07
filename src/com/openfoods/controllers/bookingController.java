/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.openfoods.controllers;

import com.openfoods.configs.IAsyncQuery;
import com.openfoods.configs.IResultQuery;
import com.openfoods.configs.OrderBillingEnum;
import com.openfoods.configs.SQLDB;
import com.openfoods.models.food;
import com.openfoods.models.order;
import com.openfoods.models.rates;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hornellama
 */ 
public class bookingController {
    private SQLDB sqldb;
     private IResultQuery iResultQuery;
    public String dt1,dt2;
    public List<order> getOrdersBilling(OrderBillingEnum obe) throws ClassNotFoundException, SQLException{
         List<order>Orders=new LinkedList<>();
         String query="";
         LocalDate ld=LocalDate.now();
         String daily=ld.getYear()+"-"+ld.getMonthValue()+"-"+ld.getDayOfMonth();
         switch(obe){
             case Daily:
                 sqldb=new SQLDB();
                 if (loginController._priority.equals("administrator")) {
                     query="SELECT * FROM t_commands_booking AS cmd JOIN t_invoices_booking AS invoices ON cmd.idInvoice=invoices.id " +
                        "JOIN t_foods AS foods ON foods.id=cmd.idfood JOIN t_type_food AS tfood ON tfood.codeType=foods.typeFood " +
                        "JOIN t_users AS users ON users.id=cmd.iduser JOIN t_rates AS rates ON rates.idRate=invoices.idRate WHERE invoices.dateInvoice='"+daily+"'";
                 }else{
                     query="SELECT * FROM t_commands_booking AS cmd JOIN t_invoices_booking AS invoices ON cmd.idInvoice=invoices.id " +
                        "JOIN t_foods AS foods ON foods.id=cmd.idfood JOIN t_type_food AS tfood ON tfood.codeType=foods.typeFood " +
                        "JOIN t_users AS users ON users.id=cmd.iduser JOIN t_rates AS rates ON rates.idRate=invoices.idRate WHERE invoices.dateInvoice='"+daily+"' AND cmd.iduser='"+loginController._idUser+"'";
                 }
                 
                 sqldb.setQuery(query, new IAsyncQuery() {
             @Override
             public void getResult(ResultSet resultSet) {
                 
                 try {                     
                     while (resultSet.next()) {
                         order o=new order(new food(
                                            resultSet.getInt("idfood"),
                                            resultSet.getString("namefood"),
                                            0.0,
                                            resultSet.getString("nameType"),
                                            1
                                            ),
                                 resultSet.getInt("qte"), 
                                 resultSet.getDouble("amount"), 
                                 resultSet.getInt("iduser"), 
                                 resultSet.getInt("idinvoice"), 
                                 resultSet.getString("serverName"));
                                 o.setUserId(resultSet.getInt("iduser"),
                                         resultSet.getString("name"), 
                                         resultSet.getString("fullname"),
                                         resultSet.getString("password"),
                                         resultSet.getString("priority"));
                                 o.setBillingOrder(
                                         resultSet.getString("idclient"),
                                         new rates(resultSet.getInt("idRate"), resultSet.getDouble("USD"), resultSet.getDouble("FC")),
                                         resultSet.getDouble("totalpaie"),
                                         resultSet.getString("servername"),
                                         resultSet.getDate("dateInvoice").toString());
                                 
                                 Orders.add(o);
                     }
                     
                     //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                 } catch (SQLException ex) {
                     Logger.getLogger(orderController.class.getName()).log(Level.SEVERE, null, ex);
                 }
             }
         });
                 break;
             case Monthly:
                            sqldb=new SQLDB();
                  String currentMonth=Integer.toString(LocalDate.now().getMonthValue());
                  //String month=
                  currentMonth=(currentMonth.length()==1?"0"+currentMonth:currentMonth);
                  System.out.println("Month:"+currentMonth);
                 if (loginController._priority.equals("administrator")) {
                     query="SELECT * FROM t_commands AS cmd JOIN t_invoices AS invoices ON cmd.idInvoice=invoices.id " +
                        "JOIN t_foods AS foods ON foods.id=cmd.idfood JOIN t_type_food AS tfood ON tfood.codeType=foods.typeFood " +
                        "JOIN t_users AS users ON users.id=cmd.iduser JOIN t_rates AS rates ON rates.idRate=invoices.idRate WHERE invoices.dateInvoice LIKE '%"+currentMonth+"%'";
                 }else{
                     query="SELECT * FROM t_commands AS cmd JOIN t_invoices AS invoices ON cmd.idInvoice=invoices.id " +
                        "JOIN t_foods AS foods ON foods.id=cmd.idfood JOIN t_type_food AS tfood ON tfood.codeType=foods.typeFood " +
                        "JOIN t_users AS users ON users.id=cmd.iduser JOIN t_rates AS rates ON rates.idRate=invoices.idRate WHERE invoices.dateInvoice LIKE '%"+currentMonth+"%' AND cmd.iduser='"+loginController._idUser+"'";
                 }
                 
                 sqldb.setQuery(query, new IAsyncQuery() {
             @Override
             public void getResult(ResultSet resultSet) {
                 
                 try {                     
                     while (resultSet.next()) {
                         order o=new order(new food(
                                            resultSet.getInt("idfood"),
                                            resultSet.getString("namefood"),
                                            0.0,
                                            resultSet.getString("nameType"),
                                            1
                                            ),
                                 resultSet.getInt("qte"), 
                                 resultSet.getDouble("amount"), 
                                 resultSet.getInt("iduser"), 
                                 resultSet.getInt("idinvoice"), 
                                 resultSet.getString("serverName"));
                                 o.setUserId(resultSet.getInt("iduser"),
                                         resultSet.getString("name"), 
                                         resultSet.getString("fullname"),
                                         resultSet.getString("password"),
                                         resultSet.getString("priority"));
                                 o.setBillingOrder(
                                         resultSet.getString("idclient"),
                                         new rates(resultSet.getInt("idRate"), resultSet.getDouble("USD"), resultSet.getDouble("FC")),
                                         resultSet.getDouble("totalpaie"),
                                         resultSet.getString("servername"),
                                         resultSet.getDate("dateInvoice").toString());
                                 
                                 Orders.add(o);
                     }
                     
                     //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                 } catch (SQLException ex) {
                     Logger.getLogger(orderController.class.getName()).log(Level.SEVERE, null, ex);
                 }
             }
         });
                 break;
             case Weekly:
                  sqldb=new SQLDB();
                  LocalDate endDate=LocalDate.now();
                  LocalDate beginDate=endDate.minusDays(endDate.getDayOfMonth());
                 // System.out.println("Begin:"+beginDate.toString()+" , end:"+endDate.toString());
                 if (loginController._priority.equals("administrator")) {
                     query="SELECT * FROM t_commands AS cmd JOIN t_invoices AS invoices ON cmd.idInvoice=invoices.id " +
                        "JOIN t_foods AS foods ON foods.id=cmd.idfood JOIN t_type_food AS tfood ON tfood.codeType=foods.typeFood " +
                        "JOIN t_users AS users ON users.id=cmd.iduser JOIN t_rates AS rates ON rates.idRate=invoices.idRate WHERE invoices.dateInvoice BETWEEN '"+beginDate.toString()+"' AND '"+endDate.toString()+"'";
                 }else{
                     query="SELECT * FROM t_commands AS cmd JOIN t_invoices AS invoices ON cmd.idInvoice=invoices.id " +
                        "JOIN t_foods AS foods ON foods.id=cmd.idfood JOIN t_type_food AS tfood ON tfood.codeType=foods.typeFood " +
                        "JOIN t_users AS users ON users.id=cmd.iduser JOIN t_rates AS rates ON rates.idRate=invoices.idRate WHERE invoices.dateInvoice BETWEEN '"+beginDate.toString()+"' AND '"+endDate.toString()+"' AND cmd.iduser='"+loginController._idUser+"'";
                 }
                 
                 sqldb.setQuery(query, new IAsyncQuery() {
             @Override
             public void getResult(ResultSet resultSet) {
                 
                 try {                     
                     while (resultSet.next()) {
                         order o=new order(new food(
                                            resultSet.getInt("idfood"),
                                            resultSet.getString("namefood"),
                                            0.0,
                                            resultSet.getString("nameType"),
                                            1
                                            ),
                                 resultSet.getInt("qte"), 
                                 resultSet.getDouble("amount"), 
                                 resultSet.getInt("iduser"), 
                                 resultSet.getInt("idinvoice"), 
                                 resultSet.getString("serverName"));
                                 o.setUserId(resultSet.getInt("iduser"),
                                         resultSet.getString("name"), 
                                         resultSet.getString("fullname"),
                                         resultSet.getString("password"),
                                         resultSet.getString("priority"));
                                 o.setBillingOrder(
                                         resultSet.getString("idclient"),
                                         new rates(resultSet.getInt("idRate"), resultSet.getDouble("USD"), resultSet.getDouble("FC")),
                                         resultSet.getDouble("totalpaie"),
                                         resultSet.getString("servername"),
                                         resultSet.getDate("dateInvoice").toString());
                                 
                                 Orders.add(o);
                     }
                     
                     //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                 } catch (SQLException ex) {
                     Logger.getLogger(orderController.class.getName()).log(Level.SEVERE, null, ex);
                 }
             }
         });
                 break;
             case FilterDate:
                 sqldb=new SQLDB();
                
                 // System.out.println("Begin:"+beginDate.toString()+" , end:"+endDate.toString());
                 if (loginController._priority.equals("administrator")) {
                     query="SELECT * FROM t_commands AS cmd JOIN t_invoices AS invoices ON cmd.idInvoice=invoices.id " +
                        "JOIN t_foods AS foods ON foods.id=cmd.idfood JOIN t_type_food AS tfood ON tfood.codeType=foods.typeFood " +
                        "JOIN t_users AS users ON users.id=cmd.iduser JOIN t_rates AS rates ON rates.idRate=invoices.idRate WHERE invoices.dateInvoice BETWEEN '"+dt1+"' AND '"+dt2+"'";
                 }else{
                     query="SELECT * FROM t_commands AS cmd JOIN t_invoices AS invoices ON cmd.idInvoice=invoices.id " +
                        "JOIN t_foods AS foods ON foods.id=cmd.idfood JOIN t_type_food AS tfood ON tfood.codeType=foods.typeFood " +
                        "JOIN t_users AS users ON users.id=cmd.iduser JOIN t_rates AS rates ON rates.idRate=invoices.idRate WHERE invoices.dateInvoice BETWEEN '"+dt1+"' AND '"+dt2+"' AND cmd.iduser='"+loginController._idUser+"'";
                 }
                 
                 sqldb.setQuery(query, new IAsyncQuery() {
             @Override
             public void getResult(ResultSet resultSet) {
                 
                 try {                     
                     while (resultSet.next()) {
                         order o=new order(new food(
                                            resultSet.getInt("idfood"),
                                            resultSet.getString("namefood"),
                                            0.0,
                                            resultSet.getString("nameType"),
                                            1
                                            ),
                                 resultSet.getInt("qte"), 
                                 resultSet.getDouble("amount"), 
                                 resultSet.getInt("iduser"), 
                                 resultSet.getInt("idinvoice"), 
                                 resultSet.getString("serverName"));
                                 o.setUserId(resultSet.getInt("iduser"),
                                         resultSet.getString("name"), 
                                         resultSet.getString("fullname"),
                                         resultSet.getString("password"),
                                         resultSet.getString("priority"));
                                 o.setBillingOrder(
                                         resultSet.getString("idclient"),
                                         new rates(resultSet.getInt("idRate"), resultSet.getDouble("USD"), resultSet.getDouble("FC")),
                                         resultSet.getDouble("totalpaie"),
                                         resultSet.getString("servername"),
                                         resultSet.getDate("dateInvoice").toString());
                                 
                                 Orders.add(o);
                     }
                     
                     //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                 } catch (SQLException ex) {
                     Logger.getLogger(orderController.class.getName()).log(Level.SEVERE, null, ex);
                 }
             }
         });
                 break;
         }
         return Orders;
     }
     
     
}
