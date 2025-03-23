/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.home;

import dal.EmployeeDB;
import dal.UserDB;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import model.Employee;
import model.User;
import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

/**
 *
 * @author admin
 */
public class HomeController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM");

    private Map<String, Set<LocalDate>> getLeaveMap(List<Employee> staffList, LocalDate weekStart, LocalDate weekEnd) {
        Map<String, Set<LocalDate>> leaveMap = new HashMap<>();
        String sql = "SELECT ownerID, startDate, endDate FROM LeaveRequests "
                + "WHERE status = 'approved' AND startDate <= ? AND endDate >= ?";

        try (Connection conn = DriverManager.getConnection(
                "jdbc:sqlserver://localhost:1433;databaseName=LeaveManagement;user=vuhuyen;password=vuhuyen11;encrypt=true;trustServerCertificate=true"); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, java.sql.Date.valueOf(weekEnd));
            stmt.setDate(2, java.sql.Date.valueOf(weekStart));
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String empID = rs.getString("ownerID");
                    LocalDate leaveStart = rs.getDate("startDate").toLocalDate();
                    LocalDate leaveEnd = rs.getDate("endDate").toLocalDate();
                    LocalDate date = leaveStart;
                    while (!date.isAfter(leaveEnd)) {
                        if (!date.isBefore(weekStart) && !date.isAfter(weekEnd)) {
                            leaveMap.computeIfAbsent(empID, k -> new HashSet<>()).add(date);
                        }
                        date = date.plusDays(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return leaveMap;
    }

    private List<Employee> getStaffList(String managerID) {
        List<Employee> staffList = new ArrayList<>();
        String sql = "SELECT e.empID, e.empName FROM Employees e WHERE e.managerID = ?";

        try (Connection conn = DriverManager.getConnection(
                "jdbc:sqlserver://localhost:1433;databaseName=LeaveManagement;user=vuhuyen;password=vuhuyen11;encrypt=true;trustServerCertificate=true"); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, managerID);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Employee staff = new Employee();
                    staff.setEmpID(rs.getString("empID"));
                    staff.setEmpName(rs.getString("empName"));
                    staffList.add(staff);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return staffList;
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = (String) request.getSession().getAttribute("email");
        UserDB udb = new UserDB();
        User user = udb.get(email);

        if (user != null) {
            EmployeeDB edb = new EmployeeDB();
            Employee profile = edb.getEmployee(user.getEmp().getEmpID());
            profile.setManager(user.getEmp().getManager());
            user.setEmp(profile);
            HttpSession session = request.getSession();
            session.setAttribute("user", user);

            int totalStaffs = user.getEmp().getStaffs().size();
            int totalDaysOff = 0;

            // Database connection details (update with your credentials)
            String url = "jdbc:sqlserver://localhost:1433;databaseName=LeaveManagement;user=vuhuyen;password=vuhuyen11;encrypt=true;trustServerCertificate=true";

            try (Connection conn = DriverManager.getConnection(url)) {
                            

                // Query 2: Total Days Off for the logged-in user
                String daysOffSql = "SELECT SUM(DATEDIFF(day, startDate, endDate) + 1) AS totalDaysOff "
                        + "FROM LeaveRequests WHERE status = 'approved' AND ownerID = ?";
                try (PreparedStatement stmt = conn.prepareStatement(daysOffSql)) {
                    stmt.setString(1, user.getEmp().getEmpID()); // Set the logged-in user's empID
                    try (ResultSet rs = stmt.executeQuery()) {
                        if (rs.next()) {
                            totalDaysOff = rs.getInt("totalDaysOff");
                            if (rs.wasNull()) { // Handle NULL result (no leave requests)
                                totalDaysOff = 0;
                            }
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                // Optionally, set an error message to display on the JSP
                request.setAttribute("error", "Database error occurred: " + e.getMessage());
            }

            // Pass data to JSP
            request.setAttribute("totalStaffs", totalStaffs);
            request.setAttribute("totalDaysOff", totalDaysOff);

            // Determine the start date (Monday of the selected week, default to current week)
            String startDateStr = request.getParameter("startDate");
            LocalDate startDate = startDateStr != null ? LocalDate.parse(startDateStr)
                    : LocalDate.now().with(java.time.temporal.TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY));
            LocalDate endDate = startDate.plusDays(6); // Sunday of the same week

            // Fetch staff list and leave data
            List<Employee> staffList = user.getEmp().getStaffs();
            Map<String, Set<LocalDate>> leaveMap = getLeaveMap(staffList, startDate, endDate);

            // Prepare date list for column headers (e.g., "01/11", "02/11", etc.)
            List<String> dateList = new ArrayList<>();
            for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
                dateList.add(date.format(DATE_FORMATTER));
            }

            // Prepare status map for each staff member and day
            Map<Employee, List<String>> statusMap = new HashMap<>();
            for (Employee staff : staffList) {
                List<String> statuses = new ArrayList<>();
                Set<LocalDate> leaveDates = leaveMap.getOrDefault(staff.getEmpID(), Collections.emptySet());
                for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
                    statuses.add(leaveDates.contains(date) ? "off" : "working");
                }
                statusMap.put(staff, statuses);
            }

            // Calculate navigation dates
            LocalDate prevStartDate = startDate.minusWeeks(1);
            LocalDate nextStartDate = startDate.plusWeeks(1);

            // Set request attributes for JSP
            request.setAttribute("staffList", staffList);
            request.setAttribute("dateList", dateList);
            request.setAttribute("statusMap", statusMap);
            request.setAttribute("startDate", startDate.toString());
            request.setAttribute("prevStartDate", prevStartDate.toString());
            request.setAttribute("nextStartDate", nextStartDate.toString());

            request.getRequestDispatcher("home/home.jsp").forward(request, response);
        } else {
            response.sendRedirect(getServletContext().getContextPath() + "/login");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
