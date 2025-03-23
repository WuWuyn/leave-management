/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.request;

import controller.authentication.BaseRequiredAuthentication;
import dal.EmployeeDB;
import dal.LeaveRequestDB;
import dal.LeaveTypeDB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author admin
 */
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB threshold before writing to disk
        maxFileSize = 1024 * 1024 * 10, // 10MB max file size
        maxRequestSize = 1024 * 1024 * 50 // 50MB max request size
)
public class CreateLeaveRequestController extends BaseRequiredAuthentication {

    private static final String UPLOAD_DIR = "uploads";

    private LeaveRequestDB leaveRequestDAO = new LeaveRequestDB();
    private EmployeeDB employeeDAO = new EmployeeDB();
    private LeaveTypeDB leaveTypeDAO = new LeaveTypeDB();

    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        return null;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response, User u) {
        try {
            // Extract form data
            String title = request.getParameter("title");
            String employeeId = request.getParameter("empID");
            String fromDateStr = request.getParameter("from-date");
            String toDateStr = request.getParameter("to-date");
            String leaveTypeId = request.getParameter("typeID");
            String reason = request.getParameter("reason");

            Part filePart = request.getPart("attachment");
            String attachment = null;
            if (filePart != null && filePart.getSize() > 0) {
                String originalFileName = extractFileName(filePart);
                // Generate a unique file name using timestamp and original name
                String uniqueFileName = System.currentTimeMillis() + "_" + originalFileName;
                // Save the file to a designated directory (e.g., "uploads/")
                String uploadPath = getServletContext().getRealPath("") + "uploads/";
                java.io.File uploadDir = new java.io.File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }
                filePart.write(uploadPath + uniqueFileName);
                attachment = "uploads/" + uniqueFileName; // Store relative path with unique name
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            // Parse the date strings into LocalDate objects
            LocalDate fromDateLocal = LocalDate.parse(fromDateStr, formatter);
            LocalDate toDateLocal = LocalDate.parse(toDateStr, formatter);

            // Convert to java.sql.Date for database storage
            Date fromDate = Date.valueOf(fromDateLocal);
            Date toDate = Date.valueOf(toDateLocal);

            // Fetch related objects
            Employee owner = employeeDAO.getEmployee(employeeId);
            LeaveType type = leaveTypeDAO.get(Integer.parseInt(leaveTypeId));

            // Create and populate LeaveRequest object
            LeaveRequest leaveRequest = new LeaveRequest();
            leaveRequest.setTitle(title);
            leaveRequest.setOwner(owner);
            leaveRequest.setStartDate(fromDate);
            leaveRequest.setEndDate(toDate);
            leaveRequest.setType(type);
            leaveRequest.setAttachment(attachment);
            leaveRequest.setReason(reason);
            leaveRequest.setStatus("Pending"); // Default status
            leaveRequest.setCreatedBy(u);
            leaveRequest.setCreatedDate(new Timestamp(System.currentTimeMillis()));
            // Approval-related fields (approvalBy, approvalDate, comments) remain null initially

            // Insert into database
            leaveRequestDAO.insert(leaveRequest);

            // Redirect to success page
            response.sendRedirect("../request");

        } catch (ServletException | IOException | IllegalArgumentException ex) {
            Logger.getLogger(CreateLeaveRequestController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, User u) {
        try {
            LeaveTypeDB ldb = new LeaveTypeDB();
            ArrayList<LeaveType> leaveTypes = ldb.getLeaveType();

            ArrayList<Employee> employees = new ArrayList<>();
            employees.add(u.getEmp());
            for (Employee staff : u.getEmp().getStaffs()) {
                employees.add(staff);
            }

            req.setAttribute("employees", employees);
            req.setAttribute("leaveType", leaveTypes);
            req.getRequestDispatcher("../request/create.jsp").forward(req, resp);

        } catch (IOException | ServletException ex) {
            Logger.getLogger(CreateLeaveRequestController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
