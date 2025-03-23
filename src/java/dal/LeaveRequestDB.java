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

    public ArrayList<LeaveRequest> getRequestForManager(User user) {
        ArrayList<LeaveRequest> lrs = new ArrayList<>();

        try {
            String sql = "WITH EmployeeHierarchy AS (\n"
                    + "    -- Anchor member: Direct reports of the logged-in manager\n"
                    + "    SELECT empID\n"
                    + "    FROM Employees\n"
                    + "    WHERE managerID = ?\n"
                    + "    UNION ALL\n"
                    + "    -- Recursive member: Employees reporting to those already in the hierarchy\n"
                    + "    SELECT e.empID\n"
                    + "    FROM Employees e\n"
                    + "    INNER JOIN EmployeeHierarchy eh ON e.managerID = eh.empID\n"
                    + ")\n"
                    + "-- Fetch all leave requests created by users who are staff in the hierarchy\n"
                    + "SELECT lr.*, lt.typeName\n"
                    + "FROM LeaveRequests lr\n"
                    + "INNER JOIN Users u ON lr.createdBy = u.username\n"
                    + "INNER JOIN EmployeeHierarchy eh ON u.empID = eh.empID\n"
                    + "INNER JOIN LeaveTypes lt ON lr.typeID = lt.typeID;";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, user.getEmp().getEmpID());

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
        String sql = "select * from LeaveRequests lr join LeaveTypes lt on lr.typeID = lt.typeID where requestID = ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
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
                User u = new User();
                u.setUsername(rs.getString("createdBy"));
                request.setCreatedBy(u);
                request.setCreatedDate(rs.getTimestamp("createdDate"));

                Employee e = new Employee();
                e.setEmpID(rs.getString("ownerID"));
                request.setOwner(e);

                return request;
            }
        } catch (SQLException ex) {
            Logger.getLogger(LeaveRequestDB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
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
                Logger.getLogger(LeaveRequestDB.class
                        .getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(LeaveRequestDB.class
                        .getName()).log(Level.SEVERE, null, ex);
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

    public void updateLeaveRequestStatus(int id, String status, String managerUsername, Timestamp approvalDate) {
        String sql = "UPDATE LeaveRequests SET status = ?, approvalDate = ?, approvalBy = ? WHERE requestID = ?";

        try {
            connection.setAutoCommit(false);

            PreparedStatement stmt = connection.prepareStatement(sql);

            // Set the parameters in the SQL query
            stmt.setString(1, status);          // Status of the leave request
            stmt.setTimestamp(2, approvalDate); // Approval date as a timestamp
            stmt.setString(3, managerUsername); // Manager who processed the request
            stmt.setInt(4, id);                 // ID of the leave request

            // Execute the update query
            stmt.executeUpdate();
            connection.commit();
        } catch(SQLException e){
            try {
                connection.rollback();
            } catch (SQLException ex) {
                Logger.getLogger(LeaveRequestDB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
