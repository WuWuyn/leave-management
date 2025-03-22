/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.request;

import controller.authentication.BaseRequiredAuthentication;
import dal.LeaveTypeDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Employee;
import model.LeaveRequest;
import model.LeaveType;
import model.User;
import java.sql.*;
import java.time.LocalDateTime;

/**
 *
 * @author admin
 */
public class CreateLeaveRequestController extends BaseRequiredAuthentication {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, User u) {
        LeaveRequest lr = new LeaveRequest();
        try {
            //Title
            lr.setTitle(req.getParameter("title"));
            //Type
            LeaveType lt = new LeaveType();
            lt.setTypeID(Integer.parseInt(req.getParameter("typeID")));
            lr.setType(lt);
            //Reason
            lr.setReason(req.getParameter("reason"));
            //From-To Date
            lr.setFromDate(Date.valueOf(req.getParameter("from-date")));
            lr.setToDate(Date.valueOf(req.getParameter("to-date")));
            //Owner
            Employee e = new Employee();
            e.setEmpID(req.getParameter("empID"));
            lr.setOwner(e);
            //Created By
            lr.setCreatedBy(u);
            //Created Date
            lr.setCreatedDate(Timestamp.valueOf(LocalDateTime.now()));

            Part part = req.getPart("file");
            
//            String realPath = req.getServletContext().getRealPath();
        } catch (IOException ex) {
            Logger.getLogger(CreateLeaveRequestController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServletException ex) {
            Logger.getLogger(CreateLeaveRequestController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, User u) {
        try {
            LeaveTypeDBContext ldb = new LeaveTypeDBContext();
            ArrayList<LeaveType> leaveTypes = ldb.getLeaveType();

            ArrayList<Employee> employees = new ArrayList<>();
            employees.add(u.getEmp());
            for (Employee staff : u.getEmp().getStaffs()) {
                employees.add(staff);
            }

            req.setAttribute("employees", employees);
            req.setAttribute("leaveType", leaveTypes);
            req.getRequestDispatcher("../request/create.jsp").forward(req, resp);

        } catch (IOException ex) {
            Logger.getLogger(CreateLeaveRequestController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServletException ex) {
            Logger.getLogger(CreateLeaveRequestController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
