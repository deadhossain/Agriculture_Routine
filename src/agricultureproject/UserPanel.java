/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agricultureproject;

import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;


/**
 *
 * @author Neen
 */
public class UserPanel extends javax.swing.JFrame {

    /**
     * Creates new form Main
     */
    HashMap hm = new HashMap();
    DatabaseConnection dc = new DatabaseConnection();
    frameLogin login;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Calendar cal = Calendar.getInstance();
    
    
    
    public UserPanel(String farmer_id, String memeber_type, String user_name) 
    {
        initComponents();
        lblFarmerId.setText(farmer_id);
        lblFarmerType.setText(memeber_type);
        lblWelcomeUser.setText(user_name);
        comboPersonalSchedulePlantId.setVisible(false);
        comboPlantId.setVisible(false);
        lblFarmerId.setVisible(false);
        lblFarmerType.setVisible(false);
        comboReportId.setVisible(false);
        panelSwitch(pnlNotification);
        tableWidth(tableNotification);
        countWorkForNotification(); 
        showNotification();
        tableNotification.setAutoscrolls(true);
        tablePlantSetup.getColumnModel().getColumn(2).setMaxWidth(100000000);
    }
    
    public UserPanel()
    {
        initComponents();
        refreshPlantSetup();
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
    
    
    
    
    public void tableAlignment(JTable table)
    {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        table.getColumnModel().getColumn(1).setCellRenderer( centerRenderer );
    }
    
    public void showNotification()// show notification for all works
    {
//        panelSwitch(pnlNotification);
        
        //panelSwitch(pnlNotification);
        Calendar tomorow = Calendar.getInstance();
        
        
        tomorow.add(Calendar.DAY_OF_YEAR, 1); // add one day to the date/calendar
    
        Date tomorrow;


        
        String day ="";
        DefaultTableModel model = (DefaultTableModel) tableNotification.getModel();
        model.setRowCount(0);
        
        HashMap<Integer,HashMap> notification = new HashMap<>();
        

        notification = dc.getAllInformation("personal_schedule_tb_v"," where farmer_id = " + lblFarmerId.getText() + " and action =0 and date between curdate() and date_add(curdate(), interval 01 day) order by plant_id, date");
        if(notification.isEmpty())
        {
            
            model.addRow(new Object[]{""," There is no work for Today or tomorrow"," "});
            //tableAlignment(tableNotification);
            
        }
        else
        {
            for (Object key : notification.keySet())
            {
                String databaseDate = (String) notification.get(key).get("date");
                Date tdate = new Date();
                
                try
                {
                    tdate = sdf.parse(databaseDate); 
                } 
                catch (ParseException ex) 
                {
                    Logger.getLogger(StartCultivation.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                if(tdate.getDate()==new Date().getDate() && tdate.getMonth()==new Date().getMonth() && tdate.getYear()==new Date().getYear())
                {
                    day = "Today";
                }
                else
                {
                    day = "Tomorrow";
                }
                model.addRow(new Object[]{notification.get(key).get("plant_name"),notification.get(key).get("step_desc"),day});  
            }
        }
      
        tableWidth(tableNotification);
    }

    
    public void countWorkForNotification()// Counting number of works
    {
        jLabel2.setText(Integer.toString(dc.countRecord("personal_schedule_tb_v"," where farmer_id = " + lblFarmerId.getText() + " and action =0 and date between curdate() and date_add(curdate(), interval 01 day) order by plant_id, date")));
    }
    
    
    public void tableBody(JTable table)
    {
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {

        public Component getTableCellRendererComponent(JTable table, 
                Object value, boolean isSelected, boolean hasFocus,
                int row, int column) {
            Component c = super.getTableCellRendererComponent(table, 
                value, isSelected, hasFocus, row, column);
            c.setBackground(row%2==0 ? Color.LIGHT_GRAY : Color.white);                        
            return c;
        };
        });
    }
    
    public void tableHeader(JTable table)
    {
        JTableHeader header = table.getTableHeader();
        header.setBackground(Color.white);
        header.setForeground(new Color(153,0,0));
        header.setFont(new Font("Monospaced", Font.ROMAN_BASELINE, 18));
    }
    
    public void refreshPanelBase()
    {
        pnlBase.repaint();
        pnlBase.revalidate();
    }
    
    public void panelSwitch(Component comp)
    {
        pnlBase.removeAll(); 
        pnlBase.add(comp);
        refreshPanelBase();
    }
    
    public void setComponentColor(Component comp){
        comp.setBackground(new Color(146, 201, 118));
    }
    
    public void resetComponentColor(Component comp){
        comp.setBackground(new Color(76, 161, 66));
    }
    
    public void setBtnColor(Component comp){
        comp.setBackground(new Color(185, 233, 252));
    }
    
    public void resetBtnColor(Component comp){
        comp.setBackground(new Color(217,235,249));
    }
    
    
    public void getPersonalSchedulePlantName(){
        
        comboPersonalSchedulePlantName.removeAllItems();
        comboPersonalSchedulePlantId.removeAllItems();
        HashMap<Integer,HashMap> personal_schedule = new HashMap<>();
        //String name = "";
        personal_schedule = dc.getAllInformation("personal_schedule_tb_v"," where farmer_id = "+ lblFarmerId.getText() + " and action = 0 group by plant_id");
      
        for (Object key : personal_schedule.keySet())
        {
            String name; 
            name = personal_schedule.get(key).get("plant_name").toString() +" ("+ personal_schedule.get(key).get("start_date").toString()+ ")";
            //System.out.println(personal_schedule.get(key).get("plant_name").toString());
            comboPersonalSchedulePlantId.addItem(personal_schedule.get(key).get("plant_id").toString());
            comboPersonalSchedulePlantName.addItem(personal_schedule.get(key).get("plant_name").toString());
        }
        refreshPanelBase();
        System.out.println("Refresh");
        tableWidth(tablePersonalSchedule);
    } 
    
    public void getPlantNameFromReport(){
        comboReportName.removeAllItems();
        comboReportId.removeAllItems();
        HashMap<Integer,HashMap> personal_schedule = new HashMap<>();

        personal_schedule = dc.getAllInformation("personal_schedule_tb_v"," where farmer_id = "+ lblFarmerId.getText() + " and action = 0 group by plant_id");
      
        for (Object key : personal_schedule.keySet())
        {
            comboReportId.addItem(personal_schedule.get(key).get("plant_id").toString());
            comboReportName.addItem(personal_schedule.get(key).get("plant_name").toString());
        }
        comboReportId.addItem("-1");
        comboReportName.addItem("All plant");
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        sideNavPanel = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        navPlantSetup = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        navSchedule = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        navCure = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        navReport = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        lblWelcomeUser = new javax.swing.JLabel();
        navDashboard = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        lblFarmerType = new javax.swing.JLabel();
        lblFarmerId = new javax.swing.JLabel();
        navLogout = new javax.swing.JPanel();
        labelLogout = new javax.swing.JLabel();
        headerPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        pnlBase = new javax.swing.JLayeredPane();
        pnlNotification = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tableNotification = new javax.swing.JTable();
        jLabel13 = new javax.swing.JLabel();
        pnlPlantSetup = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablePlantSetup = new javax.swing.JTable();
        comboPlantId = new javax.swing.JComboBox<String>();
        jPanel18 = new javax.swing.JPanel();
        comboPlantName = new javax.swing.JComboBox<String>();
        jLabel32 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtPlantDescription = new javax.swing.JTextArea();
        pnlPersonalSchedule = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablePersonalSchedule = new javax.swing.JTable();
        jLabel55 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        comboPersonalSchedulePlantName = new javax.swing.JComboBox<String>();
        comboPersonalSchedulePlantId = new javax.swing.JComboBox<String>();
        delPersonalSchedulePlant = new javax.swing.JButton();
        pnlDiseaseCure = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        tfSearchDisease = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableDisease = new javax.swing.JTable();
        jPanel21 = new javax.swing.JPanel();
        btnCure = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        pnlReport = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        btnReportSearch = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        comboReportName = new javax.swing.JComboBox<String>();
        jPanel14 = new javax.swing.JPanel();
        jLabel36 = new javax.swing.JLabel();
        dateFrom = new com.toedter.calendar.JDateChooser();
        dateTo = new com.toedter.calendar.JDateChooser();
        jLabel44 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        comboReportAction = new javax.swing.JComboBox<String>();
        comboReportId = new javax.swing.JComboBox<String>();
        jScrollPane7 = new javax.swing.JScrollPane();
        tableReport = new javax.swing.JTable();
        jPanel16 = new javax.swing.JPanel();
        btnReportPrint = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("User Panel");
        setName("User Panel"); // NOI18N

        mainPanel.setBackground(new java.awt.Color(255, 255, 255));
        mainPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        sideNavPanel.setBackground(new java.awt.Color(105, 190, 93));
        sideNavPanel.setPreferredSize(new java.awt.Dimension(290, 462));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/farmer.png"))); // NOI18N

        navPlantSetup.setBackground(new java.awt.Color(76, 161, 66));
        navPlantSetup.setPreferredSize(new java.awt.Dimension(174, 43));
        navPlantSetup.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                navPlantSetupMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                navPlantSetupMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                navPlantSetupMouseExited(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/rake.png"))); // NOI18N
        jLabel6.setText("Plant Information");

        javax.swing.GroupLayout navPlantSetupLayout = new javax.swing.GroupLayout(navPlantSetup);
        navPlantSetup.setLayout(navPlantSetupLayout);
        navPlantSetupLayout.setHorizontalGroup(
            navPlantSetupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(navPlantSetupLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        navPlantSetupLayout.setVerticalGroup(
            navPlantSetupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(navPlantSetupLayout.createSequentialGroup()
                .addComponent(jLabel6)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        navSchedule.setBackground(new java.awt.Color(76, 161, 66));
        navSchedule.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                navScheduleMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                navScheduleMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                navScheduleMouseExited(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/calendar.png"))); // NOI18N
        jLabel7.setText("Personal Schedule");

        javax.swing.GroupLayout navScheduleLayout = new javax.swing.GroupLayout(navSchedule);
        navSchedule.setLayout(navScheduleLayout);
        navScheduleLayout.setHorizontalGroup(
            navScheduleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(navScheduleLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );
        navScheduleLayout.setVerticalGroup(
            navScheduleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
        );

        navCure.setBackground(new java.awt.Color(76, 161, 66));
        navCure.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                navCureMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                navCureMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                navCureMouseExited(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/germs.png"))); // NOI18N
        jLabel8.setText("Disease & Cure");

        javax.swing.GroupLayout navCureLayout = new javax.swing.GroupLayout(navCure);
        navCure.setLayout(navCureLayout);
        navCureLayout.setHorizontalGroup(
            navCureLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(navCureLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        navCureLayout.setVerticalGroup(
            navCureLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
        );

        navReport.setBackground(new java.awt.Color(76, 161, 66));
        navReport.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                navReportMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                navReportMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                navReportMouseExited(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/report.png"))); // NOI18N
        jLabel9.setText("History & Reports");

        javax.swing.GroupLayout navReportLayout = new javax.swing.GroupLayout(navReport);
        navReport.setLayout(navReportLayout);
        navReportLayout.setHorizontalGroup(
            navReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(navReportLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        navReportLayout.setVerticalGroup(
            navReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
        );

        lblWelcomeUser.setFont(new java.awt.Font("Segoe UI Light", 1, 18)); // NOI18N
        lblWelcomeUser.setForeground(new java.awt.Color(255, 255, 255));

        navDashboard.setBackground(new java.awt.Color(76, 161, 66));
        navDashboard.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                navDashboardMouseDragged(evt);
            }
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                navDashboardMouseMoved(evt);
            }
        });
        navDashboard.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                navDashboardMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                navDashboardMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                navDashboardMouseExited(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/ring.png"))); // NOI18N
        jLabel5.setText("Notification");

        jPanel4.setBackground(new java.awt.Color(255, 102, 102));

        jLabel2.setFont(new java.awt.Font("Segoe UI Light", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("7");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addGap(0, 5, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout navDashboardLayout = new javax.swing.GroupLayout(navDashboard);
        navDashboard.setLayout(navDashboardLayout);
        navDashboardLayout.setHorizontalGroup(
            navDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(navDashboardLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        navDashboardLayout.setVerticalGroup(
            navDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(navDashboardLayout.createSequentialGroup()
                .addGroup(navDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lblFarmerType.setText("Farmer");

        lblFarmerId.setText("1");

        navLogout.setBackground(new java.awt.Color(76, 161, 66));
        navLogout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                navLogoutMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                navLogoutMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                navLogoutMouseExited(evt);
            }
        });

        labelLogout.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        labelLogout.setForeground(new java.awt.Color(255, 255, 255));
        labelLogout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logout.png"))); // NOI18N
        labelLogout.setText("Logout");
        labelLogout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelLogoutMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                labelLogoutMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                labelLogoutMouseExited(evt);
            }
        });

        javax.swing.GroupLayout navLogoutLayout = new javax.swing.GroupLayout(navLogout);
        navLogout.setLayout(navLogoutLayout);
        navLogoutLayout.setHorizontalGroup(
            navLogoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(navLogoutLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(labelLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        navLogoutLayout.setVerticalGroup(
            navLogoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelLogout, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout sideNavPanelLayout = new javax.swing.GroupLayout(sideNavPanel);
        sideNavPanel.setLayout(sideNavPanelLayout);
        sideNavPanelLayout.setHorizontalGroup(
            sideNavPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sideNavPanelLayout.createSequentialGroup()
                .addGroup(sideNavPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(navReport, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(navCure, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(navSchedule, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(navPlantSetup, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE)
                    .addComponent(navDashboard, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(navLogout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 1, Short.MAX_VALUE))
            .addGroup(sideNavPanelLayout.createSequentialGroup()
                .addGroup(sideNavPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(sideNavPanelLayout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(jLabel3))
                    .addGroup(sideNavPanelLayout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(lblFarmerType)
                        .addGap(49, 49, 49)
                        .addComponent(lblFarmerId)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sideNavPanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lblWelcomeUser, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        sideNavPanelLayout.setVerticalGroup(
            sideNavPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sideNavPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblWelcomeUser, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(navDashboard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(navPlantSetup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(navSchedule, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(navCure, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(navReport, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(navLogout, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
                .addGroup(sideNavPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFarmerType)
                    .addComponent(lblFarmerId))
                .addGap(42, 42, 42))
        );

        mainPanel.add(sideNavPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 220, 640));

        headerPanel.setBackground(new java.awt.Color(245, 244, 246));
        headerPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        headerPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI Light", 0, 36)); // NOI18N
        jLabel1.setText("Cultivator Assistant");
        headerPanel.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 10, -1, 40));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/vegetables (1).png"))); // NOI18N
        headerPanel.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 0, 70, -1));

        mainPanel.add(headerPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 0, 900, -1));

        pnlBase.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 102)));
        pnlBase.setLayout(new javax.swing.OverlayLayout(pnlBase));

        pnlNotification.setBackground(new java.awt.Color(255, 255, 255));
        pnlNotification.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel7.setBackground(new java.awt.Color(76, 161, 66));
        jPanel7.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jPanel7MouseMoved(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/checklist.png"))); // NOI18N
        jLabel10.setText("Dashboard");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jLabel10)
                .addGap(0, 781, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addComponent(jLabel10)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlNotification.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 900, 40));

        jScrollPane6.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane6.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        tableNotification.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        tableNotification.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        tableNotification.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Plant Name", "Pending Works", "Day"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tableNotification.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tableNotification.setGridColor(new java.awt.Color(204, 204, 204));
        tableNotification.setRowHeight(25);
        tableNotification.setSelectionBackground(new java.awt.Color(184, 236, 166));
        tableNotification.setSelectionForeground(new java.awt.Color(0, 0, 0));
        jScrollPane6.setViewportView(tableNotification);
        if (tableNotification.getColumnModel().getColumnCount() > 0) {
            tableNotification.getColumnModel().getColumn(0).setMinWidth(100);
            tableNotification.getColumnModel().getColumn(0).setMaxWidth(120);
            tableNotification.getColumnModel().getColumn(1).setMinWidth(700);
            tableNotification.getColumnModel().getColumn(2).setMinWidth(100);
            tableNotification.getColumnModel().getColumn(2).setMaxWidth(120);
        }

        pnlNotification.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, 830, 450));

        jLabel13.setFont(new java.awt.Font("Segoe UI Light", 0, 24)); // NOI18N
        jLabel13.setText("The works that  you have to do next >>>>>>>>>>>");
        pnlNotification.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, 650, 40));

        pnlBase.add(pnlNotification);

        pnlPlantSetup.setBackground(new java.awt.Color(255, 255, 255));
        pnlPlantSetup.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel8.setBackground(new java.awt.Color(70, 147, 21));
        jPanel8.setPreferredSize(new java.awt.Dimension(131, 43));
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel11.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/rake.png"))); // NOI18N
        jLabel11.setText("Plant Setup");
        jPanel8.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 2, 131, 30));

        pnlPlantSetup.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 900, 40));

        jScrollPane2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        tablePlantSetup.setAutoCreateRowSorter(true);
        tablePlantSetup.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        tablePlantSetup.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        tablePlantSetup.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "Days", "Steps"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablePlantSetup.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tablePlantSetup.setGridColor(new java.awt.Color(204, 204, 204));
        tablePlantSetup.setRowHeight(25);
        tablePlantSetup.setSelectionBackground(new java.awt.Color(204, 232, 181));
        tablePlantSetup.setSelectionForeground(new java.awt.Color(153, 0, 0));
        jScrollPane2.setViewportView(tablePlantSetup);
        if (tablePlantSetup.getColumnModel().getColumnCount() > 0) {
            tablePlantSetup.getColumnModel().getColumn(0).setMinWidth(0);
            tablePlantSetup.getColumnModel().getColumn(0).setMaxWidth(0);
            tablePlantSetup.getColumnModel().getColumn(1).setMinWidth(70);
            tablePlantSetup.getColumnModel().getColumn(1).setMaxWidth(80);
            tablePlantSetup.getColumnModel().getColumn(2).setMinWidth(1000);
        }

        pnlPlantSetup.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 230, 840, 290));

        pnlPlantSetup.add(comboPlantId, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, 60, 20));

        jPanel18.setBackground(new java.awt.Color(255, 255, 255));
        jPanel18.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Select a Plant to see its Details", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Light", 0, 14))); // NOI18N

        comboPlantName.setBackground(new java.awt.Color(217, 235, 249));
        comboPlantName.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        comboPlantName.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        comboPlantName.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        comboPlantName.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboPlantNameItemStateChanged(evt);
            }
        });

        jLabel32.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jLabel32.setText("Select Plant Name :");

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addComponent(jLabel32)
                .addGap(5, 5, 5)
                .addComponent(comboPlantName, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 18, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel32)
                    .addComponent(comboPlantName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlPlantSetup.add(jPanel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 70, 300, 70));

        jLabel15.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        jLabel15.setText("Description of the Plant: ");
        pnlPlantSetup.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 80, 190, 40));

        jLabel18.setFont(new java.awt.Font("Segoe UI Light", 0, 24)); // NOI18N
        jLabel18.setText("Procedure to cultivate the following plant>>>>>");
        pnlPlantSetup.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 180, 500, 40));

