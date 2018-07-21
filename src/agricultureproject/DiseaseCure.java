/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agricultureproject;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

/**
 *
 * @author Dead
 */
public class DiseaseCure extends javax.swing.JFrame {

    /**
     * Creates new form FormPlantSetup
     */
    String id = "0";
    HashMap hm = new HashMap();
    DatabaseConnection dc = new DatabaseConnection();
    Border empty = BorderFactory.createLineBorder(Color.red, 1);
    Border notEmpty = BorderFactory.createLineBorder(Color.gray, 1);
    
     public DiseaseCure(String a) {
        initComponents();
        this.id = a;
        btnSave.setText("Update");
        getPlantNameInDiseaseModule();
        UpdateDiseaseCureForm();       
    }
     
     
    public void getPlantNameInDiseaseModule()
    {
        System.out.println("getPlantNameInDiseaseModule");
        comboDiseasePlantName.removeAllItems();

        HashMap<Integer,HashMap> plant_name = new HashMap<>();

        plant_name = dc.getAllInformation("plant_setup_mst_tb","");
      
        for (Object key : plant_name.keySet())
        {
            comboDiseasePlantName.addItem(plant_name.get(key).get("plant_name").toString());   
        }
    }
    
    public void tableWidth(JTable table)
    {
        TableColumnModel m = table.getColumnModel();
        TableModel m1 = table.getModel();
        int totalColumn = m.getColumnCount();
        for (int i=0;i<totalColumn;i++)
        {
            int length = 0;
            int totalRow = m1.getRowCount();
            for (int j=0;j<totalRow;j++)
            {
                if(m1.getValueAt(j, i)!=null)
                {
                    int length2 = m1.getValueAt(j, i).toString().length()*8;
                    if(length2>length)
                    {
                        length = length2;
                    }
                }
                m.getColumn(i).setMinWidth(length);
            }           
        }
    }

    public void UpdateDiseaseCureForm() {
        //System.out.println("Update : " + id);
        HashMap<Integer, HashMap> disease = new HashMap<>();

        disease = dc.getAllInformation("disease_tb", " where id = " + id);

        for (Object key : disease.keySet()) {
            txtDiseaseName.setText(disease.get(key).get("name").toString());
            txtDiseaseDescription.setText(disease.get(key).get("symptoms").toString());
            comboDiseasePlantName.setSelectedItem(disease.get(key).get("plant_name").toString());
            
            //model.addRow(new Object[]{plant_setup.get(key).get("id"),plant_setup.get(key).get("plant_name"),plant_setup.get(key).get("description")});
        }

        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
        model.setRowCount(0);
        HashMap<Integer, HashMap> cure = new HashMap<>();

        cure = dc.getAllInformation("cure_tb", " where disease_id = " + id);

        for (Object key : cure.keySet()) {
            //comboPlantName.addItem(plant_setup.get(key).get("plant_name").toString());
            //System.out.println(cure.get(key).get("cure_steps"));
            model.addRow(new Object[]{cure.get(key).get("id"),cure.get(key).get("cure_steps")});
        }
        tableWidth(jTable2);
    }

