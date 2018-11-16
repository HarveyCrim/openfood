/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.openfoods.configs;

import java.sql.ResultSet;

/**
 *
 * @author hornellama
 */
public interface IAsyncQuery {
    public void getResult(ResultSet resultSet);
}