        txtPlantDescription.setColumns(20);
        txtPlantDescription.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        txtPlantDescription.setRows(5);
        jScrollPane4.setViewportView(txtPlantDescription);

        pnlPlantSetup.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 80, 270, 80));

        pnlBase.add(pnlPlantSetup);

        pnlPersonalSchedule.setBackground(new java.awt.Color(255, 255, 255));
        pnlPersonalSchedule.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel9.setBackground(new java.awt.Color(70, 147, 21));

        jLabel12.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/calendar.png"))); // NOI18N
        jLabel12.setText("Personal Schedule");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(797, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        pnlPersonalSchedule.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 990, 40));

        jScrollPane1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        tablePersonalSchedule.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        tablePersonalSchedule.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        tablePersonalSchedule.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "Date", "Steps", "Action"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablePersonalSchedule.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tablePersonalSchedule.setFillsViewportHeight(true);
        tablePersonalSchedule.setGridColor(new java.awt.Color(204, 204, 204));
        tablePersonalSchedule.setRowHeight(25);
        tablePersonalSchedule.setSelectionBackground(new java.awt.Color(204, 232, 181));
        tablePersonalSchedule.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablePersonalScheduleMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablePersonalSchedule);
        if (tablePersonalSchedule.getColumnModel().getColumnCount() > 0) {
            tablePersonalSchedule.getColumnModel().getColumn(0).setMinWidth(0);
            tablePersonalSchedule.getColumnModel().getColumn(0).setMaxWidth(0);
            tablePersonalSchedule.getColumnModel().getColumn(1).setMinWidth(150);
            tablePersonalSchedule.getColumnModel().getColumn(1).setMaxWidth(200);
            tablePersonalSchedule.getColumnModel().getColumn(2).setMinWidth(800);
            tablePersonalSchedule.getColumnModel().getColumn(3).setMinWidth(120);
            tablePersonalSchedule.getColumnModel().getColumn(3).setMaxWidth(150);
        }

        pnlPersonalSchedule.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, 840, 340));

        jLabel55.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        jLabel55.setText("Start New Process");
        jLabel55.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel55MouseClicked(evt);
            }
        });
        pnlPersonalSchedule.add(jLabel55, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 80, 140, 40));

        jLabel48.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        jLabel48.setText("Select Plant : ");
        pnlPersonalSchedule.add(jLabel48, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 90, -1, -1));

        comboPersonalSchedulePlantName.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboPersonalSchedulePlantNameItemStateChanged(evt);
            }
        });
        comboPersonalSchedulePlantName.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                comboPersonalSchedulePlantNameMouseExited(evt);
            }
        });
        pnlPersonalSchedule.add(comboPersonalSchedulePlantName, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 90, 129, 25));

        pnlPersonalSchedule.add(comboPersonalSchedulePlantId, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 90, 85, 25));

        delPersonalSchedulePlant.setText("Remove");
        delPersonalSchedulePlant.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delPersonalSchedulePlantActionPerformed(evt);
            }
        });
        pnlPersonalSchedule.add(delPersonalSchedulePlant, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 90, -1, -1));

        pnlBase.add(pnlPersonalSchedule);

        pnlDiseaseCure.setBackground(new java.awt.Color(255, 255, 255));
        pnlDiseaseCure.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel12.setBackground(new java.awt.Color(70, 147, 21));
        jPanel12.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel16.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/germs.png"))); // NOI18N
        jLabel16.setText("Disease & Cure");
        jPanel12.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 170, -1));

        pnlDiseaseCure.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 900, 40));

        tfSearchDisease.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tfSearchDisease.setForeground(new java.awt.Color(153, 153, 153));
        tfSearchDisease.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tfSearchDisease.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        tfSearchDisease.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tfSearchDiseaseKeyTyped(evt);
            }
        });
        pnlDiseaseCure.add(tfSearchDisease, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 90, 220, 30));

        jScrollPane3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        tableDisease.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        tableDisease.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        tableDisease.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "Plant Name", "Disease Name", "Symptoms"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tableDisease.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tableDisease.setGridColor(new java.awt.Color(204, 204, 204));
        tableDisease.setRowHeight(25);
        tableDisease.setSelectionBackground(new java.awt.Color(204, 232, 181));
        tableDisease.setSelectionForeground(new java.awt.Color(153, 0, 0));
        tableDisease.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableDiseaseMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tableDisease);
        if (tableDisease.getColumnModel().getColumnCount() > 0) {
            tableDisease.getColumnModel().getColumn(0).setMinWidth(0);
            tableDisease.getColumnModel().getColumn(0).setMaxWidth(0);
            tableDisease.getColumnModel().getColumn(1).setMinWidth(80);
            tableDisease.getColumnModel().getColumn(1).setMaxWidth(80);
            tableDisease.getColumnModel().getColumn(2).setMinWidth(250);
            tableDisease.getColumnModel().getColumn(2).setMaxWidth(250);
            tableDisease.getColumnModel().getColumn(3).setMinWidth(2000);
        }

        pnlDiseaseCure.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 780, 330));

        jPanel21.setBackground(new java.awt.Color(204, 232, 181));

        btnCure.setBackground(new java.awt.Color(255, 255, 255));
        btnCure.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        btnCure.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/solution.png"))); // NOI18N
        btnCure.setText("Cure ");
        btnCure.setToolTipText("To update 1st select a row");
        btnCure.setContentAreaFilled(false);
        btnCure.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCureActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(btnCure)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel21Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnCure))
        );

        pnlDiseaseCure.add(jPanel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 90, 120, 30));

        jLabel14.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jLabel14.setText("Search By Disease Name/ Plant Name/Symptoms ");
        pnlDiseaseCure.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 60, -1, -1));

        pnlBase.add(pnlDiseaseCure);

        pnlReport.setBackground(new java.awt.Color(255, 255, 255));
        pnlReport.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel13.setBackground(new java.awt.Color(70, 147, 21));

        jLabel17.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/report.png"))); // NOI18N
        jLabel17.setText("History & Reports");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(714, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        pnlReport.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 900, 40));

        jPanel10.setBackground(new java.awt.Color(204, 232, 181));
        jPanel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel10MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel10MouseExited(evt);
            }
        });

        btnReportSearch.setBackground(new java.awt.Color(255, 255, 255));
        btnReportSearch.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        btnReportSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/search.png"))); // NOI18N
        btnReportSearch.setText("Search");
        btnReportSearch.setToolTipText("To update 1st select a row");
        btnReportSearch.setContentAreaFilled(false);
        btnReportSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnReportSearchMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnReportSearchMouseExited(evt);
            }
        });
        btnReportSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReportSearchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(btnReportSearch)
                .addGap(0, 17, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addGap(0, 1, Short.MAX_VALUE)
                .addComponent(btnReportSearch))
        );

        pnlReport.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 100, 110, 30));

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Select By Plant", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Light", 0, 14))); // NOI18N

        comboReportName.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        comboReportName.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboReportNameItemStateChanged(evt);
            }
        });
        comboReportName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboReportNameActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(comboReportName, 0, 98, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(comboReportName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        pnlReport.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 70, 130, 80));

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));
        jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Select By Date", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Light", 0, 14))); // NOI18N

        jLabel36.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        jLabel36.setText("To:");

        jLabel44.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        jLabel44.setText("From:");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addComponent(jLabel44)
                .addGap(10, 10, 10)
                .addComponent(dateFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel36)
                .addGap(10, 10, 10)
                .addComponent(dateTo, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 11, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel36)
                        .addGroup(jPanel14Layout.createSequentialGroup()
                            .addGap(5, 5, 5)
                            .addComponent(dateTo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel44)
                        .addGroup(jPanel14Layout.createSequentialGroup()
                            .addGap(5, 5, 5)
                            .addComponent(dateFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        pnlReport.add(jPanel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 70, 360, 80));

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));
        jPanel15.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Select By Action", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Light", 0, 14))); // NOI18N

        comboReportAction.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        comboReportAction.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Not completed", "Completed", "Both" }));
        comboReportAction.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboReportActionItemStateChanged(evt);
            }
        });
        comboReportAction.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboReportActionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(comboReportAction, javax.swing.GroupLayout.Alignment.TRAILING, 0, 118, Short.MAX_VALUE)
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(comboReportAction, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        pnlReport.add(jPanel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 70, -1, -1));

        comboReportId.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        comboReportId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboReportIdActionPerformed(evt);
            }
        });
        pnlReport.add(comboReportId, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, -1, -1));

        jScrollPane7.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        tableReport.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        tableReport.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        tableReport.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "Plant Name", "Date", "Steps", "Action"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableReport.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tableReport.setGridColor(new java.awt.Color(204, 204, 204));
        tableReport.setRowHeight(25);
        tableReport.setSelectionBackground(new java.awt.Color(204, 232, 181));
        tableReport.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableReportMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(tableReport);
        if (tableReport.getColumnModel().getColumnCount() > 0) {
            tableReport.getColumnModel().getColumn(0).setMinWidth(0);
            tableReport.getColumnModel().getColumn(0).setMaxWidth(0);
            tableReport.getColumnModel().getColumn(1).setMinWidth(110);
            tableReport.getColumnModel().getColumn(1).setMaxWidth(130);
            tableReport.getColumnModel().getColumn(2).setMinWidth(100);
            tableReport.getColumnModel().getColumn(2).setMaxWidth(120);
            tableReport.getColumnModel().getColumn(3).setMinWidth(700);
            tableReport.getColumnModel().getColumn(4).setMinWidth(120);
            tableReport.getColumnModel().getColumn(4).setMaxWidth(140);
        }

        pnlReport.add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, 840, 310));

        jPanel16.setBackground(new java.awt.Color(204, 232, 181));
        jPanel16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel16MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel16MouseExited(evt);
            }
        });

        btnReportPrint.setBackground(new java.awt.Color(255, 255, 255));
        btnReportPrint.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        btnReportPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/search.png"))); // NOI18N
        btnReportPrint.setText("Print");
        btnReportPrint.setToolTipText("To update 1st select a row");
        btnReportPrint.setContentAreaFilled(false);
        btnReportPrint.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnReportPrintMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnReportPrintMouseExited(evt);
            }
        });
        btnReportPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReportPrintActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addComponent(btnReportPrint)
                .addGap(0, 29, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                .addGap(0, 1, Short.MAX_VALUE)
                .addComponent(btnReportPrint))
        );

        pnlReport.add(jPanel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 490, 110, 30));

        pnlBase.add(pnlReport);

        mainPanel.add(pnlBase, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 60, 900, 580));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    
    public void refreshPlantSetup()
    {
        panelSwitch(pnlPlantSetup);
        
        comboPlantName.removeAllItems();
        comboPlantId.removeAllItems();
        //DefaultTableModel model = (DefaultTableModel) tablePlantSetup.getModel();
        //model.setRowCount(0);
        HashMap<Integer,HashMap> plant_setup = new HashMap<>();

        plant_setup = dc.getAllInformation("plant_setup_mst_tb","");
      
        for (Object key : plant_setup.keySet())
        {
            comboPlantId.addItem(plant_setup.get(key).get("id").toString());
            comboPlantName.addItem(plant_setup.get(key).get("plant_name").toString());
            
            //model.addRow(new Object[]{plant_setup.get(key).get("id"),plant_setup.get(key).get("plant_name"),plant_setup.get(key).get("description")});
        }
        refreshPlantSetupTable();
        System.out.println("last a Johny bolod hoibo");
    }
    private void navPlantSetupMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_navPlantSetupMouseClicked
        // TODO add your handling code here:
        
        
