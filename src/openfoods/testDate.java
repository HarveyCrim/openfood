/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openfoods;

import java.util.Calendar;

/**
 *
 * @author hornellama
 */
public class testDate {
    public static void main(String[] args) {
        System.out.println(Calendar.getInstance().get(Calendar.YEAR)+"-"+Calendar.getInstance().get(Calendar.MONTH)+"-"+Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
    }
}
