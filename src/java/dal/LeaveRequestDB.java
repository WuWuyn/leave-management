/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.util.ArrayList;
import model.LeaveRequest;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Employee;
import model.LeaveType;
import model.User;

/**
 *
 * @author admin
 */
public class LeaveRequestDB extends DBContext<LeaveRequest> {

    public ArrayList<LeaveRequest> getLeaveRequestByUser(User user) {
        ArrayList<LeaveRequest> lrs = new ArrayList<>();

        try {
            String sql = "select * from LeaveRequests lr\n"
                    + "join Users u on lr.createdBy = u.username\n"
                    + "join LeaveTypes lt on lr.typeID = lt.typeID\n"
                    + "where createdBy = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, user.getUsername());

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                LeaveRequest request = new LeaveRequest();
                
                request.setId(rs.getInt("requestID"));
                request.setTitle(rs.getString("title"));
                
                LeaveType lt = new LeaveType();
                lt.setTypeID(rs.getInt("typeID"));
                lt.setTypeName(rs.getNString("typeName"));
                request.setType(lt);
                
                request.setReason(rs.getString("reasonDetail"));
                request.setStartDate(rs.getDate("startDate"));
                request.setEndDate(rs.getDate("endDate"));
                request.setAttachment(rs.getString("attachment"));
                request.setStatus(rs.getString("status"));
                request.setCreatedBy(user);
                request.setCreatedDate(rs.getTimestamp("createdDate"));
                
                Employee e = new Employee();
                e.setEmpID(rs.getString("ownerID"));
                request.setOwner(e);
                
                User manager = new User();
                manager.setUsername(rs.getString("approvalBy"));
                request.setApprovalBy(manager);
                request.setApprovalDate(rs.getTimestamp("approvalDate"));
                request.setComments(rs.getString("comments"));
                
                lrs.add(request);
            }

        } catch (SQLException ex) {
            Logger.getLogger(LeaveRequestDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lrs;
    }

    @Override
    public ArrayList<LeaveRequest> list() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public LeaveRequest get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void insert(LeaveRequest model) {
        String sql = "INSERT INTO LeaveRequests (title, typeID, reasonDetail, startDate, endDate, attachment, status, createdBy, createdDate, ownerID) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, model.getTitle());
            ps.setInt(2, model.getType().getTypeID());
            ps.setString(3, model.getReason());
            ps.setDate(4, model.getStartDate());
            ps.setDate(5, model.getEndDate());
            ps.setString(6, model.getAttachment());
            ps.setString(7, model.getStatus());
            ps.setString(8, model.getCreatedBy().getUsername());
            ps.setTimestamp(9, model.getCreatedDate());
            ps.setString(10, model.getOwner().getEmpID());

            ps.executeUpdate();

            // Retrieve the auto-generated requestID
            String sql_getid = "SELECT @@IDENTITY as requestID";
            PreparedStatement stm_getid = connection.prepareStatement(sql_getid);
            ResultSet rs = stm_getid.executeQuery();
            if (rs.next()) {
                model.setId(rs.getInt("requestID"));
            }

            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                Logger.getLogger(LeaveRequestDB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void update(LeaveRequest model) {
        String sql = "UPDATE LeaveRequests SET title = ?, typeID = ?, reasonDetail = ?, startDate = ?, endDate = ?, attachment = ?, status = ?, "
                + "createdBy = ?, createdDate = ?, ownerID = ?, approvalBy = ?, approvalDate = ?, comments = ? WHERE requestID = ?";
        try {
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, model.getTitle());
            ps.setInt(2, model.getId());
            ps.setString(3, model.getReason());
            ps.setDate(4, model.getStartDate());
            ps.setDate(5, model.getEndDate());
            ps.setString(6, model.getAttachment());
            ps.setString(7, model.getStatus());
            ps.setString(8, model.getCreatedBy().getUsername());
            ps.setTimestamp(9, model.getCreatedDate());
            ps.setString(10, model.getOwner().getEmpID());
            ps.setString(11, model.getApprovalBy().getUsername());
            ps.setTimestamp(12, model.getApprovalDate());
            ps.setString(13, model.getComments());
            ps.setInt(14, model.getId());

            ps.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                Logger.getLogger(LeaveRequestDB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void delete(LeaveRequest model) {
        String sql = "DELETE FROM LeaveRequests WHERE requestID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, model.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