//        //comboPlantId.setVisible(false);
//        //tableHeader(tablePlantSetup);
//        //tableBody(tablePlantSetup);
//        comboPlantName.removeAllItems();
//        comboPlantId.removeAllItems();
//        //DefaultTableModel model = (DefaultTableModel) tablePlantSetup.getModel();
//        //model.setRowCount(0);
//        HashMap<Integer,HashMap> plant_setup = new HashMap<>();
//
//        plant_setup = dc.getAllInformation("plant_setup_mst_tb","");
//      
//        for (Object key : plant_setup.keySet())
//        {
//            comboPlantId.addItem(plant_setup.get(key).get("id").toString());
//            comboPlantName.addItem(plant_setup.get(key).get("plant_name").toString());
//            
//            //model.addRow(new Object[]{plant_setup.get(key).get("id"),plant_setup.get(key).get("plant_name"),plant_setup.get(key).get("description")});
//        } 
          refreshPlantSetup();
    }//GEN-LAST:event_navPlantSetupMouseClicked

    private void navScheduleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_navScheduleMouseClicked
        // TODO add your handling code here:
        panelSwitch(pnlPersonalSchedule);
        getPersonalSchedulePlantName();
        countWorkForNotification(); 
    }//GEN-LAST:event_navScheduleMouseClicked

    private void navCureMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_navCureMouseClicked
        // TODO add your handling code here:
        panelSwitch(pnlDiseaseCure);
