/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms;

import com.java4less.textprinter.JobProperties;
import com.java4less.textprinter.PrinterFactory;
import com.java4less.textprinter.TextPrinter;
import com.java4less.textprinter.TextProperties;
import com.java4less.textprinter.ports.HDParallelPort;
import com.openfoods.configs.ActionType;
import com.openfoods.configs.FoodEnum;
import com.openfoods.configs.IAsyncQuery;
import com.openfoods.configs.OrderBillingEnum;
import com.openfoods.configs.PrinterService;
import com.openfoods.configs.PrintingType;
import com.openfoods.configs.SQLDB;
import com.openfoods.controllers.billingController;
import com.openfoods.controllers.bookingController;
import com.openfoods.controllers.loginController;
import com.openfoods.controllers.orderController;
import com.openfoods.controllers.productController;
import com.openfoods.controllers.rateController;
import com.openfoods.controllers.trashController;
import com.openfoods.controllers.userController;
import com.openfoods.models.billing;
import com.openfoods.models.food;
import com.openfoods.models.order;
import com.openfoods.models.rates;
import com.openfoods.models.user;
import java.awt.Dialog;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ColorModel;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import javax.imageio.ImageIO;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.plaf.metal.MetalComboBoxUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author hornellama
 */
public class Dashboard extends javax.swing.JFrame {

