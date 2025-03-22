/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.util.ArrayList;
import model.Employee;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Department;

/**
 *
 * @author admin
 */
public class EmployeeDBContext extends DBContext<Employee> {

    public Employee getEmployee(String id) {
        ArrayList<Employee> employees = new ArrayList<>();

        try {
            String sql = "WITH EmployeeTree AS (\n"
                    + "    SELECT empID, managerID, 0 AS Level\n"
                    + "    FROM Employees\n"
                    + "    WHERE empID = ?\n"
                    + "\n"
                    + "    UNION ALL\n"
                    + "\n"
                    + "    SELECT e.empID, e.managerID, et.Level + 1\n"
                    + "    FROM Employees e\n"
                    + "    INNER JOIN EmployeeTree et ON e.managerID = et.empID\n"
                    + ")\n"
                    + "SELECT s.empID AS [staffid], e.empName AS [staffname], e.empDob AS [staffdob], e.empGender AS [staffgender], \n"
                    + "       s.managerID, s.Level,\n"
                    + "       d.depID AS [staffdid], d.depName AS [staffdname]\n"
                    + "FROM EmployeeTree s\n"
                    + "INNER JOIN Employees e ON s.empID = e.empID\n"
                    + "INNER JOIN Departments d ON d.depID = e.depID\n"
                    + "ORDER BY Level;";

            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, id);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                Employee e = new Employee();
                e.setEmpID(rs.getString("staffid"));
                e.setEmpName(rs.getNString("staffname"));
                e.setEmpDob(rs.getDate("staffdob"));
                e.setEmpGender(rs.getBoolean("staffgender"));

                Department d = new Department();
                d.setDepID(rs.getString("staffdid"));
                d.setDepName(rs.getNString("staffdname"));
                e.setDep(d);

                Employee manager = new Employee();
                manager.setEmpID(rs.getString("managerID"));
                e.setManager(manager);

                employees.add(e);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(EmployeeDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        if (employees.size() > 0) {
            Employee root = employees.get(0);

            for (Employee employee : employees) {
                Employee manager = getDirectedManager(employees, employee);
                employee.setManager(manager);
                if (manager != null) {
                    manager.getDirectedStaffs().add(employee);
                }
                if (employee != root) {
                    root.getStaffs().add(employee);
                }
            }

            return root;
        } else {
            return null;
        }
    }

    private Employee getDirectedManager(ArrayList<Employee> emps, Employee e) {
        for (Employee emp : emps) {
            if (e.getManager() != null && e.getManager().getEmpID() != null && emp.getEmpID() != null) {
                if (e.getManager().getEmpID().equals(emp.getEmpID())) {
                    return emp;
                }
            }
        }
        return null;
    }

    @Override
    public ArrayList<Employee> list() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Employee get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void insert(Employee model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(Employee model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(Employee model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
