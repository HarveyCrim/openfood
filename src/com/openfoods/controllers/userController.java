/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.openfoods.controllers;

import com.openfoods.configs.IAsyncQuery;
import com.openfoods.configs.SQLDB;
import com.openfoods.models.user;
import java.sql.ResultSet;
import java.sql.SQLException;
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
public class userController {
    
    public int addUser(user uid) throws ClassNotFoundException, SQLException{
       // Map<String,Object> map=new HashMap<String,Object>();
        SQLDB sqldb=new SQLDB();
        String query="INSERT INTO t_users (name,fullname,password,priority,rate) VALUES('"+uid.getName()+"','"+uid.getFullName()+"','"+uid.getPassWord()+"','"+uid.getPriority()+"','"+loginController.idRate+"')";
        int i=sqldb.setQueryUpdate(query);
        return i;
    }
    public List<user> getUsers() throws ClassNotFoundException, SQLException{
        List<user> users=new LinkedList<>();
        String query="SELECT * FROM t_users";
        SQLDB sqldb=new SQLDB();
        sqldb.setQuery(query, new IAsyncQuery() {
            @Override
            public void getResult(ResultSet resultSet) {
                try {
                    while(resultSet.next()){
                        user uid=new user(resultSet.getInt("id"),resultSet.getString("name"),resultSet.getString("fullname"),resultSet.getString("password"), resultSet.getString("priority"));
                                            //System.out.println("Id:"+uid.getIdUser());

                        users.add(uid);
                    }
                    // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                } catch (SQLException ex) {
                    Logger.getLogger(userController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        return users;
    }
    
    public int updateUser(user uid) throws ClassNotFoundException, SQLException{
        String query="UPDATE t_users SET name='"+uid.getName()+"',fullname='"+uid.getFullName()+"',priority='"+uid.getPriority()+"' WHERE id='"+uid.getIdUser()+"'";
        SQLDB sqldb=new SQLDB();
        return sqldb.setQueryUpdate(query);
    }
}
