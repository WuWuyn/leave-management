/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.home;

import controller.authentication.BaseRequiredAuthentication;
import dal.LeaveRequestDB;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.LeaveRequest;
import model.User;

/**
 *
 * @author admin
 */
public class StaffRequestController extends BaseRequiredAuthentication{

    private LeaveRequestDB leaveRequestDAO = new LeaveRequestDB();
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, User u) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, User u) {
        ArrayList<LeaveRequest> requests = leaveRequestDAO.getRequestForManager(u);
        req.getSession().setAttribute("staffRequest", requests);
        try {
            req.getRequestDispatcher("home/process.jsp").forward(req, resp);
        } catch (ServletException | IOException ex) {
            Logger.getLogger(StaffRequestController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