//        tableHeader(tableDisease);
//        tableBody(tableDisease);
        DefaultTableModel model = (DefaultTableModel) tableDisease.getModel();
        model.setRowCount(0);
        HashMap<Integer,HashMap> disease_cure = new HashMap<>();
        

        disease_cure = dc.getAllInformation("disease_tb","");
      
        for (Object key : disease_cure.keySet())
        {
            model.addRow(new Object[]{disease_cure.get(key).get("id"),disease_cure.get(key).get("plant_name"),disease_cure.get(key).get("name"),disease_cure.get(key).get("symptoms")});  
        }      
        tableWidth(tableDisease);
    }//GEN-LAST:event_navCureMouseClicked

    private void navReportMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_navReportMouseClicked
        // TODO add your handling code here:
        panelSwitch(pnlReport);
        getPlantNameFromReport();
    }//GEN-LAST:event_navReportMouseClicked

    private void jPanel7MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel7MouseMoved
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel7MouseMoved

    private void navDashboardMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_navDashboardMouseMoved
        // TODO add your handling code here:
        
        

    }//GEN-LAST:event_navDashboardMouseMoved

    private void navDashboardMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_navDashboardMouseClicked
        // TODO add your handling code here:
        panelSwitch(pnlNotification);
//        tableWidth(tableNotification);
        showNotification();
        
    }//GEN-LAST:event_navDashboardMouseClicked

    private void navDashboardMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_navDashboardMouseEntered
        // TODO add your handling code here:
        setComponentColor(navDashboard);
    }//GEN-LAST:event_navDashboardMouseEntered

    private void navDashboardMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_navDashboardMouseDragged
        // TODO add your handling code here:
        
    }//GEN-LAST:event_navDashboardMouseDragged

    private void navDashboardMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_navDashboardMouseExited
        // TODO add your handling code here:
        resetComponentColor(navDashboard);
    }//GEN-LAST:event_navDashboardMouseExited

    private void navPlantSetupMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_navPlantSetupMouseEntered
        // TODO add your handling code here:
        setComponentColor(navPlantSetup);
    }//GEN-LAST:event_navPlantSetupMouseEntered

    private void navPlantSetupMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_navPlantSetupMouseExited
        // TODO add your handling code here:
        resetComponentColor(navPlantSetup);
    }//GEN-LAST:event_navPlantSetupMouseExited

    private void navScheduleMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_navScheduleMouseEntered
        // TODO add your handling code here:
        setComponentColor(navSchedule);
    }//GEN-LAST:event_navScheduleMouseEntered

    private void navScheduleMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_navScheduleMouseExited
        // TODO add your handling code here:
        resetComponentColor(navSchedule);
    }//GEN-LAST:event_navScheduleMouseExited

    private void navCureMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_navCureMouseEntered
        // TODO add your handling code here:
        setComponentColor(navCure);
    }//GEN-LAST:event_navCureMouseEntered

    private void navCureMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_navCureMouseExited
        // TODO add your handling code here:
        resetComponentColor(navCure);
    }//GEN-LAST:event_navCureMouseExited

    private void navReportMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_navReportMouseEntered
        // TODO add your handling code here:
        setComponentColor(navReport);
    }//GEN-LAST:event_navReportMouseEntered

    private void navReportMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_navReportMouseExited
        // TODO add your handling code here:
        resetComponentColor(navReport);
    }//GEN-LAST:event_navReportMouseExited
    
    
    public void refreshPlantSetupTable()
    {
        DefaultTableModel model = (DefaultTableModel) tablePlantSetup.getModel();
        model.setRowCount(0);
        HashMap<Integer,HashMap> plant_setup = new HashMap<>();
        
        String id = comboPlantId.getItemAt(comboPlantName.getSelectedIndex());
        
        HashMap<Integer,HashMap> plant_description = new HashMap<>();
        plant_description = dc.getAllInformation("plant_setup_mst_tb"," where id = "+id);
        for (Object key : plant_description.keySet())
        {
            txtPlantDescription.setText(plant_description.get(key).get("description").toString());
        }
        
        plant_setup = dc.getAllInformation("plant_setup_chd_tb"," where plant_id = "+id);

        for (Object key : plant_setup.keySet())
        {
            //comboPlantName.addItem(plant_setup.get(key).get("plant_name").toString());
            model.addRow(new Object[]{plant_setup.get(key).get("id"),plant_setup.get(key).get("days"),plant_setup.get(key).get("steps_desc")});           
        }
        refreshPanelBase();
        tableWidth(tablePlantSetup);
    }
    private void comboPlantNameItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboPlantNameItemStateChanged
        // TODO add your handling code here:
        refreshPlantSetupTable();
    }//GEN-LAST:event_comboPlantNameItemStateChanged

    private void jLabel55MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel55MouseClicked
        // TODO add your handling code here:
        StartCultivation sc = new StartCultivation(lblFarmerId.getText());
        sc.setVisible(true);
    }//GEN-LAST:event_jLabel55MouseClicked

    
    public void refreshPersonalScheduleTable()
    {
        DefaultTableModel model = (DefaultTableModel) tablePersonalSchedule.getModel();
        model.setRowCount(0);
        HashMap<Integer,HashMap> personal_schedule = new HashMap<>();
        String id = comboPersonalSchedulePlantId.getItemAt(comboPersonalSchedulePlantName.getSelectedIndex());

        personal_schedule = dc.getAllInformation("personal_schedule_tb_v"," where farmer_id = "+ lblFarmerId.getText() + " and plant_id = " + id + " order by action,date");
      
        for (Object key : personal_schedule.keySet())
        {
            //comboPlantName.addItem(plant_setup.get(key).get("plant_name").toString());
            String action="Completed";
            if(personal_schedule.get(key).get("action").equals("0"))
            {
                action = "Not completed";
            }
            model.addRow(new Object[]{personal_schedule.get(key).get("id"),personal_schedule.get(key).get("date"),personal_schedule.get(key).get("step_desc"),action});
        }
        refreshPanelBase();
        System.out.println("refresh table");
    }
    
    private void comboPersonalSchedulePlantNameItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboPersonalSchedulePlantNameItemStateChanged
        // TODO add your handling code here:
        refreshPersonalScheduleTable();
    }//GEN-LAST:event_comboPersonalSchedulePlantNameItemStateChanged

    
    public void workComplete()
    {
        int row = tablePersonalSchedule.getSelectedRow();
        HashMap updatePersonalSchedule = new HashMap();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //System.out.print("row = " +row);
        if(tablePersonalSchedule.getValueAt(tablePersonalSchedule.getSelectedRow(),3).toString().trim().equals("Completed")){
            JOptionPane.showMessageDialog(null, "This Step has been completed!!");
        }
        else if(row>0)
        {
            JOptionPane.showMessageDialog(null, "You have to complete the previous tasks first");
        }
        else
        {
            String tdate = tablePersonalSchedule.getValueAt(tablePersonalSchedule.getSelectedRow(), 1).toString().trim();
            String tid = tablePersonalSchedule.getValueAt(tablePersonalSchedule.getSelectedRow(), 0).toString().trim();
            Date maxdate = new Date();
            Date tabledate = new Date();
            try {
                tabledate=sdf.parse(tdate);

            } catch (ParseException ex) {
                Logger.getLogger(UserPanel.class.getName()).log(Level.SEVERE, null, ex);
            }

            if(tabledate.after(maxdate))
            {
                JOptionPane.showMessageDialog(null, "The date For this work has not come yet.");
            }
            else
            {
                JDateChooser jd = new JDateChooser();
                jd.setMaxSelectableDate(maxdate);
                jd.setMinSelectableDate(tabledate);
                String message2 ="Input Work Completion Date\n";
                Object[] params = {message2,jd};
                int a = JOptionPane.showConfirmDialog(null,params,"Work Completed?", JOptionPane.PLAIN_MESSAGE);
                if(a != -1)
                {
                    String workCompleteDate = sdf.format(((JDateChooser)params[1]).getDate());
                    updatePersonalSchedule.put("action", "1");
                    updatePersonalSchedule.put("work_complete_date", workCompleteDate);
                    boolean success = dc.updateData(" personal_schedule_tb ", updatePersonalSchedule, " where id = "+tid);
                }


            }
        }
        countWorkForNotification();
        showNotification();
        refreshPersonalScheduleTable();
    }
    private void tablePersonalScheduleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablePersonalScheduleMouseClicked
        // TODO add your handling code here:
        workComplete();         
    }//GEN-LAST:event_tablePersonalScheduleMouseClicked

    private void tfSearchDiseaseKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfSearchDiseaseKeyTyped
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel) tableDisease.getModel();
        model.setRowCount(0);
        HashMap<Integer,HashMap> disease_setup = new HashMap<>();
        //String id = comboPlantId.getItemAt(comboPlantName.getSelectedIndex());
        //        if(id == null)
        //        {
            //            id = comboPlantId.getItemAt(0);
            //        }
        //        System.out.println(id);
        //        System.out.println(id);
        disease_setup = dc.getAllInformation("disease_tb"," where symptoms like '%"+tfSearchDisease.getText()+"%' or name like '%"+tfSearchDisease.getText()+"%' or plant_name like '%"+tfSearchDisease.getText()+"%'");

        for (Object key : disease_setup.keySet())
        {
            //comboPlantName.addItem(plant_setup.get(key).get("plant_name").toString());
            model.addRow(new Object[]{disease_setup.get(key).get("id"),disease_setup.get(key).get("plant_name"),disease_setup.get(key).get("name"),disease_setup.get(key).get("symptoms")});
        }
    }//GEN-LAST:event_tfSearchDiseaseKeyTyped
    static int rowCount;
    private void tableDiseaseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableDiseaseMouseClicked
        // TODO add your handling code here:

        rowCount = tableDisease.getSelectedRow();
        
    }//GEN-LAST:event_tableDiseaseMouseClicked

    private void btnCureActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCureActionPerformed
        // TODO add your handling code here:
        Cure fps = new Cure();
        DefaultTableModel model2 = (DefaultTableModel)tableDisease.getModel();
        DefaultTableModel model = (DefaultTableModel) fps.tableCure.getModel();
        
        HashMap<Integer,HashMap> cure = new HashMap<>();
        //        System.out.println(id);
        cure = dc.getAllInformation("cure_tb"," where disease_id = "+model2.getValueAt(rowCount,0).toString());

        for (Object key : cure.keySet())
        {
            model.addRow(new Object[]{cure.get(key).get("cure_steps")});
        }

        fps.setVisible(true);
    }//GEN-LAST:event_btnCureActionPerformed

    private void labelLogoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelLogoutMouseClicked
        // TODO add your handling code here:
        this.setVisible(false);
        frameLogin obj = new frameLogin();
        obj.setVisible(true);
    }//GEN-LAST:event_labelLogoutMouseClicked

    private void navLogoutMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_navLogoutMouseEntered
        // TODO add your handling code here:
        setComponentColor(navLogout);
    }//GEN-LAST:event_navLogoutMouseEntered

    private void navLogoutMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_navLogoutMouseExited
        // TODO add your handling code here:
        resetComponentColor(navLogout);
    }//GEN-LAST:event_navLogoutMouseExited

    private void navLogoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_navLogoutMouseClicked
        // TODO add your handling code here:
        this.setVisible(false);
        frameLogin obj = new frameLogin();
        obj.setVisible(true);
    }//GEN-LAST:event_navLogoutMouseClicked

    private void labelLogoutMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelLogoutMouseEntered
        // TODO add your handling code here:
        setComponentColor(navLogout);
    }//GEN-LAST:event_labelLogoutMouseEntered

    private void labelLogoutMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelLogoutMouseExited
        // TODO add your handling code here:
        resetComponentColor(navLogout);
    }//GEN-LAST:event_labelLogoutMouseExited

    private void btnReportSearchMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReportSearchMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnReportSearchMouseEntered

    private void btnReportSearchMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReportSearchMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnReportSearchMouseExited
    
    public int workDone(){
        
        String work = comboReportAction.getSelectedItem().toString();
        //System.out.println(work);
        if(work.equals("Both")){
           return -1;
        }
        else if(work.equals("Completed")){
           return 1;
        }
        else return 0;
    }
    
    public void reportSearch()
    {
        DefaultTableModel model = (DefaultTableModel) tableReport.getModel();
        model.setRowCount(0);
        HashMap<Integer,HashMap> personal_schedule = new HashMap<>();
        String id = comboReportId.getItemAt(comboReportName.getSelectedIndex());
        Date t,f;
        String condition = "";
        f = dateFrom.getDate();
        t = dateTo.getDate();
        if(f!= null && t!=null)
        {
            condition += " and date between '"+ sdf.format(f) + "' and '" + sdf.format(t)+ "'";
        }
        if(workDone()!=-1)
        {
            condition += " and action = " + workDone();
        }
        if(comboReportName.getSelectedIndex()>-1 && !comboReportName.getSelectedItem().toString().equals("All plant"))
        {
            condition += " and plant_id = " + id;
        }
        
        
        personal_schedule = dc.getAllInformation("personal_schedule_tb_v"," where farmer_id = "+ lblFarmerId.getText() + condition);

        for (Object key : personal_schedule.keySet())
        {
            //comboPlantName.addItem(plant_setup.get(key).get("plant_name").toString());
            String action="Completed";
            if(personal_schedule.get(key).get("action").equals("0"))
            {
                action = "Not completed";
            }
            model.addRow(new Object[]{personal_schedule.get(key).get("id"),personal_schedule.get(key).get("plant_name"),personal_schedule.get(key).get("date"),personal_schedule.get(key).get("step_desc"),action});
        }
        tableWidth(tableReport);
    }
    
    private void btnReportSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReportSearchActionPerformed
        // TODO add your handling code here:
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //String f="",t="";
        reportSearch();

    }//GEN-LAST:event_btnReportSearchActionPerformed

    private void jPanel10MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel10MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel10MouseEntered

    private void jPanel10MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel10MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel10MouseExited

    private void comboReportNameItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboReportNameItemStateChanged
        // TODO add your handling code here:
