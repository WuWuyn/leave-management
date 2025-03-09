/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author admin
 */
public class Employee {
    private String empID;
    private String empName;
    private Date empDob;
    private boolean empGender;
    private Employee manager;
    private Department dep;
    
    private ArrayList<Employee> staffs = new ArrayList<>();
    private ArrayList<Employee> directedStaffs = new ArrayList<>();

    public String getEmpID() {
        return empID;
    }

    public void setEmpID(String empID) {
        this.empID = empID;
    }


    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public Date getEmpDob() {
        return empDob;
    }

    public void setEmpDob(Date empDob) {
        this.empDob = empDob;
    }

    public boolean isEmpGender() {
        return empGender;
    }

    public void setEmpGender(boolean empGender) {
        this.empGender = empGender;
    }

    public Employee getManager() {
        return manager;
    }

    public void setManager(Employee manager) {
        this.manager = manager;
    }

    public Department getDep() {
        return dep;
    }

    public void setDep(Department dep) {
        this.dep = dep;
    }

    public ArrayList<Employee> getStaffs() {
        return staffs;
    }

    public void setStaffs(ArrayList<Employee> staffs) {
        this.staffs = staffs;
    }

    public ArrayList<Employee> getDirectedStaffs() {
        return directedStaffs;
    }

    public void setDirectedStaffs(ArrayList<Employee> directedStaffs) {
        this.directedStaffs = directedStaffs;
    }
    
    
}
