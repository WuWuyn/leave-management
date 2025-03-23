/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.request;

import controller.authentication.BaseRequiredAuthentication;
import dal.EmployeeDB;
import dal.LeaveRequestDB;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Employee;
import model.LeaveRequest;
import model.User;

/**
 *
 * @author admin
 */
public class ProcessRequestController extends BaseRequiredAuthentication {

    private LeaveRequestDB leaveRequestDAO = new LeaveRequestDB();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, User u) {
        // Retrieve parameters from the form submission
        String idStr = req.getParameter("id");
        String action = req.getParameter("action");
        String managerUsername = u.getUsername();

        if (idStr != null && !idStr.isEmpty() && action != null) {
            try {
                int id = Integer.parseInt(idStr);
                // Determine the new status based on the action
                String status = "Approved".equals(action) ? "Approved" : "Rejected";
                Timestamp approvalDate = new Timestamp(System.currentTimeMillis());
                // Update the leave request in the database
                leaveRequestDAO.updateLeaveRequestStatus(id, status, managerUsername, approvalDate);
                // Redirect back to the staff request list
                resp.sendRedirect(req.getContextPath() + "/staffrequest");
            } catch (IOException ex) {
                Logger.getLogger(ProcessRequestController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, User u) {
        // Get the leave request ID from the request parameters
        String idStr = req.getParameter("id");
        if (idStr != null && !idStr.isEmpty()) {
            int id = Integer.parseInt(idStr);
            // Fetch leave request details using DAO
            LeaveRequest leaveRequest = leaveRequestDAO.get(id);
            EmployeeDB edb = new EmployeeDB();
            Employee e = edb.getEmployee(leaveRequest.getOwner().getEmpID());
            leaveRequest.setOwner(e);

            try {
                // Set the leave request as a request attribute
                req.setAttribute("re", leaveRequest);
                // Forward to processRequest.jsp
                req.getRequestDispatcher("../request/processRequest.jsp").forward(req, resp);
            } catch (ServletException ex) {
                Logger.getLogger(ProcessRequestController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ProcessRequestController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
