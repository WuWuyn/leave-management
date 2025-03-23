/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.util.ArrayList;
import model.LeaveType;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author admin
 */
public class LeaveTypeDB extends DBContext<LeaveType>{

    public ArrayList<LeaveType> getLeaveType(){
        
        ArrayList<LeaveType> list = new ArrayList<>();
        
        try {
            String sql = "select * from LeaveTypes";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            
            while(rs.next()){
                LeaveType type = new LeaveType();
                type.setTypeID(rs.getInt("typeID"));
                type.setTypeName(rs.getNString("typeName"));
                list.add(type);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LeaveTypeDB.class.getName()).log(Level.SEVERE, null, ex);
        }    
        return list;
    }
    
    @Override
    public ArrayList<LeaveType> list() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public LeaveType get(int id) {
        try {
            String sql = "select * from LeaveTypes where typeID=?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            
            while(rs.next()){
                LeaveType type = new LeaveType();
                type.setTypeID(rs.getInt("typeID"));
                type.setTypeName(rs.getNString("typeName"));
                return type;
            }
        } catch (SQLException ex) {
            Logger.getLogger(LeaveTypeDB.class.getName()).log(Level.SEVERE, null, ex);
        }    
        return null;
    }

    @Override
    public void insert(LeaveType model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(LeaveType model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(LeaveType model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
