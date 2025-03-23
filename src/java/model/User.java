/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author admin
 */
public class User {
    private String username;
    private String email;
    private Employee employee;
    private ArrayList<Role> roles = new ArrayList<>();
    private ArrayList<LeaveRequest> leaveRequests = new ArrayList<>();

    public ArrayList<LeaveRequest> getLeaveRequests() {
        return leaveRequests;
    }

    public void setLeaveRequests(ArrayList<LeaveRequest> leaveRequests) {
        this.leaveRequests = leaveRequests;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Employee getEmp() {
        return employee;
    }

    public void setEmp(Employee employee) {
        this.employee = employee;
    }

    public ArrayList<Role> getRoles() {
        return roles;
    }

    public void setRoles(ArrayList<Role> roles) {
        this.roles = roles;
    }
    
    
}