    /**
     * Creates new form Dashboard
     */
    List<food>listCommand=new LinkedList<>();
   DefaultTableModel model,modelPlat,modelAccompa,modelCmd=null;
     double totPaie=0.0;
     private JComboBox jListRules=null;
         private PrintingType pType=PrintingType.NULLABLE;
    public Dashboard() {
        initComponents();
        try {
            String path =System.getProperty("user.dir")+"/src/com/openfoods/images/icon.png";
            System.out.println("Path:"+child_config.getTitleAt(0).toString());
            child_config.setIconAt(0,new javax.swing.ImageIcon(getClass().getResource("/images/icons8-rice-bowl-528.png")));
            child_config.setIconAt(1,new javax.swing.ImageIcon(getClass().getResource("/images/icons8-coins-48.png")));
            child_config.setIconAt(2,new javax.swing.ImageIcon(getClass().getResource("/images/icons8-user-account-48.png")));
            parent_tabbed.setIconAt(0,new javax.swing.ImageIcon(getClass().getResource("/images/icons8-shopping-cart-48.png")));
            parent_tabbed.setIconAt(1,new javax.swing.ImageIcon(getClass().getResource("/images/icons8-print-48.png")));
            parent_tabbed.setIconAt(2,new javax.swing.ImageIcon(getClass().getResource("/images/icons8-settings-48.png")));
            parent_tabbed.setIconAt(3,new javax.swing.ImageIcon(getClass().getResource("/images/icons8-shutdown-40.png")));
            parent_tabbed.setIconAt(4,new javax.swing.ImageIcon(getClass().getResource("/images/icons8-about-48.png")));
            jTabbedPane1.setIconAt(0,new javax.swing.ImageIcon(getClass().getResource("/images/icons8-rice-bowl-100.png")));
            jTabbedPane1.setIconAt(1,new javax.swing.ImageIcon(getClass().getResource("/images/icons8-shopping-cart-48.png")));
        } catch (Exception e) {
            System.out.println("Console icon:"+e.getMessage());
        }
        if (!loginController._priority.equals("administrator")) {
            parent_tabbed.remove(2);
        }
        cb_client.addItem("");
        for(String s:loginController.listClients){
            cb_client.addItem(s);
        }
        lbl_user.setText(loginController._usernameLogger);
        lb_taux.setText(lb_taux.getText()+Double.toString(loginController.taux)+" CDF");
        Timer timer=new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Calendar cal=Calendar.getInstance();
               
                Date dte=new Date();
                lbl_timer.setText(cal.getTime().toLocaleString());
            }
        });
        timer.start();
        
           model =(DefaultTableModel)tble_product.getModel();
         
            for(food f:loginController.listFoods){
                if (f.getTypeFood().equals("BL")) {
                      model.insertRow(model.getRowCount(), new Object[]{f.getIdFood(),f.getNameFood(),f.getPriceFood(),false});
                }else if(f.getTypeFood().equals("PL")){
                    modelPlat=(DefaultTableModel)tble_plat.getModel();
                    modelPlat.insertRow(modelPlat.getRowCount(),new Object[]{f.getIdFood(),f.getNameFood(),f.getPriceFood(),false});
                }else{
                    modelAccompa=(DefaultTableModel)tble_accomp.getModel();
                    modelAccompa.insertRow(modelAccompa.getRowCount(),new Object[]{f.getIdFood(),f.getNameFood(),f.getPriceFood(),false});
                }
                
               

            }
            modelCmd=(DefaultTableModel)tble_command.getModel();
            
                TableColumnModel tableColumnModel=tble_product.getColumnModel();
                tableColumnModel.getColumn(0).setMaxWidth(50);
                tableColumnModel.getColumn(1).setMaxWidth(300);
                tableColumnModel.getColumn(2).setMaxWidth(80);
                tableColumnModel.getColumn(3).setMaxWidth(50);
                
                
                    TableColumnModel tableColumnModelPlat=tble_plat.getColumnModel();
                    tableColumnModelPlat.getColumn(0).setMaxWidth(50);
                    tableColumnModelPlat.getColumn(1).setMaxWidth(300);
                    tableColumnModelPlat.getColumn(2).setMaxWidth(80);
                    tableColumnModelPlat.getColumn(3).setMaxWidth(50);
                    
           TableColumnModel tableColumnModelAcc=tble_accomp.getColumnModel();
           tableColumnModel.getColumn(0).setMaxWidth(50);
           tableColumnModel.getColumn(1).setMaxWidth(300);
           tableColumnModel.getColumn(2).setMaxWidth(80);
           tableColumnModel.getColumn(3).setMaxWidth(50);
           /* tble_product.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                System.out.println("Name cells:"+evt.getPropertyName());
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
            */
    
           
           tble_command.getModel().addTableModelListener(new TableModelListener(){
            @Override
            public void tableChanged(TableModelEvent e) {
                try {
                     int row=e.getFirstRow();
                    int col=e.getColumn();
                    int qte=(int)tble_command.getValueAt(row, col);
                    double calculateqte=(qte*(double)tble_command.getValueAt(row, 4));
                    totPaie=(totPaie-(double)tble_command.getValueAt(row, 4))+calculateqte;
                    tble_command.setValueAt(calculateqte, row, 4);
                    tx_tot_paie.setText(Double.toString(totPaie));
                } catch (Exception ex) {
                    
                    
                }
               
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
               
           });
           tble_plat.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                try {
                    int row=e.getFirstRow();
                    int col=e.getColumn();
                    food f=new food
                    (
                        Integer.parseInt(modelPlat.getValueAt(row, 0).toString()),
                        modelPlat.getValueAt(row, 1).toString(),
                        Double.parseDouble(modelPlat.getValueAt(row, 2).toString()),
                        "",
                        0
                    );
                    boolean checkedProduct=(boolean)modelPlat.getValueAt(row, col);
                    if (checkedProduct) {
                    
                        modelCmd=(DefaultTableModel)tble_command.getModel();
                        modelCmd.insertRow(tble_command.getRowCount(), new Object[]{f.getIdFood(),f.getNameFood(),f.getPriceFood(),1,f.getPriceFood()});
                       // System.out.println("Size collection Add:"+listCommand.size());
                        totPaie+=f.getPriceFood();
                        tx_tot_paie.setText(Double.toString(totPaie));
                    }else{
                        for (int i = 0; i < tble_command.getRowCount(); i++) {
                        int idProduct=(int)tble_command.getValueAt(i, 0);
                        if (f.getIdFood()==idProduct) {
                            totPaie=totPaie-(double)tble_command.getValueAt(i, 4);
                            tx_tot_paie.setText(Double.toString(totPaie));
                            modelCmd.removeRow(i);
                        }
                    }
                    }
                } catch (Exception ex) {
                }
               // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
            jListRules=new JComboBox(new Object[]{"administrator","user"});
           TableColumn tbluserColumn=tble_users.getColumnModel().getColumn(3);
           tbluserColumn.setCellEditor(new DefaultCellEditor(jListRules));
           
           tble_users.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                int row=e.getFirstRow();
                int cols=e.getColumn();
                if (cols==3) {
                    String strPriority=tble_users.getValueAt(row, cols).toString();
                    //JOptionPane.showMessageDialog(null, strPriority);
                    if (!strPriority.equals("administrator") && !strPriority.equals("user") ) {
                        JOptionPane.showMessageDialog(null, "Choisissez: administrator ou user","Error Rule",JOptionPane.ERROR_MESSAGE);
                    }else{
                        try {
                            int id=Integer.parseInt(tble_users.getValueAt(row, 0).toString());
                            user uid=new user(id,
                                    tble_users.getValueAt(row, 1).toString(),
                                    tble_users.getValueAt(row, 2).toString(),
                                    "",
                                    tble_users.getValueAt(row, 3).toString()
                            );
                            int i=new userController().updateUser(uid);
                          //  JOptionPane.showMessageDialog(null, "response:"+i);
                                    
                            if (i>-1) {
                                JOptionPane.showMessageDialog(null, "Modification effectuée","Update user",JOptionPane.INFORMATION_MESSAGE);
                            }
                        } catch (ClassNotFoundException ex) {
                            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (SQLException ex) {
                            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                }
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
           tble_accomp.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                try {
                    int row=e.getFirstRow();
                    int col=e.getColumn();
                    food f=new food
                    (
                        Integer.parseInt(modelAccompa.getValueAt(row, 0).toString()),
                        modelAccompa.getValueAt(row, 1).toString(),
                        Double.parseDouble(modelAccompa.getValueAt(row, 2).toString()),
                        "",
                        0
                    );
                    boolean checkedProduct=(boolean)modelAccompa.getValueAt(row, col);
                    if (checkedProduct) {
                    
                        modelCmd=(DefaultTableModel)tble_command.getModel();
                        modelCmd.insertRow(tble_command.getRowCount(), new Object[]{f.getIdFood(),f.getNameFood(),f.getPriceFood(),1,f.getPriceFood()});
                       // System.out.println("Size collection Add:"+listCommand.size());
                        totPaie+=f.getPriceFood();
                        tx_tot_paie.setText(Double.toString(totPaie));
                    }else{
                        for (int i = 0; i < tble_command.getRowCount(); i++) {
                        int idProduct=(int)tble_command.getValueAt(i, 0);
                        if (f.getIdFood()==idProduct) {
                            totPaie=totPaie-(double)tble_command.getValueAt(i, 4);
                            tx_tot_paie.setText(Double.toString(totPaie));
                            modelCmd.removeRow(i);
                        }
                    }
                    }
                } catch (Exception ex) {
                    
                }
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
           tble_product.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
               // System.out.println("Cols:"+e.getColumn()+", Row:"+e.getFirstRow());
              // JOptionPane.showMessageDialog(null, model.getValueAt(e.getFirstRow(), e.getColumn()).toString());
                try {
                    int row=e.getFirstRow();
                int col=e.getColumn();
               food f=new food(
                       Integer.parseInt(model.getValueAt(row, 0).toString()),
                       model.getValueAt(row, 1).toString(), 
                       Double.parseDouble(model.getValueAt(row, 2).toString()),
                       "",
                       0
               );
               boolean checkedProduct=(boolean) model.getValueAt(row, col);
                if (checkedProduct) {
                    listCommand.add(f);
                    modelCmd=(DefaultTableModel)tble_command.getModel();
                    modelCmd.insertRow(tble_command.getRowCount(), new Object[]{f.getIdFood(),f.getNameFood(),f.getPriceFood(),1,f.getPriceFood()});
                    System.out.println("Size collection Add:"+listCommand.size());
                    totPaie+=f.getPriceFood();
                    tx_tot_paie.setText(Double.toString(totPaie));
                } else {
                    for (int i = 0; i < tble_command.getRowCount(); i++) {
                        int idProduct=(int)tble_command.getValueAt(i, 0);
                        if (f.getIdFood()==idProduct) {
                            totPaie=totPaie-(double)tble_command.getValueAt(i, 4);
                            tx_tot_paie.setText(Double.toString(totPaie));
                            modelCmd.removeRow(i);
                        }
                    }
                    
                }
                } catch (Exception ex) {
                    System.out.println("Exception:"+ex.getMessage());
                }
                
//               
               // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
//          tble_product.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
//            @Override
//            public void valueChanged(ListSelectionEvent e) {
//                       
//                int index=tble_product.getSelectedRow();
//                int i=0;
//                boolean status=(boolean)tble_product.getValueAt(index, 3);
//                //JOptionPane.showMessageDialog(rootPane, "Iterator:"+status);
//                System.out.println("Param event:"+e.getValueIsAdjusting());
////                if (!status){
////                                   //tble_product.setValueAt(true, index, 3);
////                                   // System.out.println("Status true: "+status);
////                                   //
////                                   status=true;
////                                   ++i;
////                                   //JOptionPane.showMessageDialog(rootPane, "Iterator:"+i);
////
////                }else{
////                                                      // JOptionPane.showMessageDialog(rootPane, "Status:"+status);
////                       //tble_product.setValueAt(false, index, 3);
////                                   // System.out.println("Status false: "+status);
////                                    ++i;
////                                    //status=false;            
////
////
////                }
//
//
//            }
//        });
            tx_filter_accomp.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterProduct(FoodEnum.AC);
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterProduct(FoodEnum.AC);
               // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
               // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
            tx_filter_plat.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterProduct(FoodEnum.PL);
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterProduct(FoodEnum.PL);
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
           
            tx_filter.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
               
                    filterProduct(FoodEnum.BL);
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterProduct(FoodEnum.BL);
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
         
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
    }
    
    private void filterProduct(FoodEnum enumf){
        RowFilter<Object, Object> filter = new RowFilter<Object, Object>() {
                    public boolean include(Entry entry) {
                      String nameproduct =entry.getValue(1).toString();
                      return (!nameproduct.equals(null));
                    }
                };
            TableRowSorter<TableModel> sorter =null;
            
        switch(enumf){
            case BL:
                sorter= new TableRowSorter<TableModel>(model);
                 tble_product.setRowSorter(sorter);
               if (tx_filter.getText().length()>0) {
                     String s=tx_filter.getText();
                     sorter.setRowFilter(RowFilter.regexFilter(s.toUpperCase()));


        }
                break;
            case PL:
                sorter= new TableRowSorter<TableModel>(modelPlat);
                tble_plat.setRowSorter(sorter);
                if (tx_filter_plat.getText().length()>0) {
                    String sPlat=tx_filter_plat.getText();
                    sorter.setRowFilter(RowFilter.regexFilter(sPlat.toUpperCase()));
                }
                break;
                
                
                case AC:
                sorter= new TableRowSorter<TableModel>(modelAccompa);
                tble_accomp.setRowSorter(sorter);
                if (tx_filter_accomp.getText().length()>0) {
                    String sAccomp=tx_filter_accomp.getText();
                    sorter.setRowFilter(RowFilter.regexFilter(sAccomp.toUpperCase()));
                }
                break;
                
                
                
        }
    }

    @Override
    public Dialog.ModalExclusionType getModalExclusionType() {
        return super.getModalExclusionType(); //To change body of generated methods, choose Tools | Templates.
    }
    
   
    

    @Override
    public ColorModel getColorModel() {
        return super.getColorModel(); //To change body of generated methods, choose Tools | Templates.
    }
  

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        parent_tabbed = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        tx_filter = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tble_product = new javax.swing.JTable();
        jPanel14 = new javax.swing.JPanel();
        tx_filter_plat = new javax.swing.JTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        tble_plat = new javax.swing.JTable();
        jPanel15 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tble_accomp = new javax.swing.JTable();
        tx_filter_accomp = new javax.swing.JTextField();
        jPanel16 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tble_command = new javax.swing.JTable();
        jPanel23 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        tx_serverName = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        cb_client = new javax.swing.JComboBox<>();
        jPanel31 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        tx_tot_paie = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lbl_timer = new javax.swing.JLabel();
        lb_taux = new javax.swing.JLabel();
        lbl_user = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        btn_add_reservation = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        btn_billing_weekly = new javax.swing.JButton();
        btn_blling_monthly = new javax.swing.JButton();
        jPanel13 = new javax.swing.JPanel();
        dpicker2 = new datechooser.beans.DateChooserCombo();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        btn_blling_choices = new javax.swing.JButton();
        dpicker1 = new datechooser.beans.DateChooserCombo();
        btnDaily = new javax.swing.JButton();
        jPanel27 = new javax.swing.JPanel();
        rd_action_cmd = new javax.swing.JRadioButton();
        rd_action_booking = new javax.swing.JRadioButton();
        jPanel24 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tble_orderBilling = new javax.swing.JTable();
        tx_filter_cmd2 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        lblTot_filter = new javax.swing.JLabel();
        lbltotf = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        child_config = new javax.swing.JTabbedPane();
        jPanel8 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        tx_nameProduct = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        tx_PU = new javax.swing.JTextField();
        cb_typeFood = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        btn_add_product = new javax.swing.JButton();
        btn_print_menu = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tble_product_viewer = new javax.swing.JTable();
        tx_filter_cmd_viewer = new javax.swing.JTextField();
        jPanel11 = new javax.swing.JPanel();
        cb_categories_food = new javax.swing.JComboBox<>();
        sp_amount = new javax.swing.JSpinner();
        btn_update_product = new javax.swing.JButton();
        rd_add = new javax.swing.JRadioButton();
        rd_suppr = new javax.swing.JRadioButton();
        jPanel9 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        tx_amount_rate = new javax.swing.JTextField();
        btn_create_rate = new javax.swing.JButton();
        jPanel25 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        tble_rates = new javax.swing.JTable();
        jPanel20 = new javax.swing.JPanel();
        jPanel21 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        tx_username = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        tx_username_full = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        tx_passwor_create = new javax.swing.JPasswordField();
        btn_add_user = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        cb_role = new javax.swing.JComboBox<>();
        jPanel22 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tble_users = new javax.swing.JTable();
        jPanel28 = new javax.swing.JPanel();
        jPanel29 = new javax.swing.JPanel();
        btn_cancel_order = new javax.swing.JButton();
        btn_cancel_booking = new javax.swing.JButton();
        tx_search_cancel_invoice = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        btn_cancel_booking_order = new javax.swing.JButton();
        jPanel30 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tble_cancel_booking = new javax.swing.JTable();
        lbl_total_cancel = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel26 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        parent_tabbed.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                parent_tabbedStateChanged(evt);
            }
        });

        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder("BOISSON ET LIQUEUR"));

        tx_filter.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                tx_filterInputMethodTextChanged(evt);
            }
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
        });
        tx_filter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tx_filterKeyPressed(evt);
            }
        });

        tble_product.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id product", "Name product", "PU", "Selected"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tble_product.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                tble_productAncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        tble_product.addVetoableChangeListener(new java.beans.VetoableChangeListener() {
            public void vetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {
                tble_productVetoableChange(evt);
            }
        });
        jScrollPane2.setViewportView(tble_product);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addComponent(tx_filter, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addComponent(tx_filter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder(" PLATS"));

        tx_filter_plat.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                tx_filter_platInputMethodTextChanged(evt);
            }
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
        });
        tx_filter_plat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tx_filter_platKeyPressed(evt);
            }
        });

        tble_plat.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id product", "Name product", "PU", "Selected"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tble_plat.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                tble_platAncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        tble_plat.addVetoableChangeListener(new java.beans.VetoableChangeListener() {
            public void vetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {
                tble_platVetoableChange(evt);
            }
        });
        jScrollPane5.setViewportView(tble_plat);

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addComponent(tx_filter_plat, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addComponent(tx_filter_plat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel15.setBorder(javax.swing.BorderFactory.createTitledBorder("ACCOPAGNEMENT"));

        tble_accomp.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id product", "Name product", "PU", "Selected"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tble_accomp.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                tble_accompAncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        tble_accomp.addVetoableChangeListener(new java.beans.VetoableChangeListener() {
            public void vetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {
                tble_accompVetoableChange(evt);
            }
        });
        jScrollPane3.setViewportView(tble_accomp);

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(tx_filter_accomp, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tx_filter_accomp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel16.setBorder(javax.swing.BorderFactory.createTitledBorder("COMMANDE CLIENT"));

        tble_command.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id product", "Name product", "PU", "Qte", "Total paie"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tble_command.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                tble_commandAncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        tble_command.addVetoableChangeListener(new java.beans.VetoableChangeListener() {
            public void vetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {
                tble_commandVetoableChange(evt);
            }
        });
        jScrollPane4.setViewportView(tble_command);

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel23.setBorder(javax.swing.BorderFactory.createTitledBorder("Client et serveur"));

        jLabel17.setText("Nom du serveur");

        jLabel18.setText("Table client");

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17)
                    .addComponent(jLabel18))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tx_serverName)
                    .addComponent(cb_client, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(tx_serverName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(cb_client, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(128, Short.MAX_VALUE))
        );

        jPanel31.setBorder(javax.swing.BorderFactory.createTitledBorder("TOTAL A PAYER"));

        jLabel16.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel16.setText("Total a payer : CDF");

        tx_tot_paie.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        tx_tot_paie.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        tx_tot_paie.setText("0.0");

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 1, 10)); // NOI18N
        jLabel1.setText("Utilisateur connecté :");
        jLabel1.setToolTipText("");

        lbl_timer.setFont(new java.awt.Font("Lucida Grande", 1, 10)); // NOI18N
        lbl_timer.setText("timer");

        lb_taux.setFont(new java.awt.Font("Lucida Grande", 1, 10)); // NOI18N
        lb_taux.setText("Taux : ");

        lbl_user.setFont(new java.awt.Font("Lucida Grande", 1, 10)); // NOI18N
        lbl_user.setText("user");

        javax.swing.GroupLayout jPanel31Layout = new javax.swing.GroupLayout(jPanel31);
        jPanel31.setLayout(jPanel31Layout);
        jPanel31Layout.setHorizontalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel31Layout.createSequentialGroup()
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tx_tot_paie, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel31Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_user))
                    .addComponent(lbl_timer)
                    .addComponent(lb_taux))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel31Layout.setVerticalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel31Layout.createSequentialGroup()
                .addContainerGap(17, Short.MAX_VALUE)
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(tx_tot_paie))
                .addGap(18, 18, 18)
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lbl_user))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_timer)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lb_taux))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Sauvegarder les informations"));

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-shopping-basket-48.png"))); // NOI18N
        jButton2.setText("Commandez");
        jButton2.setIconTextGap(5);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        btn_add_reservation.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-shopping-cart-48.png"))); // NOI18N
        btn_add_reservation.setText("Reservation");
        btn_add_reservation.setIconTextGap(5);
        btn_add_reservation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_add_reservationActionPerformed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-cancel-45.png"))); // NOI18N
        jButton3.setText("Annuler");
        jButton3.setIconTextGap(5);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
                    .addComponent(btn_add_reservation, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_add_reservation, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanel23, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel15, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(999, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(44, 44, 44)
                                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(21, 21, 21))
                    .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jPanel31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(1048, Short.MAX_VALUE))
        );

        parent_tabbed.addTab("Nouvelle demande", jPanel1);

        btn_billing_weekly.setText("Rapport Hebdomadaire");
        btn_billing_weekly.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_billing_weeklyActionPerformed(evt);
            }
        });

        btn_blling_monthly.setText("Rapport Mensuel");
        btn_blling_monthly.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_blling_monthlyActionPerformed(evt);
            }
        });

        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder("Fitrer par date"));

        dpicker2.setWeekStyle(datechooser.view.WeekDaysStyle.FULL);
        try {
            dpicker2.setDefaultPeriods(new datechooser.model.multiple.PeriodSet(new datechooser.model.multiple.Period(new java.util.GregorianCalendar(2018, 2, 4),
                new java.util.GregorianCalendar(2018, 2, 4))));
    } catch (datechooser.model.exeptions.IncompatibleDataExeption e1) {
        e1.printStackTrace();
    }
    dpicker2.setLocale(new java.util.Locale("en", "AU", ""));

    jLabel8.setText("Date fin");

    jLabel9.setText("Date debut");

    btn_blling_choices.setText("Filter");
    btn_blling_choices.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btn_blling_choicesActionPerformed(evt);
        }
    });

    dpicker1.setWeekStyle(datechooser.view.WeekDaysStyle.FULL);
    try {
        dpicker1.setDefaultPeriods(new datechooser.model.multiple.PeriodSet(new datechooser.model.multiple.Period(new java.util.GregorianCalendar(2018, 2, 4),
            new java.util.GregorianCalendar(2018, 2, 4))));
} catch (datechooser.model.exeptions.IncompatibleDataExeption e1) {
    e1.printStackTrace();
    }
    dpicker1.setLocale(new java.util.Locale("en", "AU", ""));

    javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
    jPanel13.setLayout(jPanel13Layout);
    jPanel13Layout.setHorizontalGroup(
        jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel13Layout.createSequentialGroup()
            .addComponent(btn_blling_choices, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(0, 0, Short.MAX_VALUE))
        .addGroup(jPanel13Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel13Layout.createSequentialGroup()
                    .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel8)
                        .addComponent(jLabel9)
                        .addComponent(dpicker2, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(0, 0, Short.MAX_VALUE))
                .addComponent(dpicker1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addContainerGap())
    );
    jPanel13Layout.setVerticalGroup(
        jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel13Layout.createSequentialGroup()
            .addGap(14, 14, 14)
            .addComponent(jLabel9)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(dpicker1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jLabel8)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(dpicker2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btn_blling_choices, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap())
    );

    btnDaily.setText("Rapport Journalier");
    btnDaily.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btnDailyActionPerformed(evt);
        }
    });

    jPanel27.setBorder(javax.swing.BorderFactory.createTitledBorder("Choix des données"));

    rd_action_cmd.setText("Commandes");
    rd_action_cmd.addChangeListener(new javax.swing.event.ChangeListener() {
        public void stateChanged(javax.swing.event.ChangeEvent evt) {
            rd_action_cmdStateChanged(evt);
        }
    });
    rd_action_cmd.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            rd_action_cmdActionPerformed(evt);
        }
    });

    rd_action_booking.setText("Reservations");
    rd_action_booking.addChangeListener(new javax.swing.event.ChangeListener() {
        public void stateChanged(javax.swing.event.ChangeEvent evt) {
            rd_action_bookingStateChanged(evt);
        }
    });

    javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
    jPanel27.setLayout(jPanel27Layout);
    jPanel27Layout.setHorizontalGroup(
        jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel27Layout.createSequentialGroup()
            .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                .addComponent(rd_action_booking)
                .addComponent(rd_action_cmd))
            .addGap(0, 0, Short.MAX_VALUE))
    );
    jPanel27Layout.setVerticalGroup(
        jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel27Layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(rd_action_cmd)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(rd_action_booking)
            .addContainerGap(42, Short.MAX_VALUE))
    );

    javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
    jPanel2.setLayout(jPanel2Layout);
    jPanel2Layout.setHorizontalGroup(
        jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel2Layout.createSequentialGroup()
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(14, 14, 14)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(btnDaily, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_billing_weekly, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_blling_monthly, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addComponent(jPanel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGap(0, 14, Short.MAX_VALUE))
    );
    jPanel2Layout.setVerticalGroup(
        jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel2Layout.createSequentialGroup()
            .addGap(17, 17, 17)
            .addComponent(btnDaily, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(btn_billing_weekly, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(btn_blling_monthly, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, 193, Short.MAX_VALUE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap())
    );

    jPanel24.setBorder(javax.swing.BorderFactory.createTitledBorder("Tableau des commandes"));

    tble_orderBilling.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {

        },
        new String [] {
            "idProduct", "product", "Qté", "Montant", "Facture", "Taux", "DateFacture", "Serveur", "Caissier", "Client"
        }
    ) {
        boolean[] canEdit = new boolean [] {
            false, false, false, false, false, false, false, false, false, false
        };

        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return canEdit [columnIndex];
        }
    });
    jScrollPane7.setViewportView(tble_orderBilling);

    tx_filter_cmd2.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            tx_filter_cmd2ActionPerformed(evt);
        }
    });

    jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-print-48.png"))); // NOI18N
    jButton1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
    jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
    jButton1.setIconTextGap(8);
    jButton1.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton1ActionPerformed(evt);
        }
    });

    lblTot_filter.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
    lblTot_filter.setText("0.0");

    lbltotf.setText("CDF");

    javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
    jPanel24.setLayout(jPanel24Layout);
    jPanel24Layout.setHorizontalGroup(
        jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel24Layout.createSequentialGroup()
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 484, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(tx_filter_cmd2, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jButton1)
            .addGap(187, 187, 187))
        .addGroup(jPanel24Layout.createSequentialGroup()
            .addGap(193, 193, 193)
            .addComponent(lbltotf)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(lblTot_filter, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(0, 0, Short.MAX_VALUE))
    );
    jPanel24Layout.setVerticalGroup(
        jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel24Layout.createSequentialGroup()
            .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel24Layout.createSequentialGroup()
                    .addComponent(tx_filter_cmd2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addComponent(jButton1))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(lbltotf)
                .addComponent(lblTot_filter))
            .addContainerGap(20, Short.MAX_VALUE))
    );

    javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
    jPanel4.setLayout(jPanel4Layout);
    jPanel4Layout.setHorizontalGroup(
        jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel4Layout.createSequentialGroup()
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, 592, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap(422, Short.MAX_VALUE))
    );
    jPanel4Layout.setVerticalGroup(
        jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel4Layout.createSequentialGroup()
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addGap(16, 16, 16)
                    .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addContainerGap(85, Short.MAX_VALUE))
    );

    jTabbedPane1.addTab("Les Commandes & Reservations", jPanel4);

    javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
    jPanel3.setLayout(jPanel3Layout);
    jPanel3Layout.setHorizontalGroup(
        jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel3Layout.createSequentialGroup()
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1298, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(0, 580, Short.MAX_VALUE))
    );
    jPanel3Layout.setVerticalGroup(
        jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel3Layout.createSequentialGroup()
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 618, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(0, 1184, Short.MAX_VALUE))
    );

    parent_tabbed.addTab("Rapports", jPanel3);

    child_config.addChangeListener(new javax.swing.event.ChangeListener() {
        public void stateChanged(javax.swing.event.ChangeEvent evt) {
            child_configStateChanged(evt);
        }
    });

    jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder("Ajouter un element du menu"));

    jLabel2.setText("Nom plat");

    tx_nameProduct.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            tx_nameProductActionPerformed(evt);
        }
    });

    jLabel3.setText("Prix unitaire");

    tx_PU.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            tx_PUActionPerformed(evt);
        }
    });

    cb_typeFood.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Accompagnement", "Boisson/Liqueur", "Repas" }));

    jLabel4.setText("Type ");

    btn_add_product.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-plus-48.png"))); // NOI18N
    btn_add_product.setText("Ajouter");
    btn_add_product.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btn_add_productActionPerformed(evt);
        }
    });

    btn_print_menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-print-48.png"))); // NOI18N
    btn_print_menu.setText("imprimer");
    btn_print_menu.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btn_print_menuActionPerformed(evt);
        }
    });

    javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
    jPanel10.setLayout(jPanel10Layout);
    jPanel10Layout.setHorizontalGroup(
        jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel10Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        .addGroup(jPanel10Layout.createSequentialGroup()
            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(cb_typeFood, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(tx_PU, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(tx_nameProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel10Layout.createSequentialGroup()
                    .addComponent(btn_add_product)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(btn_print_menu)))
            .addGap(0, 74, Short.MAX_VALUE))
    );
    jPanel10Layout.setVerticalGroup(
        jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel10Layout.createSequentialGroup()
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel2)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(tx_nameProduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(jLabel3)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(tx_PU, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jLabel4)
            .addGap(13, 13, 13)
            .addComponent(cb_typeFood, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(btn_add_product, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btn_print_menu, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(66, 66, 66))
    );

    jLabel19.setText("jLabel19");

    jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Grille tarifaires"));

    tble_product_viewer.setAutoCreateRowSorter(true);
    tble_product_viewer.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {

        },
        new String [] {
            "Id product", "Product Name", "Type product", "PU"
        }
    ) {
        Class[] types = new Class [] {
            java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Double.class
        };
        boolean[] canEdit = new boolean [] {
            false, true, true, true
        };

        public Class getColumnClass(int columnIndex) {
            return types [columnIndex];
        }

        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return canEdit [columnIndex];
        }
    });
    jScrollPane1.setViewportView(tble_product_viewer);

    javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
    jPanel7.setLayout(jPanel7Layout);
    jPanel7Layout.setHorizontalGroup(
        jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(tx_filter_cmd_viewer, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 379, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(136, 136, 136))
    );
    jPanel7Layout.setVerticalGroup(
        jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel7Layout.createSequentialGroup()
            .addGap(18, 18, 18)
            .addComponent(tx_filter_cmd_viewer, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

    jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder("Modifier prix par categorie"));

    cb_categories_food.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Accompagnement", "Boisson/Liqueur", "Plats" }));

    btn_update_product.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-downloading-updates-48.png"))); // NOI18N
    btn_update_product.setText("Mettre a jour");
    btn_update_product.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btn_update_productActionPerformed(evt);
        }
    });

    rd_add.setText("Ajouter");
    rd_add.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            rd_addActionPerformed(evt);
        }
    });

    rd_suppr.setText("Retrancher");
    rd_suppr.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            rd_supprActionPerformed(evt);
        }
    });

    javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
    jPanel11.setLayout(jPanel11Layout);
    jPanel11Layout.setHorizontalGroup(
        jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel11Layout.createSequentialGroup()
            .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(cb_categories_food, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(sp_amount, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel11Layout.createSequentialGroup()
                    .addComponent(rd_add, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(rd_suppr, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGap(0, 40, Short.MAX_VALUE))
        .addGroup(jPanel11Layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(btn_update_product, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
    jPanel11Layout.setVerticalGroup(
        jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel11Layout.createSequentialGroup()
            .addComponent(cb_categories_food, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(sp_amount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(rd_add)
                .addComponent(rd_suppr))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(btn_update_product, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
    );

    javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
    jPanel8.setLayout(jPanel8Layout);
    jPanel8Layout.setHorizontalGroup(
        jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel8Layout.createSequentialGroup()
            .addGap(1016, 1016, 1016)
            .addComponent(jLabel19)
            .addGap(0, 782, Short.MAX_VALUE))
        .addGroup(jPanel8Layout.createSequentialGroup()
            .addGap(12, 12, 12)
            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel8Layout.createSequentialGroup()
                    .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 402, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
    jPanel8Layout.setVerticalGroup(
        jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel8Layout.createSequentialGroup()
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel19)
            .addGap(141, 141, 141))
        .addGroup(jPanel8Layout.createSequentialGroup()
            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(0, 1305, Short.MAX_VALUE))
    );

    child_config.addTab("Plats", jPanel8);

    jPanel17.setBorder(javax.swing.BorderFactory.createTitledBorder("Creer un nouveau taux"));

    jLabel10.setText("Montant dollar");

    jTextField1.setEditable(false);
    jTextField1.setText("1$");

    jLabel11.setText("Montant CDF");

    tx_amount_rate.setText("0");

    btn_create_rate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-plus-48.png"))); // NOI18N
    btn_create_rate.setText("Creer");
    btn_create_rate.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btn_create_rateActionPerformed(evt);
        }
    });

    javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
    jPanel17.setLayout(jPanel17Layout);
    jPanel17Layout.setHorizontalGroup(
        jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel17Layout.createSequentialGroup()
            .addGap(14, 14, 14)
            .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel11)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jTextField1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addComponent(tx_amount_rate, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btn_create_rate))
            .addContainerGap(28, Short.MAX_VALUE))
    );
    jPanel17Layout.setVerticalGroup(
        jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel17Layout.createSequentialGroup()
            .addGap(18, 18, 18)
            .addComponent(jLabel10)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jLabel11)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(tx_amount_rate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(btn_create_rate)
            .addContainerGap(29, Short.MAX_VALUE))
    );

    jPanel25.setBorder(javax.swing.BorderFactory.createTitledBorder("List des taux utilisés"));

    tble_rates.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {

        },
        new String [] {
            "IdRate", "USD", "CDF"
        }
    ) {
        boolean[] canEdit = new boolean [] {
            false, false, false
        };

        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return canEdit [columnIndex];
        }
    });
    jScrollPane9.setViewportView(tble_rates);

    javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
    jPanel25.setLayout(jPanel25Layout);
    jPanel25Layout.setHorizontalGroup(
        jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
    );
    jPanel25Layout.setVerticalGroup(
        jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel25Layout.createSequentialGroup()
            .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(0, 9, Short.MAX_VALUE))
    );

    javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
    jPanel9.setLayout(jPanel9Layout);
    jPanel9Layout.setHorizontalGroup(
        jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel9Layout.createSequentialGroup()
            .addGap(37, 37, 37)
            .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap(1322, Short.MAX_VALUE))
    );
    jPanel9Layout.setVerticalGroup(
        jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel9Layout.createSequentialGroup()
            .addGap(68, 68, 68)
            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel9Layout.createSequentialGroup()
                    .addGap(11, 11, 11)
                    .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addContainerGap(1432, Short.MAX_VALUE))
    );

    child_config.addTab("Taux de change", jPanel9);

    jPanel21.setBorder(javax.swing.BorderFactory.createTitledBorder("Creer un utilisateur"));

    jLabel13.setText("Nom utilisateur (Sys.)");

    jLabel14.setText("Nom utiliseur(Dsiplay)");

    jLabel15.setText("Mot de passe");

    btn_add_user.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-plus-48.png"))); // NOI18N
    btn_add_user.setText("Creer");
    btn_add_user.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btn_add_userActionPerformed(evt);
        }
    });

    jLabel20.setText("Role");

    cb_role.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", "Administrateur", "Utilisateur" }));

    javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
    jPanel21.setLayout(jPanel21Layout);
    jPanel21Layout.setHorizontalGroup(
        jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel21Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tx_username_full, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cb_role, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tx_passwor_create)
                    .addComponent(tx_username, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addComponent(btn_add_user, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addContainerGap(16, Short.MAX_VALUE))
    );
    jPanel21Layout.setVerticalGroup(
        jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel21Layout.createSequentialGroup()
            .addGap(26, 26, 26)
            .addComponent(jLabel13)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(tx_username, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jLabel14)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(tx_username_full, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jLabel15)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(tx_passwor_create, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jLabel20)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(cb_role)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(btn_add_user)
            .addContainerGap())
    );

    jPanel22.setBorder(javax.swing.BorderFactory.createTitledBorder("Liste des utilisateurs"));

    tble_users.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {

        },
        new String [] {
            "Iduser", "Username", "Name ", "Priority"
        }
    ) {
        boolean[] canEdit = new boolean [] {
            false, true, true, true
        };

        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return canEdit [columnIndex];
        }
    });
    jScrollPane8.setViewportView(tble_users);

    javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
    jPanel22.setLayout(jPanel22Layout);
    jPanel22Layout.setHorizontalGroup(
        jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel22Layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
    jPanel22Layout.setVerticalGroup(
        jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel22Layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap(17, Short.MAX_VALUE))
    );

    javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
    jPanel20.setLayout(jPanel20Layout);
    jPanel20Layout.setHorizontalGroup(
        jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel20Layout.createSequentialGroup()
            .addGap(30, 30, 30)
            .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap(1021, Short.MAX_VALUE))
    );
    jPanel20Layout.setVerticalGroup(
        jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel20Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addContainerGap(1385, Short.MAX_VALUE))
    );

    child_config.addTab("Utilisateurs", jPanel20);

    javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
    jPanel5.setLayout(jPanel5Layout);
    jPanel5Layout.setHorizontalGroup(
        jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel5Layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(child_config))
    );
    jPanel5Layout.setVerticalGroup(
        jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel5Layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(child_config))
    );

    parent_tabbed.addTab("Configuration", jPanel5);

    jPanel29.setBorder(javax.swing.BorderFactory.createTitledBorder("Actions "));

    btn_cancel_order.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-shopping-cart-48.png"))); // NOI18N
    btn_cancel_order.setText("                              COMMANDE");
    btn_cancel_order.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btn_cancel_orderActionPerformed(evt);
        }
    });

    btn_cancel_booking.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-shopping-basket-48.png"))); // NOI18N
    btn_cancel_booking.setText("                        RESERVATION");
    btn_cancel_booking.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btn_cancel_bookingActionPerformed(evt);
        }
    });

    jLabel25.setText("Entrez le code de la facture");

    btn_cancel_booking_order.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-cancel-45.png"))); // NOI18N
    btn_cancel_booking_order.setText("             ANNULER LA FACTURE");
    btn_cancel_booking_order.setEnabled(false);
    btn_cancel_booking_order.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btn_cancel_booking_orderActionPerformed(evt);
        }
    });

    javax.swing.GroupLayout jPanel29Layout = new javax.swing.GroupLayout(jPanel29);
    jPanel29.setLayout(jPanel29Layout);
    jPanel29Layout.setHorizontalGroup(
        jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel29Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(tx_search_cancel_invoice, javax.swing.GroupLayout.Alignment.TRAILING)
                .addComponent(btn_cancel_booking, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_cancel_order, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel29Layout.createSequentialGroup()
                    .addComponent(jLabel25)
                    .addGap(0, 0, Short.MAX_VALUE))
                .addComponent(btn_cancel_booking_order, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE))
            .addContainerGap())
    );
    jPanel29Layout.setVerticalGroup(
        jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel29Layout.createSequentialGroup()
            .addGap(18, 18, 18)
            .addComponent(jLabel25)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(tx_search_cancel_invoice, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(btn_cancel_order)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(btn_cancel_booking, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(btn_cancel_booking_order)
            .addContainerGap(60, Short.MAX_VALUE))
    );

    jPanel30.setBorder(javax.swing.BorderFactory.createTitledBorder("Tableau des données"));

    tble_cancel_booking.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {
            {null, null, null, null, null},
            {null, null, null, null, null},
            {null, null, null, null, null},
            {null, null, null, null, null}
        },
        new String [] {
            "Date command", "id product", "Product Name", "Quantity", "price"
        }
    ) {
        boolean[] canEdit = new boolean [] {
            false, true, false, false, true
        };

        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return canEdit [columnIndex];
        }
    });
    jScrollPane6.setViewportView(tble_cancel_booking);
    if (tble_cancel_booking.getColumnModel().getColumnCount() > 0) {
        tble_cancel_booking.getColumnModel().getColumn(0).setResizable(false);
    }

    lbl_total_cancel.setText("lbl_total");

    javax.swing.GroupLayout jPanel30Layout = new javax.swing.GroupLayout(jPanel30);
    jPanel30.setLayout(jPanel30Layout);
    jPanel30Layout.setHorizontalGroup(
        jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
        .addGroup(jPanel30Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel30Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(lbl_total_cancel, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 910, Short.MAX_VALUE))
            .addContainerGap())
    );
    jPanel30Layout.setVerticalGroup(
        jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel30Layout.createSequentialGroup()
            .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 333, Short.MAX_VALUE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(lbl_total_cancel, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(19, 19, 19))
    );

    javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
    jPanel28.setLayout(jPanel28Layout);
    jPanel28Layout.setHorizontalGroup(
        jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel28Layout.createSequentialGroup()
            .addComponent(jPanel29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jPanel30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(0, 654, Short.MAX_VALUE))
    );
    jPanel28Layout.setVerticalGroup(
        jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel28Layout.createSequentialGroup()
            .addComponent(jPanel29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(0, 0, Short.MAX_VALUE))
        .addGroup(jPanel28Layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jPanel30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap(1385, Short.MAX_VALUE))
    );

    parent_tabbed.addTab("Annulation", jPanel28);

    javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
    jPanel18.setLayout(jPanel18Layout);
    jPanel18Layout.setHorizontalGroup(
        jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGap(0, 1878, Short.MAX_VALUE)
    );
    jPanel18Layout.setVerticalGroup(
        jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGap(0, 1802, Short.MAX_VALUE)
    );

    parent_tabbed.addTab("Deconnexion", jPanel18);

    jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-rice-bowl-528.png"))); // NOI18N

    jLabel6.setFont(new java.awt.Font("Lucida Grande", 0, 30)); // NOI18N
    jLabel6.setText("Open foods 1.0");

    jLabel7.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
    jLabel7.setText("2018 Copyright, Alls Right Reserved");

    jPanel26.setBorder(javax.swing.BorderFactory.createTitledBorder("Contact Panel"));

    jLabel21.setText("Email   :");

    jLabel22.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
    jLabel22.setText("hlama@live.fr");

    jLabel23.setText("Phone   :");

    jLabel24.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
    jLabel24.setText("(+243)994601031");

    javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
    jPanel26.setLayout(jPanel26Layout);
    jPanel26Layout.setHorizontalGroup(
        jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel26Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, 241, Short.MAX_VALUE)
                .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel26Layout.createSequentialGroup()
                    .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addContainerGap())
    );
    jPanel26Layout.setVerticalGroup(
        jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel26Layout.createSequentialGroup()
            .addGap(15, 15, 15)
            .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jLabel22)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jLabel24)
            .addContainerGap(34, Short.MAX_VALUE))
    );

    javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
    jPanel19.setLayout(jPanel19Layout);
    jPanel19Layout.setHorizontalGroup(
        jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel19Layout.createSequentialGroup()
            .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel19Layout.createSequentialGroup()
                    .addGap(147, 147, 147)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel19Layout.createSequentialGroup()
                    .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel19Layout.createSequentialGroup()
                            .addGap(155, 155, 155)
                            .addComponent(jLabel6))
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 481, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addContainerGap(1126, Short.MAX_VALUE))
    );
    jPanel19Layout.setVerticalGroup(
        jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel19Layout.createSequentialGroup()
            .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel19Layout.createSequentialGroup()
                    .addGap(25, 25, 25)
                    .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 431, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jLabel6)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap(1289, Short.MAX_VALUE))
    );

    parent_tabbed.addTab("A propos", jPanel19);

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(parent_tabbed))
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(parent_tabbed)
            .addGap(14, 14, 14))
    );

    pack();
    }// </editor-fold>//GEN-END:initComponents

    public void reInitializer(){
      
        try {
            List<food>lf=new productController().getListProducts();
            Stream strm=lf.stream();
            modelAccompa.getDataVector().removeAllElements();
            
            strm.forEach((t) -> {
                food fd=(food)t;
                if (fd.getTypeFood().toUpperCase().equals("AC")) {
                    modelAccompa.insertRow(tble_accomp.getRowCount(), new Object[]{
                        fd.getIdFood(),
                        fd.getNameFood(),
                        fd.getPriceFood(),
                        false
                    });
                }
                else if (fd.getTypeFood().toUpperCase().equals("PL")) {
                    modelPlat.insertRow(tble_plat.getRowCount(), new Object[]{
                        fd.getIdFood(),
                        fd.getNameFood(),
                        fd.getPriceFood(),
                        false
                    });
                }
                 else {
                    model.insertRow(tble_product.getRowCount(), new Object[]{
                        fd.getIdFood(),
                        fd.getNameFood(),
                        fd.getPriceFood(),
                        false
                    });
                }
                
            });
        } catch (SQLException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
                                       
    }
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
          if (tble_command.getRowCount()==0) {
            JOptionPane.showMessageDialog(null, "Aucune commande n'a été séléctionnée","pas de commande",JOptionPane.ERROR_MESSAGE);
        } else {
            if (tx_serverName.getText().equals("") || cb_client.getSelectedItem().toString()=="") {
                
               JOptionPane.showMessageDialog(null, "Séléctionnez le nom du serveur et/ou la table du client","Information requise",JOptionPane.ERROR_MESSAGE);
                
            } else {
            
       jButton2.setEnabled(false);
        String cboClient=cb_client.getSelectedItem().toString();
        orderController oController=new orderController();
        LocalDate ld=LocalDate.now();
        String dateBilling=ld.getYear()+"-"+ld.getMonthValue()+"-"+ld.getDayOfMonth();
        billing bl=new billing(cboClient,
                new rates(1,loginController.idRate,loginController.taux),
                Double.parseDouble(tx_tot_paie.getText()),
                tx_serverName.getText(),dateBilling
        );
        billingController bController = null;
        try {
            bController = new billingController(bl,ActionType.ORDER);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            bl=bController.executeUpdateBilling();
            System.out.println("Id Billing:"+bl.getIdBilling());
        } catch (SQLException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        List<order>orders=new LinkedList<>();
        for (int i = 0; i < tble_command.getRowCount(); i++) {
            
            food f=new food(
                    (int)tble_command.getValueAt(i, 0), 
                    tble_command.getValueAt(i, 1).toString()
                    , (double) tble_command.getValueAt(i, 2),
                    "", 
                    1);
            
           // foods.add(f);
          order orderPaie=new order(
                  f,
                  Integer.parseInt(tble_command.getValueAt(i, 3).toString()), 
                  Double.parseDouble(tble_command.getValueAt(i, 4).toString()),
                  loginController._idUser, 
                  bl.getIdBilling(), 
                  tx_serverName.getText());
                  orders.add(orderPaie);
          
          

        }
                try {
                    boolean responseOrders=oController.addOrder(orders,ActionType.ORDER);
                      if (responseOrders) {
                JOptionPane.showMessageDialog(null, "Commande enregistrée avec succes","Save Order",JOptionPane.INFORMATION_MESSAGE);
                jButton2.setEnabled(true);
                                

                String path=  path =System.getProperty("user.dir")+"/src/com/openfoods/reports/rp_print_order.jasper";
                 SQLDB sqldb = new SQLDB();
                   Connection cnx=sqldb.getConnection();
                   Map<String,Object> map=new HashMap<>();
                 map.put("idTicket", bl.getIdBilling());
                   JasperPrint jprint=JasperFillManager.fillReport(path, map, cnx);
                   JasperViewer.viewReport(jprint, false);
                   this.clearOrders();
                
            }
                } catch (ClassNotFoundException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                    Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                                        JOptionPane.showMessageDialog(null, ex.getMessage());

                    Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
                } catch (JRException ex) {
                    Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
                }
        
            }
        }
                
        


    }//GEN-LAST:event_jButton2ActionPerformed

    private void tble_productVetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {//GEN-FIRST:event_tble_productVetoableChange
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(null, ""+evt.getNewValue());
    }//GEN-LAST:event_tble_productVetoableChange

    private void tble_productAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_tble_productAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_tble_productAncestorAdded

    private void tx_filterInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_tx_filterInputMethodTextChanged
        // TODO add your handling code here:
         
    }//GEN-LAST:event_tx_filterInputMethodTextChanged

    private void tx_filterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tx_filterKeyPressed
        // TODO add your handling code here:

        
    }//GEN-LAST:event_tx_filterKeyPressed

    private void tble_accompAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_tble_accompAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_tble_accompAncestorAdded

    private void tble_accompVetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {//GEN-FIRST:event_tble_accompVetoableChange
        // TODO add your handling code here:
    }//GEN-LAST:event_tble_accompVetoableChange

    private void tx_filter_platInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_tx_filter_platInputMethodTextChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_tx_filter_platInputMethodTextChanged

    private void tx_filter_platKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tx_filter_platKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tx_filter_platKeyPressed

    private void tble_platAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_tble_platAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_tble_platAncestorAdded

    private void tble_platVetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {//GEN-FIRST:event_tble_platVetoableChange
        // TODO add your handling code here:
    }//GEN-LAST:event_tble_platVetoableChange

    private void tble_commandAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_tble_commandAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_tble_commandAncestorAdded

    private void tble_commandVetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {//GEN-FIRST:event_tble_commandVetoableChange
        // TODO add your handling code here:
    }//GEN-LAST:event_tble_commandVetoableChange

    private void filterViewerFood(){
        
    }
       private Map<String,Object>tryDouble(String data){
         Map<String,Object> map=new HashMap<>();
         try{
            Double.parseDouble(data);
           map.put("status", true);
           map.put("data", Double.parseDouble(data));
         }catch(Exception e){
           map.put("status", false);
           map.put("data", 0.00);
         }
         return map;
     }
       
    OrderBillingEnum obe;
    private void btnDailyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDailyActionPerformed
        // TODO add your handling code here:
         
             switch(pType){
                 case ORDERBILLING:      
                        orderController oc=new orderController();
                        DefaultTableModel modelReportViewer=(DefaultTableModel)tble_orderBilling.getModel();
                        TableRowSorter<TableModel> tableRowSorter=new TableRowSorter<TableModel>(modelReportViewer);
                        modelReportViewer.getDataVector().removeAllElements();
                        try {

                            List<order>Orders=oc.getOrdersBilling(OrderBillingEnum.Daily);
                            Stream s=Orders.stream();
                            s.forEach((x) -> {
                                 double totFilter=0.0;
                                 order o=(order)x;
                                modelReportViewer.insertRow(tble_orderBilling.getRowCount(),
                                        new Object[]{
                                        o.getFoood().getIdFood(),
                                        o.getFoood().getNameFood(),
                                        o.getQuantity(),
                                        o.getAmount(),
                                        o.getInvoice()+"-Fact",
                                        o.getBillingOrder().getIdRate().getCDF(),
                                        o.getBillingOrder().getDateBilling(),
                                        o.getBillingOrder().getServerName(),
                                        o.getUserID().getFullName(),
                                        o.getBillingOrder().getIdClient()

                                        });


                            });
                            double totFilter=0.0;
                            for(order o:Orders){
                                totFilter+=o.getAmount();
                            }
                            lblTot_filter.setText(Double.toString(totFilter));
                            obe=OrderBillingEnum.Daily;
                            tx_filter_cmd2.getDocument().addDocumentListener(new DocumentListener() {
                                @Override
                                public void insertUpdate(DocumentEvent e) {
                                   // DefaultTableModel modelReportViewer=(DefaultTableModel)tble_orderBilling.getModel();
                                    if (tx_filter_cmd2.getText().length()>0) {
                                        TableRowSorter<TableModel> tableRowSorter=new TableRowSorter<TableModel>(modelReportViewer);
                                        tble_orderBilling.setRowSorter(tableRowSorter);
                                         tableRowSorter.setRowFilter(RowFilter.regexFilter(tx_filter_cmd2.getText().toUpperCase()));

                                    }


                                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                                }

                                @Override
                                public void removeUpdate(DocumentEvent e) {
                                    if (tx_filter_cmd2.getText().length()>0) {
                                        //DefaultTableModel modelReportViewer=(DefaultTableModel)tble_orderBilling.getModel();
                                    TableRowSorter<TableModel> tableRowSorter=new TableRowSorter<TableModel>(modelReportViewer);
                                    tble_orderBilling.setRowSorter(tableRowSorter);
                                     tableRowSorter.setRowFilter(RowFilter.regexFilter(tx_filter_cmd2.getText().toUpperCase()));
                                    }else{
                                        modelReportViewer.getDataVector().removeAllElements();

                                    }
                                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                                }

                                @Override
                                public void changedUpdate(DocumentEvent e) {
                                   // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                                }
                            });


                        } catch (ClassNotFoundException ex) {
                            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (SQLException ex) {
                            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
                        }
                     break;
                 case BOOKING:
                               bookingController obController=new bookingController();
                        DefaultTableModel modelReportViewerBooking=(DefaultTableModel)tble_orderBilling.getModel();
                        TableRowSorter<TableModel> tableRowSorterBooking=new TableRowSorter<TableModel>(modelReportViewerBooking);
                        modelReportViewerBooking.getDataVector().removeAllElements();
                        try {

                            List<order>Orders=obController.getOrdersBilling(OrderBillingEnum.Daily);
                            Stream s=Orders.stream();
                            s.forEach((x) -> {
                                 double totFilter=0.0;
                                 order o=(order)x;
                                modelReportViewerBooking.insertRow(tble_orderBilling.getRowCount(),
                                        new Object[]{
                                        o.getFoood().getIdFood(),
                                        o.getFoood().getNameFood(),
                                        o.getQuantity(),
                                        o.getAmount(),
                                        o.getInvoice()+"-Fact",
                                        o.getBillingOrder().getIdRate().getCDF(),
                                        o.getBillingOrder().getDateBilling(),
                                        o.getBillingOrder().getServerName(),
                                        o.getUserID().getFullName(),
                                        o.getBillingOrder().getIdClient()

                                        });


                            });
                            double totFilter=0.0;
                            for(order o:Orders){
                                totFilter+=o.getAmount();
                            }
                            lblTot_filter.setText(Double.toString(totFilter));
                            obe=OrderBillingEnum.Daily;
                            tx_filter_cmd2.getDocument().addDocumentListener(new DocumentListener() {
                                @Override
                                public void insertUpdate(DocumentEvent e) {
                                   // DefaultTableModel modelReportViewer=(DefaultTableModel)tble_orderBilling.getModel();
                                    if (tx_filter_cmd2.getText().length()>0) {
                                        TableRowSorter<TableModel> tableRowSorter=new TableRowSorter<TableModel>(modelReportViewerBooking);
                                        tble_orderBilling.setRowSorter(tableRowSorter);
                                         tableRowSorter.setRowFilter(RowFilter.regexFilter(tx_filter_cmd2.getText().toUpperCase()));

                                    }


                                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                                }

                                @Override
                                public void removeUpdate(DocumentEvent e) {
                                    if (tx_filter_cmd2.getText().length()>0) {
                                        //DefaultTableModel modelReportViewer=(DefaultTableModel)tble_orderBilling.getModel();
                                    TableRowSorter<TableModel> tableRowSorter=new TableRowSorter<TableModel>(modelReportViewerBooking);
                                    tble_orderBilling.setRowSorter(tableRowSorter);
                                     tableRowSorter.setRowFilter(RowFilter.regexFilter(tx_filter_cmd2.getText().toUpperCase()));
                                    }else{
                                        modelReportViewerBooking.getDataVector().removeAllElements();

                                    }
                                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                                }

                                @Override
                                public void changedUpdate(DocumentEvent e) {
                                   // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                                }
                            });


                        } catch (ClassNotFoundException ex) {
                            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (SQLException ex) {
                            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
                        }
                     break;
                 default:
                     JOptionPane.showMessageDialog(null, "Selectionez Soit Resevation ou commandes");
                     break;
             }
         
             
    }//GEN-LAST:event_btnDailyActionPerformed

    private void tx_filter_cmd2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tx_filter_cmd2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tx_filter_cmd2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            // TODO add your handling code here:
            printerMethod(obe);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JRException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed
 
    private void printerMethod(OrderBillingEnum obe) throws ClassNotFoundException, SQLException, JRException{
       // JOptionPane.showMessageDialog(null, rd_action_cmd.isSelected());
        if (rd_action_cmd.isSelected()) {
              switch(obe){
            case Daily:
                        String path="";
               if (tble_orderBilling.getRowCount()>0) {
               LocalDate ld=LocalDate.now();
               String dateDaily=ld.getYear()+"-"+ld.getMonthValue()+"-"+ld.getDayOfMonth();
               SQLDB sqldb;
               try {
                   sqldb = new SQLDB();
                   Connection cnx=sqldb.getConnection();
                   Map<String,Object> map=new HashMap<>();
                   if (loginController._priority.equals("administrator")) {
                       map.put("date", dateDaily);
                       path =System.getProperty("user.dir")+"/src/com/openfoods/reports/rp_daily_orders.jasper";
                   }else{
                       map.put("date", dateDaily);
                       map.put("userid", loginController._idUser);
                       path =System.getProperty("user.dir")+"/src/com/openfoods/reports/rp_daily_orders_user.jasper";
                   }

                   JasperPrint jprint=JasperFillManager.fillReport(path, map, cnx);
                   JasperViewer.viewReport(jprint, false);

               } catch (ClassNotFoundException ex) {
                   Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
               } catch (SQLException ex) {
                               JOptionPane.showMessageDialog(null, ex.getMessage());

                   Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
               } catch (JRException ex) {
                   JOptionPane.showMessageDialog(null, ex.getMessage());
                   Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
               }
               } else {
                   JOptionPane.showMessageDialog(null, "Aucune données trouvée","Data not found",JOptionPane.WARNING_MESSAGE);
               }
                break;
               case Weekly:
                    //String path="";
        if (tble_orderBilling.getRowCount()>0) {
                LocalDate ld=LocalDate.now();
                String date_end=ld.getYear()+"-"+ld.getMonthValue()+"-"+ld.getDayOfMonth();
                LocalDate ld2=ld.minusDays(ld.getDayOfMonth());
                String date_begin=ld2.getYear()+"-"+ld2.getMonthValue()+"-"+ld2.getDayOfMonth();
                System.out.printf("Date 1:%s to date2:%s",date_begin,date_end);
        SQLDB sqldb;
        try {
            sqldb = new SQLDB();
            Connection cnx=sqldb.getConnection();
            Map<String,Object> map=new HashMap<>();
            if (loginController._priority.equals("administrator")) {
                                map.put("date_begin", date_begin);
                                map.put("date_end", date_end);
                path =System.getProperty("user.dir")+"/src/com/openfoods/reports/rp_weekly_orders.jasper";
            }else{
                 map.put("date_begin", date_begin);
                 map.put("date_end", date_end);
                 map.put("userid", loginController._idUser);
                path =System.getProperty("user.dir")+"/src/com/openfoods/reports/rp_weekly_orders_user.jasper";
            }
            
            JasperPrint jprint=JasperFillManager.fillReport(path, map, cnx);
            JasperViewer.viewReport(jprint, false);
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage());

            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JRException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
        } else {
            JOptionPane.showMessageDialog(null, "Aucune données trouvée","Data not found",JOptionPane.WARNING_MESSAGE);
        }
                   break;
                   
                   
               case Monthly:
                     if (tble_orderBilling.getRowCount()>0) {
                LocalDate ld=LocalDate.now();
                String month=Integer.toString(ld.getMonthValue());
                month=(month.length()==1?"0"+month:month);
        SQLDB sqldb;
        try {
            sqldb = new SQLDB();
            Connection cnx=sqldb.getConnection();
            Map<String,Object> map=new HashMap<>();
            if (loginController._priority.equals("administrator")) {
                                map.put("month", month);
                               // map.put("date_end", date_end);
                path =System.getProperty("user.dir")+"/src/com/openfoods/reports/rp_monthly_orders.jasper";
            }else{
                 map.put("month", month);
                 //map.put("date_end", date_end);
                 map.put("userid", loginController._idUser);
                path =System.getProperty("user.dir")+"/src/com/openfoods/reports/rp_monthly_orders_user.jasper";
            }
            
            JasperPrint jprint=JasperFillManager.fillReport(path, map, cnx);
            JasperViewer.viewReport(jprint, false);
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage());

            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JRException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
        } else {
            JOptionPane.showMessageDialog(null, "Aucune données trouvée","Data not found",JOptionPane.WARNING_MESSAGE);
        }
                       break;
               case FilterDate:
                   if (tble_orderBilling.getRowCount()>0) {
                LocalDate ld=LocalDate.now();
                String date_end=ld.getYear()+"-"+ld.getMonthValue()+"-"+ld.getDayOfMonth();
                LocalDate ld2=ld.minusDays(ld.getDayOfMonth());
                String date_begin=ld2.getYear()+"-"+ld2.getMonthValue()+"-"+ld2.getDayOfMonth();
                System.out.printf("Date 1:%s to date2:%s",date_begin,date_end);
        SQLDB sqldb;
        try {
            sqldb = new SQLDB();
            Connection cnx=sqldb.getConnection();
            Map<String,Object> map=new HashMap<>();
            if (loginController._priority.equals("administrator")) {
                                map.put("date_begin", date_begin);
                                map.put("date_end", date_end);
                path =System.getProperty("user.dir")+"/src/com/openfoods/reports/rp_choices_orders.jasper";
            }else{
                 map.put("date_begin", date_begin);
                 map.put("date_end", date_end);
                 map.put("userid", loginController._idUser);
                path =System.getProperty("user.dir")+"/src/com/openfoods/reports/rp_choices_orders_users.jasper";
            }
            
            JasperPrint jprint=JasperFillManager.fillReport(path, map, cnx);
            JasperViewer.viewReport(jprint, false);
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage());

            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JRException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
        } else {
            JOptionPane.showMessageDialog(null, "Aucune données trouvée","Data not found",JOptionPane.WARNING_MESSAGE);
        }
                       break;
        }
        }else if (rd_action_booking.isSelected()) {
               LocalDate ld=LocalDate.now();
               String dateDaily=ld.getYear()+"-"+ld.getMonthValue()+"-"+ld.getDayOfMonth();
               SQLDB sqldb=null;
               String path;
               sqldb = new SQLDB();
                   Connection cnx=sqldb.getConnection();
                   Map<String,Object> map=new HashMap<>();
                  map.put("date", dateDaily);
                  path =System.getProperty("user.dir")+"/src/com/openfoods/reports/rp_daily_booking.jasper";
                   JasperPrint jprint=JasperFillManager.fillReport(path, map, cnx);
                   JasperViewer.viewReport(jprint, false);
        }
    }
    private void btn_billing_weeklyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_billing_weeklyActionPerformed
        // TODO add your handling code here:
        
        obe=OrderBillingEnum.Weekly;
        orderController oc=new orderController();
        DefaultTableModel modelReportViewer=(DefaultTableModel)tble_orderBilling.getModel();
        TableRowSorter<TableModel> tableRowSorter=new TableRowSorter<TableModel>(modelReportViewer);
        modelReportViewer.getDataVector().removeAllElements();
        try {
          
            List<order>Orders=oc.getOrdersBilling(OrderBillingEnum.Weekly);
            Stream s=Orders.stream();
           // JOptionPane.showMessageDialog(null, Orders.size());
            s.forEach((x) -> {
                 double totFilter=0.0;
                 order o=(order)x;
                modelReportViewer.insertRow(tble_orderBilling.getRowCount(),
                        new Object[]{
                        o.getFoood().getIdFood(),
                        o.getFoood().getNameFood(),
                        o.getQuantity(),
                        o.getAmount(),
                        o.getInvoice()+"-Fact",
                        o.getBillingOrder().getIdRate().getCDF(),
                        o.getBillingOrder().getDateBilling(),
                        o.getBillingOrder().getServerName(),
                        o.getUserID().getFullName(),
                        o.getBillingOrder().getIdClient()
                        
                        });
              
              
            });
            double totFilter=0.0;
            for(order o:Orders){
                totFilter+=o.getAmount();
            }
            lblTot_filter.setText(Double.toString(totFilter));
           // obe=OrderBillingEnum.Daily;
            tx_filter_cmd2.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                   // DefaultTableModel modelReportViewer=(DefaultTableModel)tble_orderBilling.getModel();
                    if (tx_filter_cmd2.getText().length()>0) {
                        TableRowSorter<TableModel> tableRowSorter=new TableRowSorter<TableModel>(modelReportViewer);
                        tble_orderBilling.setRowSorter(tableRowSorter);
                         tableRowSorter.setRowFilter(RowFilter.regexFilter(tx_filter_cmd2.getText().toUpperCase()));
                        
                    }

                    
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    if (tx_filter_cmd2.getText().length()>0) {
                        //DefaultTableModel modelReportViewer=(DefaultTableModel)tble_orderBilling.getModel();
                    TableRowSorter<TableModel> tableRowSorter=new TableRowSorter<TableModel>(modelReportViewer);
                    tble_orderBilling.setRowSorter(tableRowSorter);
                     tableRowSorter.setRowFilter(RowFilter.regexFilter(tx_filter_cmd2.getText().toUpperCase()));
                    }else{
                        modelReportViewer.getDataVector().removeAllElements();
                        
                    }
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                   // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            });
           
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
      
    }//GEN-LAST:event_btn_billing_weeklyActionPerformed
        DefaultTableModel modelUsers,modelRates=null;
        int rowcurrent=0,colsCurrent=0;
        DefaultTableModel modelViewer=null;
    private void child_configStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_child_configStateChanged
        try {
            // TODO add your handling code here:
          
            child_config=(JTabbedPane)evt.getSource();
            int index=child_config.getSelectedIndex();
            String tabeChildTitle=child_config.getTitleAt(index);
            switch(tabeChildTitle){
                case "Plats":
                //List<order> Orders=new orderController().getOrdersBilling(OrderBillingEnum.Daily)
                List<food>Foods=new productController().getListProducts();
                Stream sFoods=Foods.stream();
                modelViewer=(DefaultTableModel)tble_product_viewer.getModel();
                tx_filter_cmd_viewer.getDocument().addDocumentListener(new DocumentListener() {
                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        TableRowSorter<TableModel> tableRowSorter=new TableRowSorter<TableModel>(modelViewer);
                        tble_product_viewer.setRowSorter(tableRowSorter);
                        tableRowSorter.setRowFilter(RowFilter.regexFilter(tx_filter_cmd_viewer.getText().toUpperCase()));
                        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        TableRowSorter<TableModel> tableRowSorter=new TableRowSorter<TableModel>(modelViewer);
                        tble_product_viewer.setRowSorter(tableRowSorter);
                        tableRowSorter.setRowFilter(RowFilter.regexFilter(tx_filter_cmd_viewer.getText().toUpperCase()));
                        //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }

                    @Override
                    public void changedUpdate(DocumentEvent e) {
                        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }
                });
                JComboBox JListTypeFood=new JComboBox(new Object[]{"Accompagnement","Boisson/Liqueur","Plats"});
                TableColumn tableColumn=tble_product_viewer.getColumnModel().getColumn(2);
                tableColumn.setCellEditor(new DefaultCellEditor(JListTypeFood));
                sFoods.forEach((t) -> {
                    food f=(food)t;
                    String typeFood="";
                    switch(f.getTypeFood()){
                        case "BL":
                        typeFood="Boisson/Liqueur";
                        break;
                        case "PL":
                        typeFood="Plats";
                        break;
                        case "AC":
                        typeFood="Accompagnement";
                        break;
                    }
                    modelViewer.insertRow(tble_product_viewer.getRowCount(), new Object[]{
                        f.getIdFood(),
                        f.getNameFood(),
                        typeFood,
                        f.getPriceFood()
                    });
                });
                tble_product_viewer.getModel().addTableModelListener(new TableModelListener() {
                @Override
                public void tableChanged(TableModelEvent e) {
                    int row=e.getFirstRow();
                    int cols=e.getColumn();
                   //JOptionPane.showMessageDialog(null, "row:"+row+", Col:"+cols);
                   
                    if (cols>0) {
                        try {
                            String typeFood=tble_product_viewer.getValueAt(row, 2).toString();
                            int idProduct=Integer.parseInt(tble_product_viewer.getValueAt(row, 0).toString());
                            String nameProduct=tble_product_viewer.getValueAt(row, 1).toString();
                            Double pu=0.0;
                            switch(typeFood){
                                case "Boisson/Liqueur":
                                    typeFood="BL";
                                    break;
                                case "Plats":
                                    typeFood="PL";
                                    break;
                                case "Accompagnement":
                                    typeFood="AC";
                                    break;
                            }
                            Map<String,Object> map=tryDouble(tble_product_viewer.getValueAt(row, 3).toString());
                            if (Boolean.parseBoolean(map.get("status").toString())) {
                                
                                pu=Double.parseDouble(map.get("data").toString());
                                food f=new food(idProduct, nameProduct, pu, typeFood, 1);
                               // JOptionPane.showMessageDialog(null, f.getTypeFood());
                                boolean b=new productController().updateProduct(f);
                                switch(typeFood){
                                    case "AC":
                                        List<food>lf=new productController().getListProducts();
                                        Stream strm=lf.stream();
                                        modelAccompa.getDataVector().removeAllElements();
                                        strm.forEach((t) -> {
                                            food fd=(food)t;
                                            if (fd.getTypeFood().toUpperCase().equals("AC")) {
                                                 modelAccompa.insertRow(tble_accomp.getRowCount(), new Object[]{
                                               fd.getIdFood(),
                                                fd.getNameFood(),
                                                fd.getPriceFood(),
                                                false
                                            });
                                            }
                                           
                                        });
                                        break;
                                        
                                        
                                        case "PL":
                                        List<food>lf2=new productController().getListProducts();
                                        Stream strm2=lf2.stream();
                                        modelPlat.getDataVector().removeAllElements();
                                        strm2.forEach((t) -> {
                                            food fd=(food)t;
                                            if (fd.getTypeFood().toUpperCase().equals("PL")) {
                                                 modelPlat.insertRow(tble_plat.getRowCount(), new Object[]{
                                               fd.getIdFood(),
                                                fd.getNameFood(),
                                                fd.getPriceFood(),
                                                false
                                            });
                                            }
                                           
                                        });
                                        break;
                                        case "BL":
                                        List<food>lf3=new productController().getListProducts();
                                        Stream strm3=lf3.stream();
                                        model.getDataVector().removeAllElements();
                                        strm3.forEach((t) -> {
                                            food fd=(food)t;
                                            if (fd.getTypeFood().toUpperCase().equals("BL")) {
                                                 model.insertRow(tble_product.getRowCount(), new Object[]{
                                               fd.getIdFood(),
                                                fd.getNameFood(),
                                                fd.getPriceFood(),
                                                false
                                            });
                                            }
                                           
                                        });
                                        break;
                                }
                                
                            }
                     
                        } catch (ClassNotFoundException ex) {
                            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (SQLException ex) {
                            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            });
                break;

                case "Utilisateurs":
                 modelUsers=(DefaultTableModel)tble_users.getModel();
                List<user> users=new userController().getUsers();
                Stream userStream=users.stream();
                userStream.forEach((t->{
                    user u=(user)t;
                    modelUsers.insertRow(tble_users.getRowCount(), new Object[]{u.getIdUser(),u.getName(),u.getFullName(),u.getPriority()});
                }));
                break;
                case "Taux de change":
                    modelRates=(DefaultTableModel)tble_rates.getModel();
                    List<rates>rt=new rateController().getRates();
                    
                    Stream streamRates=rt.stream();
                    streamRates.forEach((t) -> {
                        
                        rates rt2=(rates)t;
                        //OptionPane.showMessageDialog(null, rt2.getCDF());
                        modelRates.insertRow(tble_rates.getRowCount(),new Object[]{
                                rt2.idRate(),
                                1,
                                rt2.getCDF()

                            }
                                                                    
                                );
                    });
                    break;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_child_configStateChanged

    private void btn_add_userActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_add_userActionPerformed
        try {
            // TODO add your handling code here:
            
            String priority="";
            switch(cb_role.getSelectedItem().toString()){
                case "Administrateur":
                priority="administrator";
                break;
                default:
                priority="user";
                break;
            }
            com.openfoods.models.user uid=new com.openfoods.models.user(
                0,
                tx_username.getText(),
                tx_username_full.getText(),
                tx_passwor_create.getText().toString(),
                priority);
            int id=new userController().addUser(uid);
            if(id>0){
                modelUsers.insertRow(tble_users.getRowCount(), new Object[]{
                    id,
                    tx_username.getText(),
                    tx_username_full.getText(),
                    cb_role.getSelectedItem().toString()
                });
                JOptionPane.showMessageDialog(null, "Utilisateur crée avec succes","Add user",JOptionPane.INFORMATION_MESSAGE);
                tx_username.setText("");
                tx_username_full.setText("");
                tx_passwor_create.setText("");
                cb_role.setSelectedIndex(0);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_add_userActionPerformed

    private void btn_create_rateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_create_rateActionPerformed
        // TODO add your handling code here:
        Map<String,Object> b=tryDouble(tx_amount_rate.getText());
        if (Boolean.parseBoolean(b.get("status").toString())) {
            try {
                double data=Double.parseDouble(b.get("data").toString());
                rates rt=new rates(0, 1, data);
                int i=new rateController().addRate(rt);
                if (i>0) {
                    JOptionPane.showMessageDialog(null, "Taux ajouté");
                    loginController.idRate=i;
                    loginController.taux=data;
                    lb_taux.setText("Taux CDF:"+data);
                }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }//GEN-LAST:event_btn_create_rateActionPerformed

    private void btn_print_menuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_print_menuActionPerformed
        try {
            // TODO add your handling code here:
            String path=System.getProperty("user.dir")+"/src/com/openfoods/reports/rp_list_foods.jasper";
            JasperPrint jprint=JasperFillManager.fillReport(path, null, new SQLDB().getConnection());
            JasperViewer.viewReport(jprint, false);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JRException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_print_menuActionPerformed

    private void btn_add_productActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_add_productActionPerformed
        // TODO add your handling code here:
        Map map=this.tryDouble(tx_PU.getText());
        String typeFood="";

        switch(cb_typeFood.getSelectedItem().toString()){
            case "Accompagnement":
            typeFood="AC";
            break;
            case "Boisson/Liqueur":
            typeFood="BL";
            break;
            case "Repas":
            typeFood="PL";
            break;
        }
        if(Boolean.parseBoolean(map.get("status").toString())==true){
            try {
                //JOptionPane.showMessageDialog(null, cb_typeFood.getSelectedItem().toString());
                food f=new food(0, tx_nameProduct.getText().toUpperCase(), Double.parseDouble(map.get("data").toString()), typeFood, 1);
                com.openfoods.controllers.productController pController=new productController();
                Map<String,Object> mapper=pController.addProduct(f);
                boolean b=Boolean.parseBoolean(mapper.get("status").toString());
                if (b) {
                    JOptionPane.showMessageDialog(null, "Produit ajouté","Add product",JOptionPane.INFORMATION_MESSAGE);
                                        DefaultTableModel modelProd=null;
                                        modelProd=(DefaultTableModel)tble_product_viewer.getModel();
                                        modelProd=(DefaultTableModel)tble_product_viewer.getModel();
                                        modelProd.insertRow(tble_product_viewer.getRowCount(), new Object[]{
                                            mapper.get("key").toString(),
                                            f.getNameFood(),
                                           cb_typeFood.getSelectedItem().toString(),
                                            f.getPriceFood()
                                            });

                switch(cb_typeFood.getSelectedItem().toString()){
                    case "Accompagnement":
                    typeFood="AC";
                    model=(DefaultTableModel)tble_accomp.getModel();
                    model.insertRow(tble_accomp.getRowCount(), new Object[]{
                        mapper.get("key").toString(),
                        f.getNameFood(),
                        f.getPriceFood(),
                        false

                    });
                    break;
                    case "Boisson/Liqueur":

                    model=(DefaultTableModel)tble_product.getModel();
                    model.insertRow(tble_product.getRowCount(), new Object[]{
                        mapper.get("key").toString(),
                        f.getNameFood(),
                        f.getPriceFood(),
                        false

                    });
                    break;
                    case "Repas":
                    model=(DefaultTableModel)tble_plat.getModel();
                    model.insertRow(tble_plat.getRowCount(), new Object[]{
                        mapper.get("key").toString(),
                        f.getNameFood(),
                        f.getPriceFood(),
                        false

                    });
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
        }

    }//GEN-LAST:event_btn_add_productActionPerformed

    private void tx_PUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tx_PUActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tx_PUActionPerformed

    private void tx_nameProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tx_nameProductActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tx_nameProductActionPerformed

    private void btn_update_productActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_update_productActionPerformed
        try {
            // TODO add your handling code here:
            String category=cb_categories_food.getSelectedItem().toString();
            double amountAdded=Double.parseDouble(sp_amount.getValue().toString());
           // JOptionPane.showMessageDialog(null, amountAdded);
            switch(category){
                case "Accompagnement":
                    category="AC";
                    break;
                case "Boisson/Liqueur":
                    category="BL";
                    break;
                case "Plats":
                    category="PL";
                    break;
                    
            }
              String operation=(rd_add.isSelected()?"+":"-");
            if (!category.trim().equals("") && amountAdded>0 && !operation.equals("")) {
              
                System.out.println("Operation:"+operation+", Category:"+category+",Amount:"+amountAdded);
                 boolean b=new productController().updateProductByCategory(category, amountAdded,operation);
                if (b) {
                    String message="";
                    if (operation.equals("+")) {
                        message="Vous avez ajouté "+amountAdded+" à la categorie "+cb_categories_food.getSelectedItem().toString();
                    }else{
                        message="Vous avez retranché "+amountAdded+" pour categorie "+cb_categories_food.getSelectedItem().toString();
                    }
                    JOptionPane.showMessageDialog(null, message,"Update price",JOptionPane.INFORMATION_MESSAGE);
                    modelViewer.getDataVector().removeAllElements();
                    model.getDataVector().removeAllElements();
                    modelAccompa.getDataVector().removeAllElements();
                    modelPlat.getDataVector().removeAllElements();
                     List<food>Foods=new productController().getListProducts();
                    Stream sFoods=Foods.stream();
                            JComboBox JListTypeFood=new JComboBox(new Object[]{"Accompagnement","Boisson/Liqueur","Plats"});
                TableColumn tableColumn=tble_product_viewer.getColumnModel().getColumn(2);
                tableColumn.setCellEditor(new DefaultCellEditor(JListTypeFood));
                sFoods.forEach((t) -> {
                    food f=(food)t;
                    String typeFood="";
                    switch(f.getTypeFood()){
                        case "BL":
                        typeFood="Boisson/Liqueur";
                        break;
                        case "PL":
                        typeFood="Plats";
                        break;
                        case "AC":
                        typeFood="Accompagnement";
                        break;
                    }
                    modelViewer.insertRow(tble_product_viewer.getRowCount(), new Object[]{
                        f.getIdFood(),
                        f.getNameFood(),
                        typeFood,
                        f.getPriceFood()
                    });
                });
                
         model =(DefaultTableModel)tble_product.getModel();
         List<food> listFoods=new productController().getListProducts();
            for(food f:listFoods){
                if (f.getTypeFood().equals("BL")) {
                      model.insertRow(model.getRowCount(), new Object[]{f.getIdFood(),f.getNameFood(),f.getPriceFood(),false});
                }else if(f.getTypeFood().equals("PL")){
                    modelPlat=(DefaultTableModel)tble_plat.getModel();
                    modelPlat.insertRow(modelPlat.getRowCount(),new Object[]{f.getIdFood(),f.getNameFood(),f.getPriceFood(),false});
                }else{
                    modelAccompa=(DefaultTableModel)tble_accomp.getModel();
                    modelAccompa.insertRow(modelAccompa.getRowCount(),new Object[]{f.getIdFood(),f.getNameFood(),f.getPriceFood(),false});
                }
                
               

            }
                }
            }else{
                JOptionPane.showMessageDialog(null, "Verifie la saisie des donnees","Error Input",JOptionPane.ERROR_MESSAGE);
            }
           
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_update_productActionPerformed

    private void parent_tabbedStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_parent_tabbedStateChanged
        // TODO add your handling code here:
            parent_tabbed=(JTabbedPane)evt.getSource();
            int index=parent_tabbed.getSelectedIndex();
            String tabTitle=parent_tabbed.getTitleAt(index);
            if (tabTitle.equals("Deconnexion")) {
                //int i=JOptionPane.showMessageDialog(null, "Test","Test",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
            int response=JOptionPane.showConfirmDialog(null,"Êtes-vous sûr de quitter?", "Out System", JOptionPane.YES_NO_OPTION);
                if (response==JOptionPane.YES_OPTION) {
                    this.dispose();
                    LoginForm login=new LoginForm();
                    login.setLocationRelativeTo(null);
                    login.setVisible(true);
                }
        }
    }//GEN-LAST:event_parent_tabbedStateChanged

    private void btn_blling_monthlyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_blling_monthlyActionPerformed
        // TODO add your handling code here:
        obe=OrderBillingEnum.Monthly;
        orderController oc=new orderController();
        DefaultTableModel modelReportViewer=(DefaultTableModel)tble_orderBilling.getModel();
        TableRowSorter<TableModel> tableRowSorter=new TableRowSorter<TableModel>(modelReportViewer);
        modelReportViewer.getDataVector().removeAllElements();
        try {
          
            List<order>Orders=oc.getOrdersBilling(OrderBillingEnum.Monthly);
            Stream s=Orders.stream();
           // JOptionPane.showMessageDialog(null, Orders.size());
            s.forEach((x) -> {
                 double totFilter=0.0;
                 order o=(order)x;
                modelReportViewer.insertRow(tble_orderBilling.getRowCount(),
                        new Object[]{
                        o.getFoood().getIdFood(),
                        o.getFoood().getNameFood(),
                        o.getQuantity(),
                        o.getAmount(),
                        o.getInvoice()+"-Fact",
                        o.getBillingOrder().getIdRate().getCDF(),
                        o.getBillingOrder().getDateBilling(),
                        o.getBillingOrder().getServerName(),
                        o.getUserID().getFullName(),
                        o.getBillingOrder().getIdClient()
                        
                        });
              
              
            });
            double totFilter=0.0;
            for(order o:Orders){
                totFilter+=o.getAmount();
            }
            lblTot_filter.setText(Double.toString(totFilter));
           // obe=OrderBillingEnum.Daily;
            tx_filter_cmd2.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                   // DefaultTableModel modelReportViewer=(DefaultTableModel)tble_orderBilling.getModel();
                    if (tx_filter_cmd2.getText().length()>0) {
                        TableRowSorter<TableModel> tableRowSorter=new TableRowSorter<TableModel>(modelReportViewer);
                        tble_orderBilling.setRowSorter(tableRowSorter);
                         tableRowSorter.setRowFilter(RowFilter.regexFilter(tx_filter_cmd2.getText().toUpperCase()));
                        
                    }

                    
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    if (tx_filter_cmd2.getText().length()>0) {
                        //DefaultTableModel modelReportViewer=(DefaultTableModel)tble_orderBilling.getModel();
                    TableRowSorter<TableModel> tableRowSorter=new TableRowSorter<TableModel>(modelReportViewer);
                    tble_orderBilling.setRowSorter(tableRowSorter);
                     tableRowSorter.setRowFilter(RowFilter.regexFilter(tx_filter_cmd2.getText().toUpperCase()));
                    }else{
                        modelReportViewer.getDataVector().removeAllElements();
                        
                    }
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                   // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            });
           
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
      
    }//GEN-LAST:event_btn_blling_monthlyActionPerformed

    private void btn_blling_choicesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_blling_choicesActionPerformed
        // TODO add your handling code here:
        String dt1=dpicker1.getText().replace('/','-');
        String dt2=dpicker1.getText().replace('/','-');
              dt1="20"+dt1.split("-")[2]+"-"+dt1.split("-")[1]+"-"+(dt1.split("-")[0].length()==1?"0"+dt1.split("-")[0]:dt1.split("-")[0]);
              dt2="20"+dt2.split("-")[2]+"-"+dt2.split("-")[1]+"-"+(dt2.split("-")[0].length()==1?"0"+dt2.split("-")[0]:dt2.split("-")[0]);
         obe=OrderBillingEnum.FilterDate;
        orderController oc=new orderController();
        oc.dt1=dt1;
        oc.dt2=dt2;
        DefaultTableModel modelReportViewer=(DefaultTableModel)tble_orderBilling.getModel();
        TableRowSorter<TableModel> tableRowSorter=new TableRowSorter<TableModel>(modelReportViewer);
        modelReportViewer.getDataVector().removeAllElements();
        try {
          
            List<order>Orders=oc.getOrdersBilling(OrderBillingEnum.FilterDate);
            Stream s=Orders.stream();
           // JOptionPane.showMessageDialog(null, Orders.size());
            s.forEach((x) -> {
                 double totFilter=0.0;
                 order o=(order)x;
                modelReportViewer.insertRow(tble_orderBilling.getRowCount(),
                        new Object[]{
                        o.getFoood().getIdFood(),
                        o.getFoood().getNameFood(),
                        o.getQuantity(),
                        o.getAmount(),
                        o.getInvoice()+"-Fact",
                        o.getBillingOrder().getIdRate().getCDF(),
                        o.getBillingOrder().getDateBilling(),
                        o.getBillingOrder().getServerName(),
                        o.getUserID().getFullName(),
                        o.getBillingOrder().getIdClient()
                        
                        });
              
              
            });
            double totFilter=0.0;
            for(order o:Orders){
                totFilter+=o.getAmount();
            }
            lblTot_filter.setText(Double.toString(totFilter));
           // obe=OrderBillingEnum.Daily;
            tx_filter_cmd2.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                   // DefaultTableModel modelReportViewer=(DefaultTableModel)tble_orderBilling.getModel();
                    if (tx_filter_cmd2.getText().length()>0) {
                        TableRowSorter<TableModel> tableRowSorter=new TableRowSorter<TableModel>(modelReportViewer);
                        tble_orderBilling.setRowSorter(tableRowSorter);
                         tableRowSorter.setRowFilter(RowFilter.regexFilter(tx_filter_cmd2.getText().toUpperCase()));
                        
                    }

                    
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    if (tx_filter_cmd2.getText().length()>0) {
                        //DefaultTableModel modelReportViewer=(DefaultTableModel)tble_orderBilling.getModel();
                    TableRowSorter<TableModel> tableRowSorter=new TableRowSorter<TableModel>(modelReportViewer);
                    tble_orderBilling.setRowSorter(tableRowSorter);
                     tableRowSorter.setRowFilter(RowFilter.regexFilter(tx_filter_cmd2.getText().toUpperCase()));
                    }else{
                        modelReportViewer.getDataVector().removeAllElements();
                        
                    }
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                   // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            });
           
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
      
    }//GEN-LAST:event_btn_blling_choicesActionPerformed

    private void rd_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rd_addActionPerformed
        // TODO add your handling code here:
        if (rd_suppr.isRequestFocusEnabled()) {
            rd_suppr.setSelected(false);
        }
    }//GEN-LAST:event_rd_addActionPerformed

    private void rd_supprActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rd_supprActionPerformed
        // TODO add your handling code here:
        if (rd_add.isRequestFocusEnabled()) {
            rd_add.setSelected(false);
        }
    }//GEN-LAST:event_rd_supprActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
       // modelCmd=(DefaultTableModel)tble_command.getModel();
       this.clearOrders();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void btn_add_reservationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_add_reservationActionPerformed
        // TODO add your handling code here:
        if (tble_command.getRowCount()==0) {
            JOptionPane.showMessageDialog(null, "Aucune commande n'a été séléctionnée","pas de commande",JOptionPane.ERROR_MESSAGE);
        } else {
            if (tx_serverName.getText().equals("") || cb_client.getSelectedItem().toString()=="") {
                
               JOptionPane.showMessageDialog(null, "Séléctionnez le nom du serveur et/ou la table du client","Information requise",JOptionPane.ERROR_MESSAGE);
                
            } else {
            
       btn_add_reservation.setEnabled(false);
        String cboClient=cb_client.getSelectedItem().toString();
        orderController oController=new orderController();
        LocalDate ld=LocalDate.now();
        String dateBilling=ld.getYear()+"-"+ld.getMonthValue()+"-"+ld.getDayOfMonth();
        billing bl=new billing(cboClient,
                new rates(1,loginController.idRate,loginController.taux),
                Double.parseDouble(tx_tot_paie.getText()),
                tx_serverName.getText(),dateBilling
        );
        billingController bController = null;
        try {
            bController = new billingController(bl,ActionType.BOOKING);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
           String amount =JOptionPane.showInputDialog(null, "Entrez le montant perçu pour la reservation");
           bController.amountBooking=Double.parseDouble(amount);
            bl=bController.executeUpdateBilling();
            System.out.println("Id Billing:"+bl.getIdBilling());
        } catch (SQLException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        List<order>orders=new LinkedList<>();
        for (int i = 0; i < tble_command.getRowCount(); i++) {
            
            food f=new food(
                    (int)tble_command.getValueAt(i, 0), 
                    tble_command.getValueAt(i, 1).toString()
                    , (double) tble_command.getValueAt(i, 2),
                    "", 
                    1);
            
           // foods.add(f);
          order orderPaie=new order(
                  f,
                  Integer.parseInt(tble_command.getValueAt(i, 3).toString()), 
                  Double.parseDouble(tble_command.getValueAt(i, 4).toString()),
                  loginController._idUser, 
                  bl.getIdBilling(), 
                  tx_serverName.getText());
                  orders.add(orderPaie);
          
          

        }
                try {
                   
                    boolean responseOrders=oController.addOrder(orders,ActionType.BOOKING);
                      if (responseOrders) {
                JOptionPane.showMessageDialog(null, "Reservation enregistrée avec succes!","Save Order",JOptionPane.INFORMATION_MESSAGE);
                jButton2.setEnabled(true);
                                

                String path=  path =System.getProperty("user.dir")+"/src/com/openfoods/reports/rp_print_booking-order.jasper";
                 SQLDB sqldb = new SQLDB();
                   Connection cnx=sqldb.getConnection();
                   Map<String,Object> map=new HashMap<>();
                  
                 map.put("idTicket", bl.getIdBilling());
                   JasperPrint jprint=JasperFillManager.fillReport(path, map, cnx);
                   JasperViewer.viewReport(jprint, false);
                   this.clearOrders();
                   btn_add_reservation.setEnabled(true);

                
            }
                } catch (ClassNotFoundException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                    Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                                        JOptionPane.showMessageDialog(null, ex.getMessage());

                    Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
                } catch (JRException ex) {
                    Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
                }
        
            }
        }
          
    }//GEN-LAST:event_btn_add_reservationActionPerformed

    private void rd_action_cmdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rd_action_cmdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rd_action_cmdActionPerformed

    private void rd_action_cmdStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_rd_action_cmdStateChanged
        // TODO add your handling code here:
        if (!rd_action_cmd.isSelected()) {
           rd_action_booking.setSelected(false);
           pType=PrintingType.ORDERBILLING;
        }
    }//GEN-LAST:event_rd_action_cmdStateChanged

    private void rd_action_bookingStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_rd_action_bookingStateChanged
        // TODO add your handling code here:
        if (!rd_action_booking.isSelected()) {
            rd_action_cmd.setSelected(false);
            pType=PrintingType.BOOKING;
        }
    }//GEN-LAST:event_rd_action_bookingStateChanged
    DefaultTableModel modelCancelBooking=null;
    private void btn_cancel_bookingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cancel_bookingActionPerformed
        // TODO add your handling code here:
        trashController trash=new trashController();
        bookingController bController=new bookingController();
        modelCancelBooking=(DefaultTableModel) tble_cancel_booking.getModel();
        
        try {
            int code=Integer.parseInt(tx_search_cancel_invoice.getText());
            List<Map<String,Object>> list=bController.getBookingToCancel(code);
            if (list.size()>0) {
                btn_cancel_booking_order.setEnabled(true);
            } else {
                btn_cancel_booking_order.setEnabled(false);
            }
            double totalCancel=0.0;
            modelCancelBooking.getDataVector().removeAllElements();
            for (Map object : list) {
                 modelCancelBooking.insertRow(
                         modelCancelBooking.getRowCount(), 
                         new Object[]
                         {
                             object.get("datecmd"),
                             object.get("idprod"),
                             object.get("nameprod"),
                             object.get("qte"),
                             object.get("price")
                         }
                 );
                 totalCancel+=(Integer.parseInt(object.get("qte").toString())*Double.parseDouble(object.get("price").toString()));
            }
            lbl_total_cancel.setText("Total reservation : "+totalCancel);
            actionType=ActionType.BOOKING;
            trashController.arrayData=list;
            
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_cancel_bookingActionPerformed

    private void btn_cancel_booking_orderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cancel_booking_orderActionPerformed
        try {
            // TODO add your handling code here:
            trashController trash=new trashController();
           trash.setDataInTrash(actionType);
            modelCancelBooking=(DefaultTableModel) tble_cancel_booking.getModel();
            modelCancelBooking.getDataVector().removeAllElements();
            tx_search_cancel_invoice.setText("");
            btn_cancel_booking_order.setEnabled(false);
             lbl_total_cancel.setText("Total : ");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_cancel_booking_orderActionPerformed

    private ActionType actionType;
    private void btn_cancel_orderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cancel_orderActionPerformed
        // TODO add your handling code here:
        trashController trash=new trashController();
        orderController oController=new orderController();
        DefaultTableModel modelCancelOrder=(DefaultTableModel)tble_cancel_booking.getModel();
        try {
            List<Map<String,Object>>list=oController.getOrdeToCancel(Integer.parseInt(tx_search_cancel_invoice.getText()));
            System.out.println(list.size());
            if (list.size()>0) {
                btn_cancel_booking_order.setEnabled(true);
            } else {
                btn_cancel_booking_order.setEnabled(false);
            }
             double totalCancel=0.0;
            modelCancelOrder.getDataVector().removeAllElements();
            for (Map object : list) {
                 modelCancelOrder.insertRow(
                         modelCancelOrder.getRowCount(), 
                         new Object[]
                         {
                             object.get("datecmd"),
                             object.get("idprod"),
                             object.get("nameprod"),
                             object.get("qte"),
                             object.get("price")
                         }
                 );
                 totalCancel+=(Integer.parseInt(object.get("qte").toString())*Double.parseDouble(object.get("price").toString()));
            }
            lbl_total_cancel.setText("Total commande : "+totalCancel);
            actionType=ActionType.ORDER;
            trashController.arrayData=list;
            trash.setDataInTrash(actionType);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_cancel_orderActionPerformed

    private void clearOrders(){
           //
             int MAX_SIZE=modelCmd.getRowCount();
        //JOptionPane.showMessageDialog(null,"Sizer:"+MAX_SIZE+"Table count:"+tble_command.getRowCount());
       try{
            while(MAX_SIZE>-1){
            MAX_SIZE=MAX_SIZE-1;
            modelCmd.removeRow(MAX_SIZE);
            
        } 
       }catch(Exception e){
                   tx_tot_paie.setText("0.0");
       }
       model =(DefaultTableModel)tble_product.getModel();
       model.getDataVector().removeAllElements();
       modelPlat.getDataVector().removeAllElements();
       modelAccompa.getDataVector().removeAllElements();
           tx_serverName.setText("");
           cb_client.setSelectedIndex(0);
           tx_tot_paie.setText("0.0");
           totPaie=0.0;
           this.reInitializer();
//            for(food f:loginController.listFoods){
//                if (f.getTypeFood().equals("BL")) {
//                      model.insertRow(model.getRowCount(), new Object[]{f.getIdFood(),f.getNameFood(),f.getPriceFood(),false});
//                }else if(f.getTypeFood().equals("PL")){
//                    modelPlat=(DefaultTableModel)tble_plat.getModel();
//                    modelPlat.insertRow(modelPlat.getRowCount(),new Object[]{f.getIdFood(),f.getNameFood(),f.getPriceFood(),false});
//                }else{
//                    modelAccompa=(DefaultTableModel)tble_accomp.getModel();
//                    modelAccompa.insertRow(modelAccompa.getRowCount(),new Object[]{f.getIdFood(),f.getNameFood(),f.getPriceFood(),false});
//                }
//                
//               
//
//            }
    }
    public Dashboard(JButton jButton1, JButton jButton2, JButton jButton3, JButton jButton4, JButton jButton5, JButton jButton6, JButton jButton7, JButton jButton8, JLabel jLabel1, JPanel jPanel1, JPanel jPanel2, JPanel jPanel3, JPanel jPanel4, JPanel jPanel5, JPanel jPanel6, JPanel jPanel7, JPanel jPanel8, JPanel jPanel9, JScrollPane jScrollPane2, JTabbedPane jTabbedPane1, JTabbedPane jTabbedPane2, JLabel lb_taux, JLabel lbl_timer, JLabel lbl_user, JTable tble_product) throws HeadlessException {
      //  this.btn_save_billing = jButton1;
        this.jButton2 = jButton2;
  //      this.jButton3 = jButton3;
       // this.dailyReport = jButton5;
        this.btn_billing_weekly = jButton6;
        this.btn_blling_monthly = jButton7;
     //   this.jButton8 = jButton8;
        this.jLabel1 = jLabel1;
        this.jPanel1 = jPanel1;
        this.jPanel2 = jPanel2;
        this.jPanel3 = jPanel3;
       // this.jPanel4 = jPanel4;
        this.jPanel5 = jPanel5;
       // this.jPanel6 = jPanel6;
      //  this.jPanel7 = jPanel7;
        this.jPanel8 = jPanel8;
        this.jPanel9 = jPanel9;
        this.jScrollPane2 = jScrollPane2;
        this.parent_tabbed = jTabbedPane1;
        this.child_config = jTabbedPane2;
        this.lb_taux = lb_taux;
        this.lbl_timer = lbl_timer;
        this.lbl_user = lbl_user;
        this.tble_product = tble_product;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Dashboard().setVisible(true);
            }
        });
    }
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDaily;
    private javax.swing.JButton btn_add_product;
    private javax.swing.JButton btn_add_reservation;
    private javax.swing.JButton btn_add_user;
    private javax.swing.JButton btn_billing_weekly;
    private javax.swing.JButton btn_blling_choices;
    private javax.swing.JButton btn_blling_monthly;
    private javax.swing.JButton btn_cancel_booking;
    private javax.swing.JButton btn_cancel_booking_order;
    private javax.swing.JButton btn_cancel_order;
    private javax.swing.JButton btn_create_rate;
    private javax.swing.JButton btn_print_menu;
    private javax.swing.JButton btn_update_product;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.JComboBox<String> cb_categories_food;
    private javax.swing.JComboBox<String> cb_client;
    private javax.swing.JComboBox<String> cb_role;
    private javax.swing.JComboBox<String> cb_typeFood;
    private javax.swing.JTabbedPane child_config;
    private datechooser.beans.DateChooserCombo dpicker1;
    private datechooser.beans.DateChooserCombo dpicker2;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel lb_taux;
    private javax.swing.JLabel lblTot_filter;
    private javax.swing.JLabel lbl_timer;
    private javax.swing.JLabel lbl_total_cancel;
    private javax.swing.JLabel lbl_user;
    private javax.swing.JLabel lbltotf;
    private javax.swing.JTabbedPane parent_tabbed;
    private javax.swing.JRadioButton rd_action_booking;
    private javax.swing.JRadioButton rd_action_cmd;
    private javax.swing.JRadioButton rd_add;
    private javax.swing.JRadioButton rd_suppr;
    private javax.swing.JSpinner sp_amount;
    private javax.swing.JTable tble_accomp;
    private javax.swing.JTable tble_cancel_booking;
    private javax.swing.JTable tble_command;
    private javax.swing.JTable tble_orderBilling;
    private javax.swing.JTable tble_plat;
    private javax.swing.JTable tble_product;
    private javax.swing.JTable tble_product_viewer;
    private javax.swing.JTable tble_rates;
    private javax.swing.JTable tble_users;
    private javax.swing.JTextField tx_PU;
    private javax.swing.JTextField tx_amount_rate;
    private javax.swing.JTextField tx_filter;
    private javax.swing.JTextField tx_filter_accomp;
    private javax.swing.JTextField tx_filter_cmd2;
    private javax.swing.JTextField tx_filter_cmd_viewer;
    private javax.swing.JTextField tx_filter_plat;
    private javax.swing.JTextField tx_nameProduct;
    private javax.swing.JPasswordField tx_passwor_create;
    private javax.swing.JTextField tx_search_cancel_invoice;
    private javax.swing.JTextField tx_serverName;
    private javax.swing.JLabel tx_tot_paie;
    private javax.swing.JTextField tx_username;
    private javax.swing.JTextField tx_username_full;
    // End of variables declaration//GEN-END:variables
}
