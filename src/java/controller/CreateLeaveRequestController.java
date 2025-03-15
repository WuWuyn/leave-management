/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dal.LeaveTypeDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.LeaveType;
import model.User;

/**
 *
 * @author admin
 */
public class CreateLeaveRequestController extends BaseRequiredAuthentication{

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, User u) {

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, User u) {
        try {
            LeaveTypeDBContext ldb = new LeaveTypeDBContext();
            ArrayList<LeaveType> leaveTypes = ldb.getLeaveType();
            
            req.getRequestDispatcher("createRequest.jsp").forward(req, resp);
        } catch (ServletException ex) {
            Logger.getLogger(CreateLeaveRequestController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CreateLeaveRequestController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
