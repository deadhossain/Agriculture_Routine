/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agricultureproject;
import java.awt.List;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
/**
 *
 * @author Dead
 */
public class DatabaseConnection {
    
    Connection conn =null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    ResultSetMetaData rsmd = null;
    
    public Connection getConnection()
    {
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/irrigation_db","root","");

            if(conn == null)
            { 
                JOptionPane.showMessageDialog(null,"Failed to connect."); 
            } 
                return conn;
        }
        catch(Exception e){ 
            System.out.println(e);
            JOptionPane.showMessageDialog(null,"Please turn on the sever first"); 
            return null;
        } 

    }
    
    
    
    public boolean insertData(String tableName,HashMap hm)
    {
        try 
        {
            StringBuilder sql = new StringBuilder("INSERT INTO ").append(tableName).append(" (");
            StringBuilder placeholders = new StringBuilder();

            for (Iterator<String> iter = hm.keySet().iterator(); iter.hasNext();) {
                sql.append(iter.next());
                placeholders.append("?");

                if (iter.hasNext()) {
                    sql.append(",");
                    placeholders.append(",");
                }
            }

            sql.append(") VALUES (").append(placeholders).append(")");
            System.out.println(sql);
            getConnection();
            pst = conn.prepareStatement(sql.toString());
            int i = 0;

            for (Object value : hm.values()) 
            {
                pst.setObject(++i, value);
            }
            System.out.println(pst);
            int affectedRows = pst.executeUpdate();
            return true;
        } 
        catch (Exception ex) 
        {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null,ex.getStackTrace()); 
            //System.out.println(ex);
            return false;
        }
// Get a set of the entries
//      Set set = hm.entrySet();
//      
//      // Get an iterator
//      Iterator i = set.iterator();
//      
//      // Display elements
//      while(i.hasNext()) {
//         Map.Entry me = (Map.Entry)i.next();
//         System.out.print(me.getKey() + ": ");
//         System.out.println(me.getValue());
//      }
//      System.out.println();
    }
    
    
    public boolean updateData(String tableName,HashMap hm, String condition)
    {
        try 
        {
            StringBuilder sql = new StringBuilder("Update ").append(tableName).append(" SET ");

            for (Iterator<String> iter = hm.keySet().iterator(); iter.hasNext();) {
                sql.append(iter.next());
                sql.append(" = ? ");

                if (iter.hasNext()) {
                    sql.append(",");

                }
            }

            sql.append(condition);
            System.out.println(sql);
            getConnection();
            pst = conn.prepareStatement(sql.toString());
            int i = 0;

            for (Object value : hm.values()) 
            {
                pst.setObject(++i, value);
                //System.out.println(value);
            }
            System.out.println(pst);
            boolean affectedRows = pst.execute();
            return true;
        } 
        catch (Exception ex) 
        {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
            JOptionPane.showMessageDialog(null,ex.getStackTrace()); 
            return false;
        }
    }
    
    
    public boolean deleteData(String tableName,HashMap hm)
    {
        try 
        {
            StringBuilder sql = new StringBuilder("Delete From ").append(tableName).append(" where ");

            for (Iterator<String> iter = hm.keySet().iterator(); iter.hasNext();) {
                sql.append(iter.next());
                sql.append(" = ? ");

                if (iter.hasNext()) {
                    sql.append(" and ");
                }
            }
            
            System.out.println(sql);
            getConnection();
            pst = conn.prepareStatement(sql.toString());
            int i = 0;

            for (Object value : hm.values()) 
            {
                pst.setObject(++i, value);
            }
            System.out.println(pst);
            boolean affectedRows = pst.execute();
            return true;
        } 
        catch (Exception ex) 
        {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
            JOptionPane.showMessageDialog(null,ex.getStackTrace()); 
            return false;
        }
    }
    
    public int maxId(String tableName, String columnName)
    {
        try
        {
            int id;
            getConnection();
            //System.out.println(tableName);
            String query =  "SELECT MAX(" + columnName + ") id from " +tableName ;
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();
            if(rs.next())
            {
                id = Integer.parseInt(rs.getString("id"));
                return id;
            }
            else
            {
                return 0;
            }
        }
        catch(Exception e)
        {
            System.out.println(e.getStackTrace());
            JOptionPane.showMessageDialog(null,e.getStackTrace()); 
            return -1;
        }
    }
    
    public int countRecord(String tableName,String condition)
    {
        try
        {
            int countRow;
            getConnection();
            //System.out.println(tableName);
            String query =  "SELECT count(*) id from " +tableName;
            if(!condition.trim().equals(""))
            {
                query = query.concat(condition);
            }
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();
            if(rs.next())
            {
                countRow = Integer.parseInt(rs.getString("id"));
                return countRow;
            }
            else
            {
                return 0;
            }
        }
        catch(Exception e)
        {
            System.out.println(e.getStackTrace());
            JOptionPane.showMessageDialog(null,e.getStackTrace()); 
            return -1;
        }
    }

    public HashMap<Integer,HashMap> getAllInformation(String tableName, String condition)
    {
        HashMap<Integer,HashMap> mainTable = new HashMap<Integer,HashMap>();
        int columnNumber = 0;
        String columnName = "";
        String columnValue = "";
        
        try
        {
            getConnection();

            String query =  "SELECT * FROM " + tableName;
            if(!condition.trim().equals(""))
            {
                query = query.concat(condition);
            }
            System.out.println(query);
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();
            int j=0;
            while(rs.next())
            {
                HashMap subTable = new HashMap(j);
                //subTable.clear();
                rsmd = rs.getMetaData();
                columnNumber = rsmd.getColumnCount();
                
                for(int i=1; i<=columnNumber;i++)
                {
                    columnName = rsmd.getColumnName(i);
                    columnValue = rs.getString(rsmd.getColumnName(i));
                    subTable.put(columnName, columnValue);
                }
                mainTable.put(++j, subTable);

            }


        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,e);
            JOptionPane.showMessageDialog(null,e.getStackTrace()); 
        }
        finally
        {
            return mainTable;
        }  
    }
}