//        DefaultTableModel model = (DefaultTableModel) tableReport.getModel();
//        model.setRowCount(0);
//        HashMap<Integer,HashMap> personal_schedule = new HashMap<>();
//        String id = comboPersonalscheduleId.getItemAt(comboPersonalscheduleName.getSelectedIndex());
//
//        personal_schedule = dc.getAllInformation("personal_schedule_tb_v"," where farmer_id = "+ lblFarmerId.getText() + " and plant_id = " + id );
//
//        for (Object key : personal_schedule.keySet())
//        {
//            //comboPlantName.addItem(plant_setup.get(key).get("plant_name").toString());
//            String action="Completed";
//            if(personal_schedule.get(key).get("action").equals("0"))
//            {
//                action = "Not completed";
//            }
//            model.addRow(new Object[]{personal_schedule.get(key).get("id"),personal_schedule.get(key).get("date"),personal_schedule.get(key).get("step_desc"),action});
//        }
        reportSearch();
    }//GEN-LAST:event_comboReportNameItemStateChanged

    private void comboReportNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboReportNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboReportNameActionPerformed

    private void comboReportActionItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboReportActionItemStateChanged
        // TODO add your handling code here:
//        DefaultTableModel model = (DefaultTableModel) tableReport.getModel();
//        model.setRowCount(0);
//        HashMap<Integer,HashMap> personal_schedule = new HashMap<>();
//        String id = comboPersonalscheduleId.getItemAt(comboPersonalscheduleName.getSelectedIndex());
//        int completedWork = workDone();
//        personal_schedule = dc.getAllInformation("personal_schedule_tb_v"," where farmer_id = "+ lblFarmerId.getText() + " and plant_id = " + id + " and action = "+completedWork);
//
//        for (Object key : personal_schedule.keySet())
//        {
//            //comboPlantName.addItem(plant_setup.get(key).get("plant_name").toString());
//            String action="Completed";
//            if(personal_schedule.get(key).get("action").equals("0"))
//            {
//                action = "Not completed";
//            }
//            model.addRow(new Object[]{personal_schedule.get(key).get("id"),personal_schedule.get(key).get("date"),personal_schedule.get(key).get("step_desc"),action});
//        }
        reportSearch();
    }//GEN-LAST:event_comboReportActionItemStateChanged

    private void comboReportActionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboReportActionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboReportActionActionPerformed

    private void comboReportIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboReportIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboReportIdActionPerformed

    private void tableReportMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableReportMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tableReportMouseClicked

    private void btnReportPrintMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReportPrintMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnReportPrintMouseEntered

    private void btnReportPrintMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReportPrintMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnReportPrintMouseExited

    private void btnReportPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReportPrintActionPerformed
        // TODO add your handling code here:
        MessageFormat Header = new MessageFormat("Report Of Cultivation Proccess:");
        MessageFormat Footer = new MessageFormat("Page{0,number,integer}");
        
        try{
            
            tableReport.print(JTable.PrintMode.FIT_WIDTH,Header,Footer);
            
        }catch(java.awt.print.PrinterException e){
            JOptionPane.showMessageDialog(null,e.getMessage());
        }
    }//GEN-LAST:event_btnReportPrintActionPerformed

    private void jPanel16MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel16MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel16MouseEntered

    private void jPanel16MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel16MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel16MouseExited

    private void comboPersonalSchedulePlantNameMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_comboPersonalSchedulePlantNameMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_comboPersonalSchedulePlantNameMouseExited

    private void delPersonalSchedulePlantActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delPersonalSchedulePlantActionPerformed
        // TODO add your handling code here:
        HashMap delPersonalSchedulePlant = new HashMap();
        String id = comboPersonalSchedulePlantId.getItemAt(comboPersonalSchedulePlantName.getSelectedIndex());
        if(comboPersonalSchedulePlantName.getItemCount()>0)
        {
            String start_date = comboPersonalSchedulePlantName.getSelectedItem().toString();
            //start_date = start_date.substring(start_date.indexOf("(")+1,start_date.indexOf(")"));

            delPersonalSchedulePlant.put("plant_id", id);
            //delPersonalSchedulePlant.put("start_date", start_date.trim());
            boolean delSuccess = dc.deleteData("personal_schedule_tb", delPersonalSchedulePlant);
            if(delSuccess == true)
            {
                JOptionPane.showMessageDialog(null, "Delete Successful");
                countWorkForNotification();
                getPersonalSchedulePlantName();
                refreshPersonalScheduleTable();               
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Delete Un-Successful");
            }
            
        }
        
        else
        {
            JOptionPane.showMessageDialog(null, "No Item is selected");
        }
    }//GEN-LAST:event_delPersonalSchedulePlantActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCure;
    private javax.swing.JButton btnReportPrint;
    private javax.swing.JButton btnReportSearch;
    private javax.swing.JComboBox<String> comboPersonalSchedulePlantId;
    private javax.swing.JComboBox<String> comboPersonalSchedulePlantName;
    private javax.swing.JComboBox<String> comboPlantId;
    private javax.swing.JComboBox<String> comboPlantName;
    private javax.swing.JComboBox<String> comboReportAction;
    private javax.swing.JComboBox<String> comboReportId;
    private javax.swing.JComboBox<String> comboReportName;
    private com.toedter.calendar.JDateChooser dateFrom;
    private com.toedter.calendar.JDateChooser dateTo;
    private javax.swing.JButton delPersonalSchedulePlant;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JLabel labelLogout;
    private javax.swing.JLabel lblFarmerId;
    private javax.swing.JLabel lblFarmerType;
    private javax.swing.JLabel lblWelcomeUser;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel navCure;
    private javax.swing.JPanel navDashboard;
    private javax.swing.JPanel navLogout;
    private javax.swing.JPanel navPlantSetup;
    private javax.swing.JPanel navReport;
    private javax.swing.JPanel navSchedule;
    private javax.swing.JLayeredPane pnlBase;
    private javax.swing.JPanel pnlDiseaseCure;
    private javax.swing.JPanel pnlNotification;
    private javax.swing.JPanel pnlPersonalSchedule;
    private javax.swing.JPanel pnlPlantSetup;
    private javax.swing.JPanel pnlReport;
    private javax.swing.JPanel sideNavPanel;
    private javax.swing.JTable tableDisease;
    private javax.swing.JTable tableNotification;
    private javax.swing.JTable tablePersonalSchedule;
    private javax.swing.JTable tablePlantSetup;
    private javax.swing.JTable tableReport;
    private javax.swing.JTextField tfSearchDisease;
    private javax.swing.JTextArea txtPlantDescription;
    // End of variables declaration//GEN-END:variables
}