    public DiseaseCure() {
        initComponents();
        getContentPane().setBackground( Color.white );
        
        
        JTableHeader header = jTable2.getTableHeader();
      header.setBackground(Color.white);
      header.setForeground(new Color(153,0,0));
      header.setFont(new Font("Monospaced", Font.ROMAN_BASELINE, 18));
        
        jTable2.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {

        public Component getTableCellRendererComponent(JTable table, 
                Object value, boolean isSelected, boolean hasFocus,
                int row, int column) {
            Component c = super.getTableCellRendererComponent(table, 
                value, isSelected, hasFocus, row, column);
            c.setBackground(row%2==0 ? Color.LIGHT_GRAY : Color.white);                        
            return c;
        };
        });
        getPlantNameInDiseaseModule();
    }
    
    
    
    
    public boolean checkInput()
    {        
        //System.out.println(jTable2.getColumnCount()-1);
        if(txtDiseaseName.getText().trim().equals(""))
        {
            txtDiseaseName.setBorder(empty);
            return false;     
        }
        else
        {
            txtDiseaseName.setBorder(notEmpty);
        }
        
//        if(txtPlantName.getText().trim().equals(""))
//        {
//            txtPlantName.setBorder(empty);
//            return false;     
//        }
//        else
//        {
//            txtPlantName.setBorder(notEmpty);
//        }
        
        if(txtDiseaseDescription.getText().trim().equals(""))
        {
            txtDiseaseDescription.setBorder(empty);
            return false;     
        }
        else
        {
            txtDiseaseDescription.setBorder(notEmpty);
        }
        
        int row=0,column=0;
        try
        {
            for(row=0; row<jTable2.getRowCount();row++)
            {
                for(column=0; column<jTable2.getColumnCount();column++)
                {
                    if(jTable2.getValueAt(row, column).toString().trim().equals(""))
                    {
                        JOptionPane.showMessageDialog(null,"Found Empty cell at row = "+row + " column =  "+ column);
                        jTable2.editCellAt(row, column);
                        return false;
                    }
                }
            }
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,"Found Empty cell at row = "+row + " column =  "+ column);
            jTable2.editCellAt(row, column);
            return false;
        }
        
        
        //System.out.println("All true");
        return true;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        btnSave = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDiseaseDescription = new javax.swing.JTextArea();
        jPanel6 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtDiseaseName = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        comboDiseasePlantName = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Disease and Cure");
        setBackground(new java.awt.Color(255, 255, 255));
        setResizable(false);
        setType(java.awt.Window.Type.POPUP);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Disease Name");

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel12.setText("Plant Name");

        jButton3.setText("remove row");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jTable2.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                { new Integer(1), ""}
            },
            new String [] {
                "id", "Cure Steps(Please press Enter after last entry)"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTable2.setToolTipText("Press Enter after last entry");
        jTable2.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTable2.setColumnSelectionAllowed(true);
        jTable2.setGridColor(new java.awt.Color(153, 153, 153));
        jTable2.setIntercellSpacing(new java.awt.Dimension(0, 0));
        jTable2.setNextFocusableComponent(btnSave);
        jTable2.setRowHeight(25);
        jTable2.setSelectionForeground(new java.awt.Color(153, 0, 0));
        jTable2.getTableHeader().setReorderingAllowed(false);
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTable2MouseExited(evt);
            }
        });
        jScrollPane4.setViewportView(jTable2);
        jTable2.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        if (jTable2.getColumnModel().getColumnCount() > 0) {
            jTable2.getColumnModel().getColumn(0).setMinWidth(0);
            jTable2.getColumnModel().getColumn(0).setMaxWidth(0);
            jTable2.getColumnModel().getColumn(1).setMinWidth(10000);
        }

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(204, 0, 0));
        jLabel3.setText("*");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setText("Cure Steps");

        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        txtDiseaseDescription.setColumns(20);
        txtDiseaseDescription.setFont(new java.awt.Font("Monospaced", 0, 18)); // NOI18N
        txtDiseaseDescription.setLineWrap(true);
        txtDiseaseDescription.setRows(5);
        jScrollPane1.setViewportView(txtDiseaseDescription);

        jPanel6.setBackground(new java.awt.Color(204, 0, 51));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Disease And Cure Setup Form");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(145, 145, 145)
                .addComponent(jLabel5)
                .addContainerGap(177, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel5)
        );

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(204, 0, 0));
        jLabel8.setText("*");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("Symptoms");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(204, 0, 0));
        jLabel7.setText("*");

        txtDiseaseName.setFont(new java.awt.Font("Monospaced", 0, 18)); // NOI18N
        txtDiseaseName.setForeground(new java.awt.Color(51, 51, 51));
        txtDiseaseName.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtDiseaseName.setToolTipText("Enter Disease Name");
        txtDiseaseName.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true));

        jButton2.setText("add row");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel7))
                            .addComponent(jLabel2))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(comboDiseasePlantName, javax.swing.GroupLayout.PREFERRED_SIZE, 640, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txtDiseaseName, javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel12)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel8))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel1)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel3))
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnSave, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE))))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboDiseasePlantName, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtDiseaseName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(btnSave)
                    .addComponent(jButton2))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
        model.addRow(new Object[]{1,null});
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
        //System.out.println(dc.maxId("plant_setup_mst_tb", "id"));
        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
        boolean check = checkInput();
        //System.out.println(check);
        boolean mstSuccess = false;
        if(check == true)
        {
            // Create a hash map
            hm.clear();
            // Put elements to the map
            hm.put("name", txtDiseaseName.getText());
            hm.put("symptoms", txtDiseaseDescription.getText());
            hm.put("plant_name", comboDiseasePlantName.getSelectedItem());
            //hm.put("image_path", txtDiseaseDescription.getText());
            
            if (this.id != "0") {
                mstSuccess = dc.updateData(" disease_tb ", hm, " where id = " + this.id);
            } else {
                mstSuccess = dc.insertData("disease_tb", hm);
            }

            //mstSuccess = dc.insertData("disease_tb",hm);

            boolean chdSuccess = false;
            String chdId = "";
            if(mstSuccess == true)
            {
                for(int row=0; row<jTable2.getRowCount();row++)
                {
                    hm.clear();
                    hm.put("cure_steps", jTable2.getValueAt(row, 1).toString().trim());
                    //chdSuccess = dc.insertData("cure_tb",hm);
                    
                    if (this.id != "0") {
                        chdId = jTable2.getValueAt(row, 0).toString().trim();
                        chdSuccess = dc.updateData(" cure_tb ", hm, " where id = " + chdId);
                    } else {
                        hm.put("disease_id", dc.maxId("disease_tb", "id"));
                        chdSuccess = dc.insertData("cure_tb", hm);
                    }
                }
                //JOptionPane.showMessageDialog(null,"Data Saved");
                
                if (mstSuccess == true && chdSuccess == true && this.id == "0") {
                    JOptionPane.showMessageDialog(null, "Data Saved");
                    txtDiseaseName.setText("");
                    txtDiseaseDescription.setText("");
                    model.setRowCount(0);
                } else if (mstSuccess == true && chdSuccess == true && this.id != "0") {
                    JOptionPane.showMessageDialog(null, "Data Updated");
                    this.dispose();

                }
            }
            else
            {
                JOptionPane.showMessageDialog(null,"Error in inserting disease");
            }
        }
        else
        {
            JOptionPane.showMessageDialog(null,"Error: Inserting Plant Information");
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        try
        {
            DefaultTableModel model = (DefaultTableModel)jTable2.getModel();
            int i= jTable2.getSelectedRow();
            if(i>=0)
            {
                model.removeRow(i);
            }
            else
            {
                JOptionPane.showMessageDialog(null,"Please select a row from the table");
            }
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,"Error in removing row");
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTable2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseExited
        // TODO add your handling code here:
        System.out.println("Typed");
        tableWidth(jTable2);
    }//GEN-LAST:event_jTable2MouseExited

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox<String> comboDiseasePlantName;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextArea txtDiseaseDescription;
    private javax.swing.JTextField txtDiseaseName;
    // End of variables declaration//GEN-END:variables
}
