/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openfoods;

import com.openfoods.controllers.loginController;
import com.openfoods.controllers.productController;
import com.openfoods.models.food;
import forms.LoginForm;
import forms.flashscreen;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author hornellama
 */
public class OpenFoods {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        // TODO code application logic here
       // String s=System.getProperty("user.dir");
       // JOptionPane.showMessageDialog(null, s);

        //System.out.println("Month:"+new java.util.Date().toLocaleString().split(" ")[1]);
  flashscreen fscreen=new flashscreen();
        fscreen.setLocationRelativeTo(null);
        String pathIcon=System.getProperty("user.dir")+"/src/images/icon.png";
        ImageIcon icon=new ImageIcon(pathIcon);
        fscreen.setVisible(true);
                System.out.println("Je suis dans le Thread:"+Thread.currentThread().getName());
//        
    Runnable run=new Runnable() {
            @Override
            public void run() {
                try {
                   
                    System.out.println("Je usis dans le thread:"+Thread.currentThread().getName());
                    loginController.listFoods=new productController().getListProducts();
                    fscreen.dispose();
                    LoginForm loginForm=new LoginForm();
                    loginForm.setLocationRelativeTo(null);
                    loginForm.setVisible(true);
                    String pathIcon=System.getProperty("user.dir")+"/src/images/icon.png";
                    ImageIcon icon=new ImageIcon(pathIcon);
                    loginForm.setIconImage(icon.getImage());
                  
                    // JOptionPane.showMessageDialog(null, "Test");
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                } catch (SQLException ex) {
                    Logger.getLogger(OpenFoods.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(OpenFoods.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
                Thread t=new Thread(run);
                t.start();
      
            

//                ExecutorService pool=Executors.newFixedThreadPool(1);
//        Callable<List<food>> callable=new Callable<List<food>>() {
//            @Override
//            public List<food> call() throws Exception {
//                System.out.println("Je suis dans le Thread:"+Thread.currentThread().getName());
//                return new productController().getListProducts();
//            }
//        };
//        pool.submit(callable);
//        //pool.shutdown();
//        
//        try {
//            System.out.println("Size is:"+callable.call().size());
//        } catch (Exception ex) {
//            Logger.getLogger(OpenFoods.class.getName()).log(Level.SEVERE, null, ex);
//        }
      //loginController log=new loginController();
       //log.setUserName("admin");
       //log.setPassWord("1234567");
        //System.out.println("Reponse:"+log.getResponseLogging());
       // LocalDate ld=LocalDate.now();
        //LocalDate dl=ld.minusDays(ld.getDayOfWeek().getValue());
        //System.out.println(ld.toString());
        //System.out.println("Minus:"+dl.toString());
 
    }
    
}
